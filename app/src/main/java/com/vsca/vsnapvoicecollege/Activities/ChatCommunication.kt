package com.vsca.vsnapvoicecollege.Activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.vsca.vsnapvoicecollege.Model.GetVideoListDetails
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.util.ArrayList

class ChatCommunication : BaseActivity() {
    override var appViewModel: App? = null

    @JvmField
    @BindView(R.id.recyclerCommon)
    var recyclerNoticeboard: RecyclerView? = null

    @JvmField
    @BindView(R.id.imgAdvertisement)
    var imgAdvertisement: ImageView? = null

    @JvmField
    @BindView(R.id.imgthumb)
    var imgthumb: ImageView? = null

    @JvmField
    @BindView(R.id.lbltotalsize)
    var lbltotalsize: TextView? = null

    @JvmField
    @BindView(R.id.lblMenuTitle)
    var lblMenuTitle: TextView? = null

    @JvmField
    @BindView(R.id.lblDepartment)
    var lblDepartment: TextView? = null

    @JvmField
    @BindView(R.id.layoutTab)
    var layoutTab: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    var GetVideoListData: List<GetVideoListDetails> = ArrayList()
    var CountVideo="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)
        MenuBottomType()

        layoutTab!!.visibility = View.GONE
        lblMenuTitle!!.setText(R.string.txt_Video)


        CommonUtil.OnMenuClicks("ChatCommunication")


        imgRefresh!!.setOnClickListener(View.OnClickListener {


        })
    }


    override val layoutResourceId: Int
        get() = R.layout.activity_chat_communication

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()

        super.onBackPressed()
    }
}