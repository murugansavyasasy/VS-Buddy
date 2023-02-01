package com.vsca.vsnapvoicecollege.ViewModel

import androidx.lifecycle.AndroidViewModel
import com.vsca.vsnapvoicecollege.Repository.AppServices
import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import android.app.Activity
import android.app.Application
import android.content.Context
import com.vsca.vsnapvoicecollege.Model.*
import com.vsca.vsnapvoicecollege.Model.StatusMessageResponse
import com.vsca.vsnapvoicecollege.SenderModel.GetCourseDepartmentResposne
import com.vsca.vsnapvoicecollege.SenderModel.GetDepartmentResponse
import com.vsca.vsnapvoicecollege.SenderModel.GetDivisionResponse
import com.vsca.vsnapvoicecollege.SenderModel.SenderStatusMessageData

class App(application: Application) : AndroidViewModel(application) {
    private var apiRepositories: AppServices? = null
    var courseDetailsResposneLiveData: LiveData<GetCourseDetailsResposne?>? = null
        private set
    private var ProfileDetailsResponseLiveData: LiveData<GetProfileResponse?>? = null
    var examApplicationResponseLiveData: LiveData<GetExamApplicationResponse?>? = null
        private set
    var noticeBoardResponseLiveData: LiveData<GetNoticeboardResposne?>? = null
        private set
    var circularResponseLiveData: LiveData<GetCircularListResponse?>? = null
        private set
    var assignmentListResponseLiveData: LiveData<GetAssignmentListResponse?>? = null
        private set
    var assignmentCountResponseLiveData: LiveData<GetAssignmentCountResponse?>? = null
        private set
    var assignmentViewContentResponseLiveData: LiveData<GetAssignmentViewContentResponse?>? = null
        private set

    var appreadstatusresponseLiveData: LiveData<StatusMessageResponse?>? = null
    var eventListbyTypeliveData: LiveData<GetEventListbyTypeResponse?>? = null
    var communicationLiveData: LiveData<GetCommunicationResponse?>? = null
    var OverAllMenuResponseLiveData: LiveData<GetOverAllCountResposne?>? = null
    var ExamListResponseLiveData: LiveData<GetExamListResponse?>? = null
    var ExamMarkListLiveData: LiveData<ExamMarkListResponse?>? = null
    var VideoListLiveData: LiveData<GetVideoListResponse?>? = null
    var GetStaffDetailsLiveData: LiveData<GetStaffDetailsResponse?>? = null
    var SemesterSectionLiveData: LiveData<SemesterAndSectionListResposne?>? = null
    var FacultyLiveData: LiveData<GetFacultyListResponse?>? = null
    var CategoryTypeLiveData: LiveData<GetCategoryTypesResponse?>? = null
    var CategoryWiseCreditLiveData: LiveData<GetCategoryWiseCreditResponse?>? = null
    var SemesterTypeLiveData: LiveData<GetSemesterWiseTypeResponse?>? = null
    var SemesterWiseCreditLiveData: LiveData<GetSemesterWiseCreditResponse?>? = null
    var SemesterWiseCreditAllLiveData: LiveData<GetSemesterWiseCreditALLResponse?>? = null
    var AttendanceDatesLiveData: LiveData<AttendanceResponse?>? = null
    var LeaveHistoryLiveData: LiveData<LeaveHistoryResponse?>? = null
    var LeaveTypeLiveData: LiveData<LeaveTypeResponse?>? = null
    var ChangePasswordLiveData: LiveData<StatusMessageResponse?>? = null
    var AdvertisementLiveData: LiveData<GetAdvertisementResponse?>? = null
    var UpdateDeviceTokenMutableLiveData: LiveData<StatusMessageResponse?>? = null
    var VideoRestrictContentMutableLiveData: LiveData<VideoRestrictionContentResponse?>? = null


