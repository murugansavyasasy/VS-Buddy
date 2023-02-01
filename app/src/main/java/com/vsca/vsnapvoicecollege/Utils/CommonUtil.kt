package com.vsca.vsnapvoicecollege.Utils

import android.Manifest
import android.content.SharedPreferences
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.webkit.WebViewClient
import com.vsca.vsnapvoicecollege.Utils.MyWebViewClient
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.vsca.vsnapvoicecollege.Activities.BaseActivity
import com.vsca.vsnapvoicecollege.Activities.Noticeboard
import com.vsca.vsnapvoicecollege.Model.AWSUploadedFiles
import com.vsca.vsnapvoicecollege.Model.MenuDetailsResponse
import com.vsca.vsnapvoicecollege.Utils.CustomGestureDetector
import java.text.SimpleDateFormat
import java.util.*

object CommonUtil {
    @JvmField
    var Appid = 1

    @JvmField
    var VersionId = 13
    var UserDataList: List<LoginDetails>? = null

    //Login details
    var Priority = ""
    var MobileNUmber = ""
    var MemberId = 0
    var CollegeId = 0
    var DivisionId = ""
    var Courseid = ""
    var DepartmentId = ""
    var YearId = ""
    var SemesterId = ""
    var SectionId = ""
    var MemberName = ""
    var MemberType = ""
    var CollegeLogo = ""
    var isParentEnable = ""
    var CommonAdvertisement = ""
    var CommonAdImageSmall = ""
    var AdWebURl = ""
    var parentMenuCourseExam = 0
    var SenderAppId = 1
    var ReceiverAppId = 2
    var MenuCourseDetails = true
    var MenuDashboardHome = true
    var MenuNoticeBoard = true
    var MenuExamDetails = true
    var MenuCircular = true
    var MenuAssignment = true
    var MenuChat = true
    var MenuCommunication = true
    var MenuExamination = true
    var MenuAttendance = true
    var MenuEvents = true
    var MenuFaculty = true
    var MenuVideo = true
    var MenuCategoryCredit = true
    var MenuSemCredit = true
    var HeaderMenuNotification = false
    var CollegeNotice = "collegenotice"
    var DepartmentNotice = "departmentnotice"
    var CollegeCircular = "collegecircular"
    var DepartmentCircular = "departmentcircular"
    var UpcomingAssignment = "upcomingassignments"
    var PastAssignment = "pastassignments"
    var UpcomingEvents = "upcomingevents"
    var PastEvents = "pastevents"
    var UpcomingExams = "upcomingexams"
    var PastExams = "pastexams"
    var DeviceType = "android"

    var Unread = "0"
    var Read = "1"

    var isExpandAdapter = false
    var CommunicationisExpandAdapter = false

    var MenuIDCommunication: String? = null
    var MenuIDExamination: String? = null
    var MenuIDAssignment: String? = null
    var MenuIDCircular: String? = null
    var MenuIDNoticeboard: String? = null
    var MenuIDEvents: String? = null
    var MenuIDVideo: String? = null

    var SelcetedFileList = ArrayList<String>()
    var pathList = ArrayList<String>()
    var extension: String? = null
    var MenuList: ArrayList<MenuDetailsResponse> = ArrayList()

    val myCalendar = Calendar.getInstance()
    var cal = Calendar.getInstance()
    var mTimePicker: TimePickerDialog? = null

    var FromDate: String? = null

    var MenuTitle: String? = null
    var MenuDescription: String? = null
    var TermsNConditionUrl = "https://gradit.voicesnap.com/Home/TermsAndConditions"

    fun ApiAlert(activity: Activity?, msg: String?) {
        if (activity != null) {
            val dlg = AlertDialog.Builder(activity)
            dlg.setTitle("Info")
            dlg.setMessage(msg)
            dlg.setPositiveButton("OK") { dialog, which -> }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }
    }

    fun ApiAlertContext(activity: Context?, msg: String?) {
        if (activity != null) {
            val dlg = AlertDialog.Builder(activity)
            dlg.setTitle("Info")
            dlg.setMessage(msg)
            dlg.setPositiveButton("OK") { dialog, which ->

                CommonUtil.MenuAssignment = true
                CommonUtil.MenuCourseDetails = true
                CommonUtil.MenuExamDetails = true
                CommonUtil.MenuNoticeBoard = true
                CommonUtil.MenuDashboardHome = true
                CommonUtil.MenuCircular = true
                CommonUtil.MenuChat = true
                CommonUtil.MenuCommunication = true
                CommonUtil.MenuExamination = true
                CommonUtil.MenuAttendance = true
                CommonUtil.MenuEvents = true
                CommonUtil.MenuFaculty = true
                CommonUtil.MenuVideo = true
                CommonUtil.MenuCategoryCredit = true
                CommonUtil.MenuSemCredit = true
            }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }
    }

    fun ApiAlertFinish(activity: Activity?, msg: String?) {
        if (activity != null) {
            val dlg = AlertDialog.Builder(activity)
            dlg.setTitle("Info")
            dlg.setMessage(msg)
            dlg.setPositiveButton("OK") { dialog, which ->

                activity.finish()
                CommonUtil.MenuAssignment = true
                CommonUtil.MenuCourseDetails = true
                CommonUtil.MenuExamDetails = true
                CommonUtil.MenuNoticeBoard = true
                CommonUtil.MenuDashboardHome = true
                CommonUtil.MenuCircular = true
                CommonUtil.MenuChat = true
                CommonUtil.MenuCommunication = true
                CommonUtil.MenuExamination = true
                CommonUtil.MenuAttendance = true
                CommonUtil.MenuEvents = true
                CommonUtil.MenuFaculty = true
                CommonUtil.MenuVideo = true
                CommonUtil.MenuCategoryCredit = true
                CommonUtil.MenuSemCredit = true

            }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }
    }

