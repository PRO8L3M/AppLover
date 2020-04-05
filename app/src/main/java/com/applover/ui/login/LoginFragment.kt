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
import com.applover.entity.LoginStatus
import com.applover.entity.UserCredentials
import com.applover.ext.checkDataValidationOnUnfocus
import com.applover.ext.snackBar
import com.applover.ext.toast
import com.applover.network.ResultObserver
import com.applover.ui.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.login_progress_button
import kotlinx.android.synthetic.main.fragment_login.login_email_input_edit_text
import kotlinx.android.synthetic.main.fragment_login.login_password_input_edit_text
import kotlinx.android.synthetic.main.fragment_login.login_password_text_input_layout
import kotlinx.android.synthetic.main.fragment_login.login_email_text_input_layout
import kotlinx.android.synthetic.main.progress_button_layout.progress_button_progress_bar
import kotlinx.android.synthetic.main.progress_button_layout.progress_button_text_view
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()
    private var isDoubleClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginStatus.observe(viewLifecycleOwner, ResultObserver(::onSuccess, ::onFailure))

        setUpClickListeners()
        handleInputEditTextErrors()
        onDoubleBackPressed()
    }

    private fun setUpClickListeners() {
        login_progress_button.setOnClickListener {
            signIn()
        }
    }

    private fun activateProgressIndicator() {
        progress_button_progress_bar.visibility = View.VISIBLE
        progress_button_text_view.visibility = View.INVISIBLE
    }

    private fun deactivateProgressIndicator(){
        progress_button_progress_bar.visibility = View.GONE
        progress_button_text_view.visibility = View.VISIBLE
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

    private fun onDoubleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isDoubleClicked) {
                requireActivity().finish()
            }
            isDoubleClicked = true
            toast(DOUBLE_BACK_PRESSED_EXIT)
            lifecycleScope.launch {
                delay(resources.getInteger(R.integer.DOUBLE_BACK_PRESSED_DELAY).toLong())
                isDoubleClicked = false
            }
        }
    }

    private fun hasTextInputLayoutAnyErrors() = login_email_text_input_layout.error != null && login_password_text_input_layout.error != null

    private fun hasEditTextBlank() = login_email_input_edit_text.text.isNullOrBlank() && login_password_input_edit_text.text.isNullOrBlank()

    private fun signIn() {
        if (!hasTextInputLayoutAnyErrors() && !hasEditTextBlank()) {
            activateProgressIndicator()
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
        deactivateProgressIndicator()
        snackBar(exception.localizedMessage ?: resources.getString(R.string.uknown_error))
    }

}
