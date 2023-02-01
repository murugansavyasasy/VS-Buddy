package com.vsca.vsnapvoicecollege.Utils

import android.content.SharedPreferences
import com.vsca.vsnapvoicecollege.Utils.SharedPreference
import android.app.Activity
import android.content.Context
import android.webkit.WebViewClient
import com.vsca.vsnapvoicecollege.Utils.MyWebViewClient
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import com.vsca.vsnapvoicecollege.R
import com.vsca.vsnapvoicecollege.Model.LoginDetails
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import com.vsca.vsnapvoicecollege.Utils.CustomGestureDetector

object SharedPreference {
    const val SH_PREF = "Mypref"
    const val SH_TermsConditionsPref = "TermsConditions"
    const val SH_Agreed = "Agreed"
    const val SH_Baseurl = "baseurl"
    const val SH_Countryid = "Countryid"
    const val SH_Country = "country"
    const val SH_Mobilelength = "mobilenumberlen"
    const val SH_MobileNumber = "mobilenumber"
    const val SH_Password = "password"
    const val SH_DeviceToken = "DeviceToken"
    const val SH_TermsNcondition = "termsandcondition"
    const val SH_Faq = "faq"
    const val SH_Help = "help"
    const val SH_Privacypolicy = "privacypolicy"
    const val SH_ImageCount = "imagecount"
    const val SH_PdfCount = "pdfcount"
    const val SH_AttendanceDayCount = "attendancedaycount"
    const val SH_EventPhotosCount = "eventphotoscount"
    const val SH_Playstorelink = "playstorelink"
    const val SH_VersionTitle = "versionalerttitle"
    const val SH_VersionAlertContent = "versionalertcontent"
    const val SH_PlaystoreMarketID = "playstoremarketid"
    const val SH_VideoJson = "videojson"
    const val SH_VideosizeLimit = "videosizelimit"
    const val SH_Videosizealert = "videosizealert"
    const val SH_EmergencyDuration = "emergencyduration"
    const val SH_NonEmergencyDuration = "nonemergencyduration"


    const val SH_UnreadCount = "Unread"
    const val SH_ReadCount = "read"
    const val SH_ExamUpcomingCount = "examupcoming"
    const val SH_ExamPastCount = "exampast"
    const val SH_UpcomingAssignmentCount = "UpomingAssignment"
    const val SH_PastAssignmentCount = "pastassignment"
    const val SH_DepartmentCircularCount = "departmentcircular"
    const val SH_CollegeCircularCount = "collegecircular"
    const val SH_NoticeboardDepartmentCount = "noticeboarddepartment"
    const val SH_NoticeboardCollegeCount = "noticeboardcollege"
    const val SH_UpcomingEventCount = "upcomingEvent"
    const val SH_PastEventCount = "pastevent"
    const val SH_VideoCount = "video"


    fun putagreed(activity: Context, agreed: Boolean) {
        val sharepref = activity.getSharedPreferences(SH_TermsConditionsPref, Context.MODE_PRIVATE)
        val ed = sharepref.edit()
        ed.putBoolean(SH_Agreed, agreed)
        ed.apply()
        ed.commit()
        return
    }

    fun getSH_agreed(activity: Context): Boolean {
        return activity.getSharedPreferences(SH_TermsConditionsPref, Context.MODE_PRIVATE)
            .getBoolean(SH_Agreed, false)
    }

    fun putCountryDetails(
        activity: Activity,
        countryID: String?,
        country: String?,
        mobilelength: String?,
        baseurl: String?
    ) {
        val sharepref = activity.getSharedPreferences(SH_TermsConditionsPref, 0)
        val ed = sharepref.edit()
        ed.putString(SH_Country, country)
        ed.putString(SH_Countryid, countryID)
        ed.putString(SH_Mobilelength, mobilelength)
        ed.putString(SH_Baseurl, baseurl)
        ed.apply()
        ed.commit()
        return
    }

    fun putVersionCheckData(
        activity: Activity,
        faq: String?,
        help: String?,
        privacypolicy: String?,
        termsandcondition: String?,
        imagecount: String?,
        pdfcount: String?,
        eventphotoscount: String?,
        attendancedaycount: String?,
        playstorelink: String?,
        versionalerttitle: String?,
        versionalertcontent: String?,
        playstoremarketid: String?,
        videojson: String?,
        videosizelimit: String?,
        videosizealert: String?,
        emergencyduration: String?,
        nonemergencyduration: String?
    ) {
        val sharepref = activity.getSharedPreferences(SH_PREF, 0)
        val ed = sharepref.edit()
        ed.putString(SH_Faq, faq)
        ed.putString(SH_Help, help)
        ed.putString(SH_Privacypolicy, privacypolicy)
        ed.putString(SH_TermsNcondition, termsandcondition)
        ed.putString(SH_ImageCount, imagecount)
        ed.putString(SH_PdfCount, pdfcount)
        ed.putString(SH_EventPhotosCount, eventphotoscount)
        ed.putString(SH_PdfCount, pdfcount)
        ed.putString(SH_AttendanceDayCount, attendancedaycount)
        ed.putString(SH_Playstorelink, playstorelink)
        ed.putString(SH_VersionTitle, versionalerttitle)
        ed.putString(SH_VersionAlertContent, versionalertcontent)
        ed.putString(SH_PlaystoreMarketID, playstoremarketid)
        ed.putString(SH_VideoJson, videojson)
        ed.putString(SH_VideosizeLimit, videosizelimit)
        ed.putString(SH_Videosizealert, videosizealert)
        ed.putString(SH_EmergencyDuration, emergencyduration)
        ed.putString(SH_NonEmergencyDuration, nonemergencyduration)
        ed.apply()
        ed.commit()
        return
    }