    var GetDivisionMutableLiveData:LiveData<GetDivisionResponse?>? = null
    var GetDepartmentMutableLiveData:LiveData<GetDepartmentResponse?>? = null
    var GetCourseDepartmentMutableLiveData:LiveData<GetCourseDepartmentResposne?>? = null
    var SendSMSToEntireCollegeLiveData:LiveData<SenderStatusMessageData?>? = null
    var SendSMStoParticularMutableData:LiveData<SenderStatusMessageData?>? = null

    fun init() {
        apiRepositories = AppServices()
        courseDetailsResposneLiveData = apiRepositories!!.courseDetailsResposneLiveData
        examApplicationResponseLiveData = apiRepositories!!.examApplicationResponseLiveData
        ProfileDetailsResponseLiveData = apiRepositories!!.profileResponseLiveData
        noticeBoardResponseLiveData = apiRepositories!!.noticeboardLiveData
        circularResponseLiveData = apiRepositories!!.circularListResponseLiveData
        assignmentListResponseLiveData = apiRepositories!!.assignmentListResponseLiveData
        assignmentCountResponseLiveData = apiRepositories!!.assignmentCountResponseLiveData
        assignmentViewContentResponseLiveData = apiRepositories!!.assignmentViewContentResponseLiveData
        appreadstatusresponseLiveData = apiRepositories!!.appreadstatusLiveData
        OverAllMenuResponseLiveData = apiRepositories!!.OverAllCountMenuLiveData
        eventListbyTypeliveData = apiRepositories!!.getEventListLiveData
        communicationLiveData = apiRepositories!!.getCommunicationLiveData
        ExamListResponseLiveData = apiRepositories!!.getExamListLiveData
        ExamMarkListLiveData = apiRepositories!!.getExamMarkListLiveData
        VideoListLiveData = apiRepositories!!.getVideoListMutableLiveData
        GetStaffDetailsLiveData = apiRepositories!!.getStaffListMutableLiveData
        SemesterSectionLiveData = apiRepositories!!.getSemesterSectionListLiveData
        FacultyLiveData = apiRepositories!!.getFacultyReceiverLiveData
        CategoryTypeLiveData = apiRepositories!!.getCategoryTypeLiveData
        CategoryWiseCreditLiveData = apiRepositories!!.getCategoryWiseCreditLiveData
        SemesterTypeLiveData = apiRepositories!!.getSemesterTypeLiveData
        SemesterWiseCreditLiveData = apiRepositories!!.getSemesterwiseCreditsLiveData
        SemesterWiseCreditAllLiveData = apiRepositories!!.getSemesterwiseCreditsAllLiveData
        AttendanceDatesLiveData = apiRepositories!!.getAttendanceLiveData
        LeaveHistoryLiveData = apiRepositories!!.getLeaveHistoryLiveData
        LeaveTypeLiveData = apiRepositories!!.getLeaveTypeLiveData
        ChangePasswordLiveData = apiRepositories!!.getChangePawwordLiveData
        AdvertisementLiveData = apiRepositories!!.getAdvertisementMutableData
        UpdateDeviceTokenMutableLiveData = apiRepositories!!.UpdateDeviceTokenMutableLiveData
        VideoRestrictContentMutableLiveData = apiRepositories!!.VideoRestrictionMutableLiveData

        GetDivisionMutableLiveData = apiRepositories!!.GetDivisionMutableLiveData
        GetDepartmentMutableLiveData = apiRepositories!!.GetDepartmentMutableLiveData
        GetCourseDepartmentMutableLiveData = apiRepositories!!.GetCourseLiveData
        SendSMSToEntireCollegeLiveData = apiRepositories!!.SendSMStoEntireCollegeMutableData
        SendSMStoParticularMutableData = apiRepositories!!.SendSMStoParticularMutableData
    }

    fun getCourseDetails(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetCourseDetails(jsonObject, activity!!)
    }

    fun getExamApplication(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetExamApplicationDetails(jsonObject, activity!!)
    }

    fun getStudentProfile(memberid: Int, activity: Activity?) {
        apiRepositories!!.GetStudentProfile(memberid, activity!!)
    }

