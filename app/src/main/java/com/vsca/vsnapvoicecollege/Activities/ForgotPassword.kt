package com.vsca.vsnapvoicecollege.Activities

import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import android.os.Bundle
import butterknife.ButterKnife
import android.content.Intent
import android.app.AlertDialog
import android.net.Uri
import android.view.*
import android.widget.*

class ForgotPassword : AppCompatActivity() {
    @JvmField
    @BindView(R.id.btnOtp)
    var Btnotp: Button? = null
    var lblclose: TextView? = null
    var Btndial: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        ButterKnife.bind(this)
        Btnotp!!.setOnClickListener {
            val builder = AlertDialog.Builder(this@ForgotPassword)
            val layoutInflater = LayoutInflater.from(this@ForgotPassword)
            val customView = layoutInflater.inflate(R.layout.activity_otp_popup, null)
            lblclose = customView.findViewById(R.id.lblclose)
            Btndial = customView.findViewById(R.id.btndial)
            builder.setView(customView)
            val alert = builder.create()
            alert.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            alert.setCanceledOnTouchOutside(true)
            alert.show()
            val lp = WindowManager.LayoutParams()
            val window = alert.window
            window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            window.setGravity(Gravity.CENTER)
            window.setBackgroundDrawableResource(R.drawable.bg_rectangle_white)
            lp.copyFrom(window.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = 800
            window.attributes = lp
//            lblclose!!setOnClickListener(View.OnClickListener { alert.dismiss() })
//            Btndial!!.setOnClickListener(View.OnClickListener {
//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:04471168965")
//                startActivity(intent)
//                val intents = Intent(this@ForgotPassword, EnterOtp::class.java)
//                this@ForgotPassword.startActivity(intents)
//                setResult(RESULT_OK, intents)
//                startActivityForResult(intent, 1)
//            })
        }
    }
}