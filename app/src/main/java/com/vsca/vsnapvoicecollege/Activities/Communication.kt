package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.ActivitySender.AddTextNoticeboard
import com.vsca.vsnapvoicecollege.ActivitySender.CommunicationVoice
import com.vsca.vsnapvoicecollege.ActivitySender.ImageOrPdf
import com.vsca.vsnapvoicecollege.Adapters.CommunicationAdapter
import com.vsca.vsnapvoicecollege.Interfaces.MenuCountResponseCallback
import com.vsca.vsnapvoicecollege.Interfaces.communicationListener
import com.vsca.vsnapvoicecollege.Model.*
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.util.ArrayList

class Communication : BaseActivity(), MenuCountResponseCallback {
    var communicationAdapter: CommunicationAdapter? = null
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
    @BindView(R.id.lblCollege)
    var lblCollege: TextView? = null

    @JvmField
    @BindView(R.id.lblDepartmentSize)
    var lblDepartmentSize: TextView? = null

    @JvmField
    @BindView(R.id.lblCollegeSize)
    var lblCollegeSize: TextView? = null

    @JvmField
    @BindView(R.id.lblNoRecordsFound)
    var lblNoRecordsFound: TextView? = null

    @JvmField
    @BindView(R.id.imgRecordVoice)
    var imgRecordVoice: ImageView? = null

