package com.vsca.vsnapvoicecollege.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import com.vsca.vsnapvoicecollege.Adapters.AssignmentAdapter
import com.vsca.vsnapvoicecollege.ViewModel.App
import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.vsca.vsnapvoicecollege.Model.GetAssignmentDetails
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import androidx.lifecycle.ViewModelProvider
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import butterknife.OnClick
import com.vsca.vsnapvoicecollege.ActivitySender.ImageOrPdf
import com.vsca.vsnapvoicecollege.Model.GetOverAllCountDetails
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.OnBackSetBottomMenuClickTrue
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.OnMenuClicks

import java.util.ArrayList

class Assignment : BaseActivity() {
    var assignmentadapter: AssignmentAdapter? = null
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
    var AssignmentType = true
    var GetAssignmentData: List<GetAssignmentDetails> = ArrayList()
    var TotalSize = ""

    var CountUpcoming: String? = null
    var Countpast: String? = null
    var OverAllMenuCountData1: List<GetOverAllCountDetails> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this@Assignment)

        CommonUtil.RequestCameraPermission(this)
        MenuBottomType()
        OverAllMenuCountRequestAssignment(this, CommonUtil.MenuIDAssignment!!)

        OnMenuClicks("Assignment")
        TabDepartmentColor()


        lblMenuTitle!!.setText(R.string.txt_assignment)
        lblDepartment!!.setText(R.string.txt_upcoming)
        lblCollege!!.setText(R.string.txt_past)

        Glide.with(this@Assignment)
            .load(CommonUtil.CommonAdvertisement)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgAdvertisement!!)
        Glide.with(this@Assignment)
            .load(CommonUtil.CommonAdImageSmall)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgthumb!!)

        appViewModel!!.OverAllMenuResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                if (status == 1) {
                    if (response.data.isNullOrEmpty()) {
                        AssignmentRequest(AssignmentType)
                        OverAllMenuCountData1 = emptyList()
                    } else {
                        OverAllMenuCountData1 = response.data!!
                        CountUpcoming = OverAllMenuCountData1[0].upcomingassignment
                        Countpast = OverAllMenuCountData1[0].pastassignment
                        AssignmentRequest(AssignmentType)
                        CountValueSet()
                    }
                } else {
                    AssignmentRequest(AssignmentType)
                    OverAllMenuCountData1 = emptyList()
                }
            } else {
                AssignmentRequest(AssignmentType)
            }
        }

        appViewModel!!.assignmentListResponseLiveData!!.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message
                UserMenuRequest(this@Assignment)
                if (status == 1) {
                    if (AssignmentType) {
                        GetAssignmentData = response.data!!
                        val size = GetAssignmentData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            assignmentadapter =
                                AssignmentAdapter(GetAssignmentData, this@Assignment)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@Assignment)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = assignmentadapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            assignmentadapter!!.notifyDataSetChanged()
                        } else {
                            lblNoRecordsFound!!.visibility = View.VISIBLE
                            recyclerNoticeboard!!.visibility = View.GONE
                        }
                    } else {
                        GetAssignmentData = response.data!!
                        val size = GetAssignmentData.size
                        if (size > 0) {
                            lblNoRecordsFound!!.visibility = View.GONE
                            recyclerNoticeboard!!.visibility = View.VISIBLE
                            assignmentadapter =
                                AssignmentAdapter(GetAssignmentData, this@Assignment)
                            val mLayoutManager: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@Assignment)
                            recyclerNoticeboard!!.layoutManager = mLayoutManager
                            recyclerNoticeboard!!.itemAnimator = DefaultItemAnimator()
                            recyclerNoticeboard!!.adapter = assignmentadapter
                            recyclerNoticeboard!!.recycledViewPool.setMaxRecycledViews(0, 80)
                            assignmentadapter!!.notifyDataSetChanged()
                        } else {
                            lblNoRecordsFound!!.visibility = View.VISIBLE
                            recyclerNoticeboard!!.visibility = View.GONE
                        }
                    }
                } else {
                    if (AssignmentType) {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        recyclerNoticeboard!!.visibility = View.GONE
                    } else {
                        lblNoRecordsFound!!.visibility = View.VISIBLE
                        recyclerNoticeboard!!.visibility = View.GONE
                    }
                }
            } else {
                UserMenuRequest(this@Assignment)
                lblNoRecordsFound!!.visibility = View.VISIBLE
                recyclerNoticeboard!!.visibility = View.GONE
            }
        }

        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (AssignmentType) {
                AssignmentType = true
                AssignmentRequest(AssignmentType)
            } else {
                AssignmentType = false
                AssignmentRequest(AssignmentType)
            }
        })
    }

    private fun CountValueSet() {

        if (!CountUpcoming.equals("0") && !CountUpcoming.equals("")) {
            lblDepartmentSize!!.visibility = View.VISIBLE
            lblDepartmentSize!!.text = CountUpcoming
        } else {
            lblDepartmentSize!!.visibility = View.GONE
            CountUpcoming = "0"
        }
        if (!Countpast.equals("0") && !Countpast.equals("")) {
            lblCollegeSize!!.visibility = View.VISIBLE
            lblCollegeSize!!.text = Countpast
        } else {
            lblCollegeSize!!.visibility = View.GONE
            Countpast = "0"
        }
        var intdepartment = Integer.parseInt(CountUpcoming!!)
        var intCollegecount = Integer.parseInt(Countpast!!)
        var TotalSizeCount = intdepartment + intCollegecount
        if (TotalSizeCount > 0) {
            lbltotalsize!!.visibility = View.VISIBLE
            lbltotalsize!!.text = TotalSizeCount.toString()
        } else {
            lbltotalsize!!.visibility = View.GONE
        }
    }


    override val layoutResourceId: Int
        get() = R.layout.activity_noticeboard

    private fun AssignmentRequest(type: Boolean) {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
            if (CommonUtil.Priority == "p1") {
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, 0)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
            }
            if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)

            }
            jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
            if (type) {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.UpcomingAssignment)
            } else {
                jsonObject.addProperty(ApiRequestNames.Req_type, CommonUtil.PastAssignment)
            }
            appViewModel!!.getAssignmentListbyType(jsonObject, this@Assignment)
            Log.d("AssignmentRequest:", jsonObject.toString())
        }
    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        bottomsheetStateCollpased()

        AssignmentType = true
        AssignmentRequest(AssignmentType)
        TabDepartmentColor()
    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        AssignmentType = false
        AssignmentRequest(AssignmentType)

        bottomsheetStateCollpased()
        TabCollegeColor()

    }
    @OnClick(R.id.imgAddPlus)
    fun imgaddclick() {
        val i: Intent = Intent(this, ImageOrPdf::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)

    }

//    override fun onResume() {
//        AssignmentRequest(AssignmentType)
//        super.onResume()
//    }

    override fun onBackPressed() {
        OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }

    @OnClick(R.id.LayoutAdvertisement)
    fun adclick() {
        LoadWebViewContext(this, CommonUtil.AdWebURl)

    }

    fun OverAllMenuCountRequestAssignment(activity: Activity?, menuid: String) {

        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_menuid, menuid)
        jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
        if (CommonUtil.Priority == "p1") {
            jsonObject.addProperty(ApiRequestNames.Req_departmentid, 0)
            jsonObject.addProperty(ApiRequestNames.Req_sectionid, 0)
        } else {
            jsonObject.addProperty(ApiRequestNames.Req_departmentid, CommonUtil.DepartmentId)
            jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
        }
        if (CommonUtil.Priority == "p1" || CommonUtil.Priority == "p2" || CommonUtil.Priority == "p3") {
            jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.SenderAppId)
        } else {
            jsonObject.addProperty(ApiRequestNames.Req_appid, CommonUtil.ReceiverAppId)

        }
        jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
        appviewModelbase!!.getOverAllMenuCount(jsonObject, activity)
        Log.d("OverAllMenuCount_Req:", jsonObject.toString())

    }

}