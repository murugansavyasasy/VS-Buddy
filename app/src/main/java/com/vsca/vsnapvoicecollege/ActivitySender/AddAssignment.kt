package com.vsca.vsnapvoicecollege.ActivitySender

import android.os.Bundle
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Activities.BaseActivity
import com.vsca.vsnapvoicecollege.R


class AddAssignment : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_image_new)

        ButterKnife.bind(this)

    }

    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard
}