    fun getSH_Faq(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE).getString(SH_Faq, "")
    }

    fun getSH_Help(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_Help, "")
    }

    fun getSH_Privacypolicy(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_Privacypolicy, "")
    }

    fun getSH_TermsNcondition(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_TermsNcondition, "")
    }

    fun putLoginDetails(activity: Activity, mobilenumber: String?, password: String?) {
        val sharepref = activity.getSharedPreferences(SH_PREF, 0)
        val ed = sharepref.edit()
        ed.putString(SH_MobileNumber, mobilenumber)
        ed.putString(SH_Password, password)
        ed.apply()
        ed.commit()
        return
    }

    fun getSH_Baseurl(activity: Activity): String? {
        return activity.getSharedPreferences(SH_TermsConditionsPref, Context.MODE_PRIVATE)
            .getString(SH_Baseurl, "")
    }

    fun getCountryId(activity: Activity): String? {
        return activity.getSharedPreferences(SH_TermsConditionsPref, Context.MODE_PRIVATE)
            .getString(SH_Countryid, "")
    }

    fun getSH_MobileNumber(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_MobileNumber, "")
    }

    fun getSH_Password(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_Password, "")
    }


    fun putMenuCountDetails(
        activity: Activity,
        Unread: String?,
        read: String?,
        examupcoming: String?,
        exampast: String?,
        UpomingAssignment: String?,
        pastassignment: String?,
        departmentcircular: String?,
        collegecircular: String?,
        noticeboarddepartment: String?,
        noticeboardcollege: String?,
        upcomingEvent: String?,
        pastevent: String?,
        video: String?
    ) {
        val sharepref = activity.getSharedPreferences(SH_PREF, 0)
        val ed = sharepref.edit()
        ed.putString(SH_UnreadCount, Unread)
        ed.putString(SH_ReadCount, read)
        ed.putString(SH_ExamUpcomingCount, examupcoming)
        ed.putString(SH_ExamPastCount, exampast)
        ed.putString(SH_UpcomingAssignmentCount, UpomingAssignment)
        ed.putString(SH_PastAssignmentCount, pastassignment)
        ed.putString(SH_DepartmentCircularCount, departmentcircular)
        ed.putString(SH_CollegeCircularCount, collegecircular)
        ed.putString(SH_NoticeboardDepartmentCount, noticeboarddepartment)
        ed.putString(SH_NoticeboardCollegeCount, noticeboardcollege)
        ed.putString(SH_UpcomingEventCount, upcomingEvent)
        ed.putString(SH_PastEventCount, pastevent)
        ed.putString(SH_VideoCount, video)
        ed.apply()
        ed.commit()
        return
    }


    fun getSH_UnreadCount(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_UnreadCount, "")
    }

    fun getSH_ReadCount(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_ReadCount, "")
    }

    fun getSH_ExamUpcoming(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_ExamUpcomingCount, "")
    }

    fun getSH_ExamPast(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_ExamPastCount, "")
    }

    fun getSH_AssignmentUpcoming(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_UpcomingAssignmentCount, "")
    }

    fun getSH_AssignmentPast(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_PastAssignmentCount, "")
    }
    fun getSh_CircularDepartment(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_DepartmentCircularCount, "")
    }

    fun getSH_CircularCollege(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_CollegeCircularCount, "")
    }
    fun getSH_EventUpcoming(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_UpcomingEventCount, "")
    }

    fun getSH_EventPast(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_PastEventCount, "")
    }

    fun getSH_Video(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_VideoCount, "")
    }

    fun getSH_DepartmentNoticeboard(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_NoticeboardDepartmentCount, "")
    }

    fun getSH_CollegeNoticeboard(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_NoticeboardDepartmentCount, "")
    }

    fun clearShLogin(activity: Activity) {
        val sharepref = activity.getSharedPreferences(SH_PREF, 0)
        val ed = sharepref.edit()
        ed.clear()
        ed.apply()
        ed.commit()
        return
    }


        fun putDeviceToken(activity: Context, fcmdevicetoken: String) {
            val sharepref = activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            val ed = sharepref.edit()
            ed.putString(SH_DeviceToken, fcmdevicetoken)
            ed.apply()
            ed.commit()
            return
    }

    fun getSH_DeviceToken(activity: Activity): String? {
        return activity.getSharedPreferences(SH_PREF, Context.MODE_PRIVATE)
            .getString(SH_DeviceToken, "")
    }
}