    fun isNetworkConnected(activity: Activity): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun RequestPermission(activity: Activity?) {

        if (Priority.equals("p4") || Priority.equals("p5") || Priority.equals("p6")) {
            Log.d("testPrioruty", Priority)
            Dexter.withActivity(activity).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
            )
        } else {
            Log.d("testPriorutyelse", Priority)

            Dexter.withActivity(activity).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
            )
        }
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {

                    } else {
                        activity!!.finishAffinity()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { }
            .onSameThread()
            .check()
    }

    fun RequestCameraPermission(activity: Activity?) {
        Dexter.withActivity(activity).withPermissions(
            Manifest.permission.CAMERA
        )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {

                    } else {
                        activity!!.finishAffinity()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { }
            .onSameThread()
            .check()
    }

    fun HideKeyboard(activity: Activity?) {
        if (activity != null) {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun SetTheme(activity: Activity) {
        if (Priority == "p1") {
            activity.setTheme(R.style.Prinipal)
        } else if (Priority == "p2" || CommonUtil.Priority == "p3") {
            activity.setTheme(R.style.Staff)
        } else if (Priority == "p4") {
            activity.setTheme(R.style.Student)
        } else if (Priority == "p5" || CommonUtil.Priority == "p6") {
            activity.setTheme(R.style.ParentNonTeahingStaff)
        }
    }

    fun OnBackSetBottomMenuClickTrue() {
        MenuCourseDetails = true
        MenuExamDetails = true
        MenuNoticeBoard = true
        MenuDashboardHome = true
        MenuCircular = true
        MenuAssignment = true
        MenuChat = true
        MenuCommunication = true
        MenuExamination = true
        MenuAttendance = true
        MenuEvents = true
        MenuFaculty = true
        MenuVideo = true
        MenuCategoryCredit = true
        MenuSemCredit = true

    }

    fun OnMenuClicks(type: String) {

        if (type.equals("Home")) {
            MenuDashboardHome = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("Communication")) {

            MenuCommunication = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuExamination = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true

        } else if (type.equals("Chat")) {
            MenuChat = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("ExamList")) {
            MenuExamination = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("Attendance")) {
            MenuAttendance = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("Assignment")) {
            MenuAssignment = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("Circular")) {
            MenuCircular = false
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuEvents = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true

        } else if (type.equals("Noticeboard")) {
            MenuNoticeBoard = false
            CommonUtil.MenuCourseDetails = true
            CommonUtil.MenuExamDetails = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuVideo = true
            CommonUtil.MenuCategoryCredit = true
            CommonUtil.MenuSemCredit = true
        } else if (type.equals("Events")) {
            MenuEvents = false

            MenuNoticeBoard = true
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuFaculty = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true
        } else if (type.equals("Faculty")) {
            MenuFaculty = false

            MenuEvents = true
            MenuNoticeBoard = true
            MenuCourseDetails = true
            MenuExamDetails = true
            MenuNoticeBoard = true
            MenuDashboardHome = true
            MenuCircular = true
            MenuAssignment = true
            MenuChat = true
            MenuCommunication = true
            MenuExamination = true
            MenuAttendance = true
            MenuVideo = true
            MenuCategoryCredit = true
            MenuSemCredit = true

        } else if (type.equals("Video")) {
            CommonUtil.MenuVideo = false
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuCourseDetails = true
            CommonUtil.MenuExamDetails = true
            CommonUtil.MenuNoticeBoard = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuCategoryCredit = true
            CommonUtil.MenuSemCredit = true
        } else if (type.equals("CourseDetails")) {
            MenuCourseDetails = false
            CommonUtil.MenuExamDetails = true
            CommonUtil.MenuNoticeBoard = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuVideo = true
            CommonUtil.MenuCategoryCredit = true
            CommonUtil.MenuSemCredit = true
        } else if (type.equals("CategoryCredit")) {
            MenuCategoryCredit = false
            MenuCourseDetails = true
            CommonUtil.MenuExamDetails = true
            CommonUtil.MenuNoticeBoard = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuVideo = true
            CommonUtil.MenuSemCredit = true
        } else if (type.equals("SemCredit")) {
            MenuSemCredit = false
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuCourseDetails = true
            CommonUtil.MenuExamDetails = true
            CommonUtil.MenuNoticeBoard = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuVideo = true
            CommonUtil.MenuCategoryCredit = true
        } else if (type.equals("ExamApp")) {
            CommonUtil.MenuExamDetails = false
            CommonUtil.MenuCourseDetails = true
            CommonUtil.MenuNoticeBoard = true
            CommonUtil.MenuDashboardHome = true
            CommonUtil.MenuCircular = true
            CommonUtil.MenuAssignment = true
            CommonUtil.MenuChat = true
            CommonUtil.MenuCommunication = true
            CommonUtil.MenuExamination = true
            CommonUtil.MenuAttendance = true
            CommonUtil.MenuEvents = true
            CommonUtil.MenuFaculty = true
            CommonUtil.MenuVideo = true
            CommonUtil.MenuCategoryCredit = true
            CommonUtil.MenuSemCredit = true
        }

    }

    fun Toast(activity: Activity?, msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }


    fun Datepicker(activity: Activity?, lblFromDate: TextView) {

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(lblFromDate!!)

            }
        }

        DatePickerDialog(
            activity!!, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun updateLabel(txt_date: TextView) {

        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_date.text = sdf.format(cal.time)
        FromDate = txt_date.text.toString()

    }
}