    fun getprofileResponseLiveData(): LiveData<GetProfileResponse?>? {
        return ProfileDetailsResponseLiveData
    }

    fun getNoticeboardList(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetNoticeboradList(jsonObject, activity!!)
    }

    fun getCircularList(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetCircularListbyType(jsonObject, activity!!)
    }

    fun getAssignmentListbyType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetAssignmentListbyType(jsonObject, activity!!)
    }

    fun getAssignmentMemberCount(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetAssignmentmemberCount(jsonObject, activity!!)
    }

    fun getAssignmentViewContent(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetAssignmentViewContent(jsonObject, activity!!)
    }

    fun getAppreadStatus(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetAppreadStatus(jsonObject, activity!!)
    }

    fun getAppreadStatusContext(jsonObject: JsonObject?, activity: Context?) {
        apiRepositories!!.GetAppreadStatusContext(jsonObject, activity!!)
    }
    fun getOverAllMenuCount(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetOVerAllCount(jsonObject, activity!!)
    }

    fun getEventListbyType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetEventListBytType(jsonObject, activity!!)
    }
    fun getCommunicationListbyType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetCommunicationList(jsonObject, activity!!)
    }

    fun getExamListbyType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetExamListReceiver(jsonObject, activity!!)
    }

    fun getStudentExamMarklist(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.ExamMarkList(jsonObject, activity!!)
    }
    fun getVideoList(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetVideoList(jsonObject, activity!!)
    }
    fun getSemesterAndSection(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.SemesterSectionListForApp(jsonObject, activity!!)
    }

    fun getFaculty(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetFacultyListReceiver(jsonObject, activity!!)
    }

    fun getCategoryType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetCategoryType(jsonObject, activity!!)
    }

    fun getCategoryWiseCredit(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetCategoryCreditwise(jsonObject, activity!!)
    }
    fun getSmesterType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetSemesterType(jsonObject, activity!!)
    }

    fun getSemesterWiseCredit(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetSemesterWiseCredits(jsonObject, activity!!)
    }

    fun getSemesterWiseCreditAll(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetSemesterWiseCreditsAll(jsonObject, activity!!)
    }

    fun getAttendanceReceiver(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetAttendanceForParent(jsonObject, activity!!)
    }

    fun getleaveHistory(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetLeaveHistoryParent(jsonObject, activity!!)
    }

    fun getLeaveType(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetLeaveType(jsonObject, activity!!)
    }

    fun getChangePassword(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.ChangePassword(jsonObject, activity!!)
    }
    fun getStaffDetailsForApp(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.GetStaffDetails(jsonObject, activity!!)
    }

    fun getAdforCollege(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.getAddFoCollege(jsonObject, activity!!)
    }

    fun UpdateDeviceToken(jsonObject: JsonObject?, activity: Activity?) {
        apiRepositories!!.UpdateDeviceToken(jsonObject, activity!!)
    }

    fun VideoRestrictionContent(activity: Activity?) {
        apiRepositories!!.VideoRestriction(activity!!)
    }


    fun getDivision(jsonObject: JsonObject?,activity: Activity?) {
        apiRepositories!!.GetDivisions(jsonObject,activity!!)
    }

    fun getDepartment(jsonObject: JsonObject?,activity: Activity?) {
        apiRepositories!!.GetDepartmentData(jsonObject,activity!!)
    }


    fun getCourseDepartment(jsonObject: JsonObject?,activity: Activity?) {
        apiRepositories!!.GetCoursesByDepartment(jsonObject,activity!!)
    }

    fun SendSmsToEntireCollege(jsonObject: JsonObject?,activity: Activity?) {
        apiRepositories!!.SendSMSToEntireCollege(jsonObject,activity!!)
    }

    fun SendSmsToParticularType(jsonObject: JsonObject?,activity: Activity?) {
        apiRepositories!!.SendSMStoParticularType(jsonObject,activity!!)
    }

}