package com.vsca.vsnapvoicecollege.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.vsca.vsnapvoicecollege.Adapters.AttendanceAdapter
import com.vsca.vsnapvoicecollege.Adapters.LeaveHistoryAdapter
import com.vsca.vsnapvoicecollege.Interfaces.LeaveHistoryListener
import com.vsca.vsnapvoicecollege.Model.AttendanceData
import com.vsca.vsnapvoicecollege.Model.GetAdvertisementResponse
import com.vsca.vsnapvoicecollege.Model.LeaveHistoryData
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import com.vsca.vsnapvoicecollege.ViewModel.App
import java.text.SimpleDateFormat
import java.util.*


class Attendance : BaseActivity() {
    var attendanceAdapter: AttendanceAdapter? = null
    var leaveHistoryAdapter: LeaveHistoryAdapter? = null

    @JvmField
    @BindView(R.id.recyclerAttendance)
    var recyclerAttendance: RecyclerView? = null

    @JvmField
    @BindView(R.id.recyclerLeaveHistory)
    var recyclerLeaveHistory: RecyclerView? = null

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
    @BindView(R.id.CalendarView)
    var CalendarView: CalendarView? = null

    @JvmField
    @BindView(R.id.lnrCalendar)
    var lnrCalendar: LinearLayout? = null

