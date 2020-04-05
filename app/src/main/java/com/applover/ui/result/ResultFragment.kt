package com.applover.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.applover.R
import com.applover.common.BUNDLE_LOGIN_STATUS
import com.applover.common.BaseFragment
import com.applover.common.STATUS_SUCCESS
import com.applover.entity.LoginStatus
import kotlinx.android.synthetic.main.fragment_result.result_login_status

class ResultFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            /** It's unnecessary now but I left that to show one of many possible ways to pass data */
            val loginStatus = it.getParcelable<LoginStatus>(BUNDLE_LOGIN_STATUS)
            result_login_status.text = STATUS_SUCCESS
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
