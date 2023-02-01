package com.vsca.vsnapvoicecollege.ActivitySender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.vsca.vsnapvoicecollege.Activities.ActionBarActivity
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.MenuDescription
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.MenuTitle

class AddTextNoticeboard : ActionBarActivity() {
    var ScreenType: Boolean? = null

    @JvmField
    @BindView(R.id.lblMenuTitle)
    var lblMenuTitle: TextView? = null

    @JvmField
    @BindView(R.id.btnConfirm)
    var btnConfirm: Button? = null

    @JvmField
    @BindView(R.id.txtTitle)
    var txtTitle: EditText? = null

    @JvmField
    @BindView(R.id.txtDescription)
    var txtDescription: EditText? = null

    var ScreenName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)


        ActionbarWithoutBottom(this)

        ScreenType = intent.getBooleanExtra("screentype", true)
        if (ScreenType!!) {
            ScreenName = "Noticeboard"
            lblMenuTitle!!.setText(R.string.txt_new_noticeboard)
        } else {
            ScreenName = "Text"
            lblMenuTitle!!.setText(R.string.txt_new_text)
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_add_text_noticeboard


    @OnClick(R.id.btnConfirm)
    fun btnNextClick() {

        MenuTitle = txtTitle!!.text.toString()
        MenuDescription = txtDescription!!.text.toString()

        Log.d("MenuDescription",MenuDescription!!)

        if (!(MenuTitle.isNullOrEmpty() && MenuDescription.isNullOrEmpty())) {
            val i: Intent = Intent(this, AddRecipients::class.java)
            i.putExtra("ScreenName",ScreenName)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        } else {
            CommonUtil.ApiAlert(this, "Kindly Enter Details")
        }


    }

    @OnClick(R.id.btnCancel)
    fun btnCancelClick() {
        onBackPressed()
    }

    @OnClick(R.id.imgTextback)
    fun imgBackClick() {
        onBackPressed()
    }

}