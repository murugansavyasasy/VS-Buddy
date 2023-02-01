package com.vsca.vsnapvoicecollege.Activities

import butterknife.BindView
import com.vsca.vsnapvoicecollege.R
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import butterknife.ButterKnife
import androidx.recyclerview.widget.DefaultItemAnimator
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import android.widget.TextView
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.vsca.vsnapvoicecollege.Adapters.DashboardParent
import com.vsca.vsnapvoicecollege.Model.*
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.DeviceType
import com.vsca.vsnapvoicecollege.Utils.CommonUtil.MobileNUmber
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import java.util.ArrayList

class DashBoard : BaseActivity() {
    @JvmField
    @BindView(R.id.lblSwipedown)
    var lblSwipedown: TextView? = null

    @JvmField
    @BindView(R.id.idRVCategories)
    var idRVCategories: RecyclerView? = null

    @JvmField
    @BindView(R.id.LayoutBottomMenus)
    var LayoutBottomMenus: CoordinatorLayout? = null
    var adapter: DashboardParent? = null
    var DashboardData: List<DashboardTypeResponse> = ArrayList()
    var DashboardDetails: List<DashboardDetailsDataResponse> = ArrayList()
    private val dashboardOverallList = ArrayList<DashboardOverall>()
    private val dashboardNoticeboardlist = ArrayList<DashboardSubItems>()
    private val adimageList1 = ArrayList<DashboardSubItems>()
    private val adimageList2 = ArrayList<DashboardSubItems>()
    private val dashboardCircularlist = ArrayList<DashboardSubItems>()
    private val dashboardEmergencyVoicelist = ArrayList<DashboardSubItems>()
    private val dashboardRecentVoicelist = ArrayList<DashboardSubItems>()
    private val dashboardAssignmentList = ArrayList<DashboardSubItems>()
    var dashboardAttendanceList: ArrayList<DashboardSubItems>? = null

    var category: String? = null
    var deviceToken: String? = null
    var order = 0

    var previous_add_id = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        CommonUtil.RequestPermission(this)
        ActionBarMethod(this@DashBoard)

        MenuBottomType()

        CommonUtil.OnMenuClicks("Home")

