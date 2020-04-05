package com.applover.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.applover.R
import com.applover.common.BUNDLE_LOGIN_STATUS
import com.applover.common.BaseFragment
import com.applover.common.DOUBLE_BACK_PRESSED_EXIT
import com.applover.common.EMPTY_FIELD
import com.applover.common.INPUT_FIELDS_ERROR
import com.applover.common.WRONG_EMAIL_PATTERN
import com.applover.common.customs.ProgressButton
import com.applover.entity.LoginStatus
import com.applover.entity.UserCredentials
import com.applover.ext.checkDataValidationOnUnfocus
import com.applover.ext.snackBar
import com.applover.ext.toast
import com.applover.network.ResultObserver
import com.applover.ui.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()

    /**
     * Because delay is set for 2 sec I don't save and restore this value (e.g. screen rotation).
     * It's pointless due to rotation and click again which may take the same time.
     * */
    private var isDoubleClicked = false
    private lateinit var progressButton: ProgressButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressButton = ProgressButton(view)

        viewModel.loginStatus.observe(viewLifecycleOwner, ResultObserver(::onSuccess, ::onFailure))

        setUpClickListeners(view)
        handleInputEditTextErrors()
        exitTheAppOnDoubleBackPressed()
    }

    private fun setUpClickListeners(view: View) {
        login_progress_button.setOnClickListener {
            signIn()
        }
    }

    private fun handleInputEditTextErrors() {
        login_email_input_edit_text.checkDataValidationOnUnfocus(
            login_email_text_input_layout,
            { it.error = EMPTY_FIELD },
            { it.error = WRONG_EMAIL_PATTERN }
        )

        login_password_input_edit_text.checkDataValidationOnUnfocus(
            login_password_text_input_layout,
            { it.error = EMPTY_FIELD }, {}
        )
    }

    private fun exitTheAppOnDoubleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isDoubleClicked) {
                requireActivity().finish()
            }
            isDoubleClicked = true
            toast(DOUBLE_BACK_PRESSED_EXIT)
            lifecycleScope.launch {
                delay(2000)
                isDoubleClicked = false
            }
        }
    }

    private fun hasTextInputLayoutAnyErrors() =
        login_email_text_input_layout.error == null && login_password_text_input_layout.error == null

    private fun signIn() {
        if (hasTextInputLayoutAnyErrors()) {
            progressButton.buttonActivated()
            val email = login_email_input_edit_text.text.toString()
            val password = login_password_input_edit_text.text.toString()
            val userCredentials = UserCredentials(email, password)
            viewModel.signIn(userCredentials)
        } else {
            snackBar(INPUT_FIELDS_ERROR)
        }
    }

    private fun onSuccess(loginStatus: LoginStatus) {
        val bundle = bundleOf(BUNDLE_LOGIN_STATUS to loginStatus)
        findNavController().navigate(R.id.action_loginFragment_to_resultFragment, bundle)
    }

    private fun onFailure(exception: Exception) {
        progressButton.actionFinished()
        snackBar(exception.localizedMessage ?: "Error occured")
    }

}