    @JvmField
    @BindView(R.id.LayoutNoAttendanceData)
    var LayoutNoAttendanceData: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.lblNoDataFound)
    var lblNoDataFound: TextView? = null

    @JvmField
    @BindView(R.id.imgApplyleave)
    var imgApplyleave: ImageView? = null

    @JvmField
    @BindView(R.id.lblAttendanceCount)
    var lblAttendanceCount: TextView? = null

    @JvmField
    @BindView(R.id.lblLeaveHistoryCount)
    var lblLeaveHistoryCount: TextView? = null

    var attendanceType = true
    var LeaveHistoryType = true
    var GetAttendanceData: List<AttendanceData> = ArrayList()
    var LeaveHistoryLiveData: ArrayList<LeaveHistoryData> = ArrayList()
    var departmentSize = 0
    var CollegeSize = 0
    var SelectedDate: String? = null
    var AdBackgroundImage: String? = null
    var AdSmallImage: String? = null
    var AdWebURl: String? = null
    var PreviousAddId: Int = 0

    override var appViewModel: App? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtil.SetTheme(this)

        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        ButterKnife.bind(this)
        ActionBarMethod(this)
        TabDepartmentColor()

        val selectedDate: Long = CalendarView!!.getDate()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        SelectedDate = simpleDateFormat.format(selectedDate)

        appViewModel = ViewModelProvider(this).get(App::class.java)
        appViewModel!!.init()
        if (CommonUtil.Priority.equals("p4") ||
            CommonUtil.Priority.equals("p5") ||
            CommonUtil.Priority.equals("p6")
        ) {
            AttendanceRequest(SelectedDate!!)
        }

        MenuBottomType()

        CommonUtil.OnMenuClicks("Attendance")

        lblMenuTitle!!.setText(R.string.txt_attendance)
        lblDepartment!!.setText(R.string.txt_attendance)
        lblCollege!!.setText(R.string.txt_leave_history)

        imgRefresh!!.setOnClickListener(View.OnClickListener {
            if (attendanceType) {
                attendanceType = true
                if (CommonUtil.Priority.equals("p4") ||
                    CommonUtil.Priority.equals("p5") ||
                    CommonUtil.Priority.equals("p6")
                ) {
                    AttendanceRequest(SelectedDate!!)
                }
            } else {
                attendanceType = false
//                AttendanceRequest(attendanceType)
            }
        })
        CalendarView!!.setMaxDate(Date().getTime())

        CalendarView!!.setOnDateChangeListener(OnDateChangeListener { calendarView, year, month, dayOfMonth ->
            SelectedDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            Log.d("SelectedDateClick", SelectedDate!!)
            if (CommonUtil.Priority.equals("p4") ||
                CommonUtil.Priority.equals("p5") ||
                CommonUtil.Priority.equals("p6")
            ) {

                AttendanceRequest(SelectedDate!!)

            }
            Log.d("AttendanceType", attendanceType.toString())
        })
        appViewModel!!.AdvertisementLiveData?.observe(this){
                response ->

            if(response != null){


            }
        }
        appViewModel!!.AttendanceDatesLiveData?.observe(this) { response ->
            if (response != null) {
                UserMenuRequest(this)

                GetLeaveHistory(LeaveHistoryType)

                val status = response.status
                val message = response.message

                if (status == 1) {
                    CalendarHeight()

                    LayoutNoAttendanceData!!.visibility = View.GONE
                    recyclerAttendance!!.visibility = View.VISIBLE
                    recyclerLeaveHistory!!.visibility = View.GONE
                    GetAttendanceData = response.data!!
                    lblAttendanceCount!!.visibility = View.VISIBLE
                    lblAttendanceCount!!.text = GetAttendanceData.size.toString()

                    Log.d("AttendanceDataSize", GetAttendanceData.size.toString())

                    attendanceAdapter = AttendanceAdapter(GetAttendanceData, this@Attendance)
                    val mLayoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(this@Attendance)
                    recyclerAttendance!!.layoutManager = mLayoutManager
                    recyclerAttendance!!.itemAnimator = DefaultItemAnimator()
                    recyclerAttendance!!.adapter = attendanceAdapter
                    recyclerAttendance!!.recycledViewPool.setMaxRecycledViews(0, 80)
                    attendanceAdapter!!.notifyDataSetChanged()

                } else {
                    CalendarHeight()
                    LayoutNoAttendanceData!!.visibility = View.VISIBLE
                    recyclerAttendance!!.visibility = View.GONE
                }
            } else {
                CalendarHeight()
                LayoutNoAttendanceData!!.visibility = View.VISIBLE
                recyclerAttendance!!.visibility = View.GONE
            }
        }

        appViewModel!!.LeaveHistoryLiveData?.observe(this) { response ->
            if (response != null) {
                val status = response.status
                val message = response.message

                if (status == 1) {
                    lblNoDataFound!!.visibility = View.GONE
                    recyclerLeaveHistory!!.visibility = View.VISIBLE
                    lblLeaveHistoryCount!!.visibility = View.VISIBLE
                    LeaveHistoryLiveData = response.data!!
                    lblLeaveHistoryCount!!.text = LeaveHistoryLiveData.size.toString()

                    Log.d("LeaveHistoryDataSize", LeaveHistoryLiveData.size.toString())
                    if (!LeaveHistoryType) {
                        leaveHistoryAdapter =
                            LeaveHistoryAdapter(LeaveHistoryLiveData, this@Attendance,
                                object : LeaveHistoryListener {
                                    override fun onLeaveClick(holder: LeaveHistoryAdapter.MyViewHolder, item: LeaveHistoryData) {


                                    }
                                })

                        val mLayoutManager: RecyclerView.LayoutManager =
                            LinearLayoutManager(this@Attendance)
                        recyclerLeaveHistory!!.layoutManager = mLayoutManager
                        recyclerLeaveHistory!!.itemAnimator = DefaultItemAnimator()
                        recyclerLeaveHistory!!.adapter = leaveHistoryAdapter
                        recyclerLeaveHistory!!.recycledViewPool.setMaxRecycledViews(0, 80)
                        leaveHistoryAdapter!!.notifyDataSetChanged()
                    } else {
                        if (!LeaveHistoryType) {

                            recyclerLeaveHistory!!.visibility = View.GONE
                            recyclerAttendance!!.visibility = View.VISIBLE
                            LayoutNoAttendanceData!!.visibility = View.VISIBLE
                        }

                    }

                } else {
                    if (!LeaveHistoryType) {

                        LayoutNoAttendanceData!!.visibility = View.GONE
                        lblNoDataFound!!.visibility = View.VISIBLE
                        recyclerLeaveHistory!!.visibility = View.GONE
                    }
                }
            } else {
                if (!LeaveHistoryType) {

                    lblNoDataFound!!.visibility = View.VISIBLE
                    recyclerLeaveHistory!!.visibility = View.GONE
                }
            }
        }

    }

    private fun convertDate(s: String, s1: String) {

    }

    fun CalendarHeight() {

//        val params: ViewGroup.LayoutParams = lnrCalendar!!.getLayoutParams()
//
//        params.height = 400
//        lnrCalendar!!.setLayoutParams(params)
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_attendance

    private fun AttendanceRequest(SelectedDate: String) {

        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_userid, CommonUtil.MemberId)
            jsonObject.addProperty(ApiRequestNames.Req_Attendancedate, SelectedDate)
            jsonObject.addProperty(ApiRequestNames.Req_sectionid, CommonUtil.SectionId)
            appViewModel!!.getAttendanceReceiver(jsonObject, this)
            Log.d("AttendanceListRequest:", jsonObject.toString())
        }

    }

    private fun GetLeaveHistory(type: Boolean) {
        val jsonObject = JsonObject()
        run {
            jsonObject.addProperty(ApiRequestNames.Req_collegeid, CommonUtil.CollegeId)
            jsonObject.addProperty(ApiRequestNames.Req_staffid, CommonUtil.MemberId)
            appViewModel!!.getleaveHistory(jsonObject, this)
            Log.d("LeaveHistoryRequest:", jsonObject.toString())
        }

    }

    @OnClick(R.id.imgApplyleave)
    fun ApplyLeave() {
        val i = Intent(this, ApplyLeave::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)

    }

    @OnClick(R.id.LayoutDepartment)
    fun departmentClick() {
        TabDepartmentColor()

        attendanceType = true
        LeaveHistoryType = true
        recyclerAttendance!!.visibility = View.VISIBLE
        recyclerLeaveHistory!!.visibility = View.GONE
        if (CommonUtil.Priority.equals("p4") ||
            CommonUtil.Priority.equals("p5") ||
            CommonUtil.Priority.equals("p6")
        ) {
            AttendanceRequest(SelectedDate!!)
        }
        lnrCalendar!!.visibility = View.VISIBLE
        imgApplyleave!!.visibility = View.GONE

        bottomsheetStateCollpased()
    }

    @OnClick(R.id.LayoutCollege)
    fun collegeClick() {
        bottomsheetStateCollpased()
        attendanceType = false
        LeaveHistoryType = false
        GetLeaveHistory(LeaveHistoryType)
        TabCollegeColor()
        lnrCalendar!!.visibility = View.GONE
        LayoutNoAttendanceData!!.visibility = View.GONE
        recyclerAttendance!!.visibility = View.GONE
        recyclerLeaveHistory!!.visibility = View.VISIBLE
        imgApplyleave!!.visibility = View.VISIBLE


    }


    override fun onBackPressed() {
        CommonUtil.OnBackSetBottomMenuClickTrue()
        super.onBackPressed()
    }


    override fun onResume() {
        var AddId: Int = 1
        PreviousAddId = PreviousAddId + 1;

        AdForCollegeApi()

        super.onResume()
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

        PreviousAddId = PreviousAddId + 1;
        Log.d("PreviousAddId", PreviousAddId.toString())
    }


}