    var CommunicationType = true
    var GetCommunicationdata: List<GetCommunicationDetails> = ArrayList()
    var readcount: String? = null
    var unreadcount: String? = null
    var PreviousAddId: Int = 0
    var AdBackgroundImage: String? = null
    var AdSmallImage: String? = null
    var AdWebURl: String? = null
    var GetAdForCollegeData: List<GetAdvertiseData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)


        TabDepartmentColor()
        MenuBottomType()
        CommonUtil.OnMenuClicks("Communication")

        lblMenuTitle!!.setText(R.string.txt_communication)
        lblDepartment!!.setText(R.string.txt_unread)
        lblCollege!!.setText(R.string.txt_read)
        
        if(CommonUtil.Priority.equals("p1")||CommonUtil.Priority.equals("p2")||CommonUtil.Priority.equals("p3")){
            imgRecordVoice!!.visibility=View.VISIBLE
        }else{
            imgRecordVoice!!.visibility=View.GONE
        }
        appViewModel!!.AdvertisementLiveData?.observe(
            this,
            Observer<GetAdvertisementResponse?> { response ->
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    if (status == 1) {
                        GetAdForCollegeData = response.data!!
                        for (j in GetAdForCollegeData.indices) {
                            AdSmallImage = GetAdForCollegeData[j].add_image
                            AdBackgroundImage = GetAdForCollegeData[0].background_image!!
                            AdWebURl = GetAdForCollegeData[0].add_url.toString()
                        }
                        Glide.with(this)
                            .load(AdBackgroundImage)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgAdvertisement!!)
                        Log.d("AdBackgroundImage", AdBackgroundImage!!)

                        Glide.with(this)
                            .load(AdSmallImage)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgthumb!!)

                        OverAllMenuCountRequest(this, CommonUtil.MenuIDCommunication!!)
                    } else {
                        OverAllMenuCountRequest(this, CommonUtil.MenuIDCommunication!!)
                    }
                } else {
                    OverAllMenuCountRequest(this, CommonUtil.MenuIDCommunication!!)
                }
            })


        appviewModelbase!!.OverAllMenuResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    if (response.data.isNullOrEmpty()) {
                        CommunicationRequest(CommunicationType)
                        OverAllMenuCountData = emptyList()
                    } else {
                        OverAllMenuCountData = response.data!!
                        unreadcount = OverAllMenuCountData[0].unread
                        readcount = OverAllMenuCountData[0].read
                        CommunicationRequest(CommunicationType)
                        CountValueSet()
                    }
                } else {
                    CommunicationRequest(CommunicationType)
                    OverAllMenuCountData = emptyList()
                }
            } else {
                CommunicationRequest(CommunicationType)
            }
        }

        appViewModel!!.communicationLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this)
                if (status == 1) {
                    if (CommunicationType) {
                        GetCommunicationdata = response.data!!
                        val size = GetCommunicationdata.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE

                            communicationAdapter =
                                CommunicationAdapter(GetCommunicationdata, this, object :
                                    communicationListener {
                                    override fun oncommunicationClick(
                                        holder: CommunicationAdapter.MyViewHolder,
                                        item: GetCommunicationDetails
                                    ) {
                                        holder.rytRecentNotification.setOnClickListener({
                                            AppReadStatus(
                                                this@Communication,
                                                "sms",
                                                item.msgdetailsid!!
                                            );

                                        })
                                    }
                                })

                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = communicationAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            communicationAdapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    } else {

                        GetCommunicationdata = response.data!!
                        val size = GetCommunicationdata.size
                        if (size > 0) {
                            Log.d("SetRecuylerview", "test")
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE

                            communicationAdapter =
                                CommunicationAdapter(GetCommunicationdata, this, object :
                                    communicationListener {
                                    override fun oncommunicationClick(
                                        holder: CommunicationAdapter.MyViewHolder,
                                        item: GetCommunicationDetails
                                    ) {
                                        holder.rytRecentNotification.setOnClickListener({
                                            AppReadStatus(
                                                this@Communication,
                                                "sms",
                                                item.msgdetailsid!!
                                            );
                                        })
                                    }
                                })
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = communicationAdapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            communicationAdapter!!.notifyDataSetChanged()
                        } else {
                            NoDataFound()
                        }
                    }
                } else {
                    UserMenuRequest(this)
                    NoDataFound()

                }
            } else {
                UserMenuRequest(this)
                NoDataFound()
            }
        }


        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (CommunicationType) {
                CommunicationType = true
                CommunicationRequest(CommunicationType)
            } else {
                CommunicationType = false
                CommunicationRequest(CommunicationType)
            }
        })
    }

    fun NoDataFound() {
        lblNoRecordsFound!!.visibility = View.VISIBLE
        recyclerNoticeboard!!.visibility = View.GONE
    }

    private fun CountValueSet() {
        if (!unreadcount.equals("0") && !unreadcount.equals("")) {
            lblDepartmentSize!!.visibility = View.VISIBLE
            lblDepartmentSize!!.text = unreadcount
        } else {
            lblDepartmentSize!!.visibility = View.GONE
            unreadcount = "0"
        }
        if (!readcount.equals("0") && !readcount.equals("")) {
            lblCollegeSize!!.visibility = View.VISIBLE
            lblCollegeSize!!.text = readcount
        } else {
            lblCollegeSize!!.visibility = View.GONE
            readcount = "0"
        }
        var intdepartment = Integer.parseInt(unreadcount!!)
        var intCollegecount = Integer.parseInt(readcount!!)
        var TotalSizeCount = intdepartment + intCollegecount
        if (TotalSizeCount > 0) {
            lbltotalsize!!.visibility = View.VISIBLE
            lbltotalsize!!.text = TotalSizeCount.toString()
        } else {
            lbltotalsize!!.visibility = View.GONE
        }
    }

    private fun AdForCollegeApi() {

        var mobilenumber = SharedPreference.getSH_MobileNumber(this)
        var devicetoken = SharedPreference.getSH_DeviceToken(this)
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_ad_device_token, devicetoken)
        jsonObject.addProperty(ApiRequestNames.Req_MemberID, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_mobileno, mobilenumber)
        jsonObject.addProperty(ApiRequestNames.Req_college_id, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
        jsonObject.addProperty(ApiRequestNames.Req_previous_add_id, PreviousAddId)
        appviewModelbase!!.getAdforCollege(jsonObject, this)
        Log.d("AdForCollege:", jsonObject.toString())

        PreviousAddId = PreviousAddId+1;
        Log.d("PreviousAddId", PreviousAddId.toString())
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard

    fun CommunicationRequest(readtype: Boolean) {
        val jsonObject = JsonObject()
        run {

            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            if (readtype) {
                jsonObject.addProperty(ApiRequestNames.Req_readtype, CommonUtil.Unread)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_readtype, CommonUtil.Read)
            }
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)
            }
            appViewModel!!.getCommunicationListbyType(jsonObject, this)
            Log.d("CommunicationRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        bottomsheetStateCollpased()
        TabDepartmentColor()
        CommunicationType = true
        CommunicationRequest(CommunicationType)
    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        CommunicationType = false
        CommunicationRequest(CommunicationType)
        TabCollegeColor()

    }

    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }


    @OnClick(R.id.imgRecordVoice)
    fun RecordVoice(){
        val i: Intent = Intent(this, CommunicationVoice::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }
    @OnClick(R.id.imgAddPlus)
    fun plusClick() {

        val i: Intent = Intent(this, AddTextNoticeboard::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.putExtra("screentype",false)
        startActivity(i)

    }

    @OnClick(R.id.LayoutAdvertisement)
    fun adclick() {
        LoadWebViewContext(this, AdWebURl)
    }

    override fun menucountcallback(responseBody: MenuDetailsResponse) {
        UserMenuRequest(this)
    }


    override fun onResume() {
        var AddId: Int = 1
        PreviousAddId = PreviousAddId+1;

        AdForCollegeApi()

        super.onResume()
    }


}