        FirebaseMessaging.getInstance().setAutoInitEnabled(true)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("Fetching FCM ", task.exception.toString())
                    return@OnCompleteListener
                }
                deviceToken = task.result
                SharedPreference.putDeviceToken(this, deviceToken!!)

                DeviceTokenRequest()
            })

        appviewModelbase!!.UpdateDeviceTokenMutableLiveData?.observe(
            this,
            Observer<StatusMessageResponse?> { response ->
                DashBoardRequest()
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    if (status == 1) {
                    } else {
                    }
                } else {
                }
            })

        dashboardViewModel!!.dashboardLivedata
            ?.observe(this, Observer<DashBoardResponse?> { response ->
                if (response != null) {
                    val status = response.status
                    val message = response.message
                    UserMenuRequest(this@DashBoard)

                    dashboardOverallList.clear()
                    if (status == 1) {
                        UserMenuRequest(this@DashBoard)
                        DashboardData = response.data!!
                        Log.d("DashboardResponse", DashboardData.size.toString())
                        SetDashboardValues()
                    } else {
                        CommonUtil.ApiAlert(this@DashBoard, message)
                    }
                } else {
                    UserMenuRequest(this@DashBoard)

                }
            })

        dashboardAttendanceList = ArrayList()

        imgRefresh!!.setOnClickListener(View.OnClickListener { DashBoardRequest() })
    }

    private fun DeviceTokenRequest() {
        var mobilenumber = SharedPreference.getSH_MobileNumber(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_mobileNumber, mobilenumber)
        jsonObject.addProperty(ApiRequestNames.Req_devicetoken, deviceToken)
        jsonObject.addProperty(ApiRequestNames.Req_devicetype, DeviceType)
        Log.d("deviceToken", deviceToken!!)
        appviewModelbase!!.UpdateDeviceToken(jsonObject, this)
    }


    override val layoutResourceId: Int
        protected get() = R.layout.bottom_menu_swipe

    private fun SetDashboardValues() {
        dashboardOverallList.clear()
        for (i in DashboardData.indices) {
            category = DashboardData[i].type
            order = DashboardData[i].order
            Log.d("Categories", category!!)
            if (category == "Ad" && order == 1) {
                adimageList1.clear()
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val addimage = DashboardDetails[j].add_image
                    val adBackgroundImage = DashboardDetails[j].background_image
                    CommonUtil.CommonAdvertisement = DashboardDetails[0].background_image!!
                    CommonUtil.CommonAdImageSmall = DashboardDetails[0].add_image.toString()
                    CommonUtil.AdWebURl = DashboardDetails[0].add_url.toString()
                    adimageList1.add(DashboardSubItems(addimage, adBackgroundImage))
                }
            }
            if (category == "Ad" && order == 2) {
                adimageList2.clear()
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val addimage = DashboardDetails[j].background_image
                    val adBackgroundImage = DashboardDetails[j].background_image
                    adimageList2.add(DashboardSubItems(addimage, adBackgroundImage))
                }
            }
            if (category == "Circular") {
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val description = DashboardDetails[j].description
                    val title = DashboardDetails[j].title
                    val createddate = DashboardDetails[j].createddate
                    val createdtime = DashboardDetails[j].createdtime
                    val filepaths = DashboardDetails[j].filepaths
                    dashboardCircularlist.add(
                        DashboardSubItems(
                            title,
                            description,
                            createddate,
                            createdtime,
                            filepaths as ArrayList<String>
                        )
                    )
                }
            }
            if (category == "Notice Board") {
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val description = DashboardDetails[j].topicbody
                    val title = DashboardDetails[j].topicheading
                    val createddate = DashboardDetails[j].createddate
                    val createdtime = DashboardDetails[j].createdtime
                    dashboardNoticeboardlist.add(
                        DashboardSubItems(
                            title,
                            "",
                            description,
                            createddate,
                            createdtime,
                            "",
                            category
                        )
                    )
                }
            }
            if (category == "Emergency Notification") {
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val description = DashboardDetails[j].description
                    val VoiceFilepath = DashboardDetails[j].voicefilepath
                    val membername = DashboardDetails[j].membername
                    val duration = DashboardDetails[j].duration
                    val createdon = DashboardDetails[j].createdon
                    val detailsid = DashboardDetails[j].detailsid
                    dashboardEmergencyVoicelist.add(
                        DashboardSubItems(
                            description,
                            VoiceFilepath,
                            membername,
                            duration,
                            createdon,
                            category,
                            detailsid
                        )
                    )
                }
            }
            if (category == "Recent Notifications") {
                DashboardDetails = DashboardData[i].data!!
                for (j in DashboardDetails.indices) {
                    val description = DashboardDetails[j].description
                    val content = DashboardDetails[j].content
                    val membername = DashboardDetails[j].sentbyname
                    val duration = DashboardDetails[j].duration
                    val createdon = DashboardDetails[j].createdondate
                    val recentType = DashboardDetails[j].typ
                    val Createdontime = DashboardDetails[j].createdontime
                    val detailsID = DashboardDetails[j].id

                    dashboardRecentVoicelist.add(
                        DashboardSubItems(
                            description,
                            membername,
                            recentType,
                            createdon,
                            Createdontime,
                            content,
                            duration,
                            category,
                            detailsID
                        )
                    )
                }
            }

            if (category == "Attendance") {

                DashboardDetails = DashboardData[i].data!!

                Log.d("DashboardDetailssize", DashboardDetails.size.toString())

                if (DashboardDetails.size == 1) {
                    var message = DashboardDetails.get(0).message
                    if (message.equals("Today's attendance is not yet available")) {

                    }

                } else {
                    for (k in DashboardDetails.indices) {

                        val subjectname = DashboardDetails[k].subjectname
                        val attendancetype = DashboardDetails[k].attendancetype
                        val attendancedate = DashboardDetails[k].attendancedate
                        dashboardAttendanceList!!.add(
                            DashboardSubItems(
                                subjectname,
                                attendancetype,
                                attendancedate,
                            )
                        )
                    }
                }
            }


            //Setting into adapter
            if (category == "Ad" && order == 1) {
                dashboardOverallList.add(DashboardOverall(category!!, adimageList1))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
            if (category == "Ad" && order == 2) {
                dashboardOverallList.add(DashboardOverall(category!!, adimageList2))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else if (category == "Notice Board") {
                dashboardOverallList.add(DashboardOverall(category!!, dashboardNoticeboardlist))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else if (category == "Circular") {
                dashboardOverallList.add(DashboardOverall(category!!, dashboardCircularlist))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else if (category == "Emergency Notification") {
                dashboardOverallList.add(DashboardOverall(category!!, dashboardEmergencyVoicelist))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else if (category == "Recent Notifications") {
                dashboardOverallList.add(DashboardOverall(category!!, dashboardRecentVoicelist))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } else if (category == "Attendance") {
                dashboardOverallList.add(DashboardOverall(category!!, dashboardAttendanceList!!))
                adapter = DashboardParent(dashboardOverallList, this@DashBoard)
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@DashBoard)
                idRVCategories!!.layoutManager = mLayoutManager
                idRVCategories!!.itemAnimator = DefaultItemAnimator()
                idRVCategories!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }

        }
    }

    private fun DashBoardRequest() {
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
        jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
        jsonObject.addProperty(ApiRequestNames.Req_priority, CommonUtil.Priority)
        dashboardViewModel!!.dashboard(jsonObject, this@DashBoard)
        Log.d("DahsboardRequest:", jsonObject.toString())
    }

    override fun onResume() {
        super.onResume()
    }


}