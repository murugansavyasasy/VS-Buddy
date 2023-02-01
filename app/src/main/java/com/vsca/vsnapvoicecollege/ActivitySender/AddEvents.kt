package com.vsca.vsnapvoicecollege.ActivitySender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.OnClick
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil

class AddEvents : AppCompatActivity() {

    @JvmField
    @BindView(R.id.rytEventDate)
    var rytEventDate: RelativeLayout? = null

    @JvmField
    @BindView(R.id.rytEventTime)
    var rytEventTime: RelativeLayout? = null

    @JvmField
    @BindView(R.id.lblEventDate)
    var lblEventDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)
    }


    @OnClick(R.id.rytEventDate)
    fun eventdateClick(){

        CommonUtil.Datepicker(this, lblEventDate!!)


    }
}