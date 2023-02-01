package com.vsca.vsnapvoicecollege.Activities


import com.vsca.vsnapvoicecollege.R
import android.os.Bundle
import butterknife.ButterKnife
import android.util.Log


class EnterOtp : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

    }

    override val layoutResourceId: Int
        protected get() = R.layout.activity_enter_otp
}