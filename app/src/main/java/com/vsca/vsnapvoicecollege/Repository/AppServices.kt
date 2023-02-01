package com.vsca.vsnapvoicecollege.Repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import com.vsca.vsnapvoicecollege.Utils.CustomLoading
import com.vsca.vsnapvoicecollege.Utils.CommonUtil
import com.vsca.vsnapvoicecollege.R
import androidx.lifecycle.LiveData
import com.vsca.vsnapvoicecollege.Model.*
import com.vsca.vsnapvoicecollege.Model.StatusMessageResponse
import com.vsca.vsnapvoicecollege.SenderModel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppServices {
    var progressDialog: ProgressDialog? = null
    var client_app: RestClient
    var GetCourseDetailsMutableData: MutableLiveData<GetCourseDetailsResposne?>
    var GetProfileDetailsMutableData: MutableLiveData<GetProfileResponse?>
    var GetExamDetailsMutableData: MutableLiveData<GetExamApplicationResponse?>
    var GetNoticeboardMutableLiveData: MutableLiveData<GetNoticeboardResposne?>
    var GetCircularMutableLiveData: MutableLiveData<GetCircularListResponse?>
    var GetAssignmentMutableLiveData: MutableLiveData<GetAssignmentListResponse?>
    var GetAssignmentCountMutableLivedata: MutableLiveData<GetAssignmentCountResponse?>
    var GetAssignmentViewContentLivedata: MutableLiveData<GetAssignmentViewContentResponse?>
    var AppReadStatusLiveData: MutableLiveData<StatusMessageResponse?>
    var ChangePasswordLiveData: MutableLiveData<StatusMessageResponse?>
    var GetOverAllMenuCountMutableLiveData: MutableLiveData<GetOverAllCountResposne?>
    var GetEventListMutableData: MutableLiveData<GetEventListbyTypeResponse?>
    var GetCommunicationMutableLiveData: MutableLiveData<GetCommunicationResponse?>
    var GetFacultyRecieverMutableLiveData: MutableLiveData<GetFacultyListResponse?>
    var SemesterSectionMutableLiveData: MutableLiveData<SemesterAndSectionListResposne?>
    var ExamListMutableLiveData: MutableLiveData<GetExamListResponse?>
    var ExamMarkListMutableLiveData: MutableLiveData<ExamMarkListResponse?>
    var VideoListMutableLiveData: MutableLiveData<GetVideoListResponse?>
    var StaffForAppListMutableLiveData: MutableLiveData<GetStaffDetailsResponse?>
    var CategoryTypeLiveData: MutableLiveData<GetCategoryTypesResponse?>
    var CategoryWiseCreditLiveData: MutableLiveData<GetCategoryWiseCreditResponse?>
    var SemesterTypeLiveData: MutableLiveData<GetSemesterWiseTypeResponse?>
    var SemesterWiseCreditLiveData: MutableLiveData<GetSemesterWiseCreditResponse?>
    var SemesterWiseCreditAllLiveData: MutableLiveData<GetSemesterWiseCreditALLResponse?>
    var AttendanceDatesiveData: MutableLiveData<AttendanceResponse?>
    var LeaveHistoryLiveData: MutableLiveData<LeaveHistoryResponse?>
    var LeaveTypeLiveData: MutableLiveData<LeaveTypeResponse?>
    var AdvertisementLiveData: MutableLiveData<GetAdvertisementResponse?>
    var UpdateDeviceTokenLiveData: MutableLiveData<StatusMessageResponse?>
    var VideoRestrictionLiveData: MutableLiveData<VideoRestrictionContentResponse?>

    //Recipient
    var GetDivisionLiveData: MutableLiveData<GetDivisionResponse?>
    var GetDepartmentLiveData: MutableLiveData<GetDepartmentResponse?>
    var GetCourseLiveData: MutableLiveData<GetCourseDepartmentResposne?>
    var SMSEntireCollegeLiveData: MutableLiveData<SenderStatusMessageData?>
    var SMSParticularTypeLiveData: MutableLiveData<SenderStatusMessageData?>

    init {
        client_app = RestClient()
        GetCourseDetailsMutableData = MutableLiveData()
        GetProfileDetailsMutableData = MutableLiveData()
        GetExamDetailsMutableData = MutableLiveData()
        GetNoticeboardMutableLiveData = MutableLiveData()
        GetCircularMutableLiveData = MutableLiveData()
        GetAssignmentMutableLiveData = MutableLiveData()
        GetAssignmentCountMutableLivedata = MutableLiveData()
        GetAssignmentViewContentLivedata = MutableLiveData()
        AppReadStatusLiveData = MutableLiveData()
        GetEventListMutableData = MutableLiveData()
        GetCommunicationMutableLiveData = MutableLiveData()
        GetFacultyRecieverMutableLiveData = MutableLiveData()
        SemesterSectionMutableLiveData = MutableLiveData()
        GetOverAllMenuCountMutableLiveData = MutableLiveData()
        ExamListMutableLiveData = MutableLiveData()
        ExamMarkListMutableLiveData = MutableLiveData()
        VideoListMutableLiveData = MutableLiveData()
        CategoryTypeLiveData = MutableLiveData()
        CategoryWiseCreditLiveData = MutableLiveData()
        SemesterTypeLiveData = MutableLiveData()
        SemesterWiseCreditLiveData = MutableLiveData()
        SemesterWiseCreditAllLiveData = MutableLiveData()
        AttendanceDatesiveData = MutableLiveData()
        LeaveHistoryLiveData = MutableLiveData()
        LeaveTypeLiveData = MutableLiveData()
        ChangePasswordLiveData = MutableLiveData()
        StaffForAppListMutableLiveData = MutableLiveData()
        AdvertisementLiveData = MutableLiveData()
        UpdateDeviceTokenLiveData = MutableLiveData()
        VideoRestrictionLiveData = MutableLiveData()
        GetDivisionLiveData = MutableLiveData()
        GetDepartmentLiveData = MutableLiveData()
        GetCourseLiveData = MutableLiveData()
        SMSEntireCollegeLiveData = MutableLiveData()
        SMSParticularTypeLiveData = MutableLiveData()

    }

    fun GetCourseDetails(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCourseDetails(jsonObject)
            ?.enqueue(object : Callback<GetCourseDetailsResposne?> {
                override fun onResponse(
                    call: Call<GetCourseDetailsResposne?>,
                    response: Response<GetCourseDetailsResposne?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetCourseDetailRespose",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetCourseDetailsMutableData.postValue(response.body())
                            } else {
                                CommonUtil.ApiAlertFinish(activity, response.body()!!.message)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        GetCourseDetailsMutableData.postValue(null)
                    } else {
                        GetCourseDetailsMutableData.postValue(null)

                    }
                }

                override fun onFailure(call: Call<GetCourseDetailsResposne?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetCourseDetailsMutableData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val courseDetailsResposneLiveData: LiveData<GetCourseDetailsResposne?>
        get() = GetCourseDetailsMutableData

    fun GetExamApplicationDetails(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetExamApplicationDetails(jsonObject)
            ?.enqueue(object : Callback<GetExamApplicationResponse?> {
                override fun onResponse(
                    call: Call<GetExamApplicationResponse?>,
                    response: Response<GetExamApplicationResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetExamDetailRespose",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            GetExamDetailsMutableData.postValue(response.body())

//                            if (status == 1) {
//                                GetExamDetailsMutableData.postValue(response.body())
//                            } else {
//                                GetExamDetailsMutableData.postValue(null)
//
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetExamDetailsMutableData.postValue(null)

                    } else {
                        GetExamDetailsMutableData.postValue(null)

                    }
                }

                override fun onFailure(call: Call<GetExamApplicationResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetExamDetailsMutableData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val examApplicationResponseLiveData: LiveData<GetExamApplicationResponse?>
        get() = GetExamDetailsMutableData

    fun GetStudentProfile(memberid: Int, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetProfile(memberid)
            ?.enqueue(object : Callback<GetProfileResponse?> {
                override fun onResponse(
                    call: Call<GetProfileResponse?>,
                    response: Response<GetProfileResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetProfileResponse",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetProfileDetailsMutableData.postValue(response.body())
                            } else {
                                CommonUtil.ApiAlertFinish(activity, response.body()!!.message)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetProfileDetailsMutableData.postValue(null)

                    } else {
                        GetProfileDetailsMutableData.postValue(null)

                    }
                }

                override fun onFailure(call: Call<GetProfileResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetProfileDetailsMutableData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val profileResponseLiveData: LiveData<GetProfileResponse?>
        get() = GetProfileDetailsMutableData

    fun GetNoticeboradList(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetNoticeboardlistbytpe(jsonObject)
            ?.enqueue(object : Callback<GetNoticeboardResposne?> {
                override fun onResponse(
                    call: Call<GetNoticeboardResposne?>,
                    response: Response<GetNoticeboardResposne?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetNoticeboardRsponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            GetNoticeboardMutableLiveData.postValue(response.body())

//                            if (status == 1) {
//                                GetNoticeboardMutableLiveData.postValue(response.body())
//                            } else {
//                                GetNoticeboardMutableLiveData.postValue(response.body())
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetNoticeboardMutableLiveData.postValue(null)
                    } else {
                        GetNoticeboardMutableLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetNoticeboardResposne?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetNoticeboardMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val noticeboardLiveData: LiveData<GetNoticeboardResposne?>
        get() = GetNoticeboardMutableLiveData

    fun GetCircularListbyType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCircularListbyType(jsonObject)
            ?.enqueue(object : Callback<GetCircularListResponse?> {
                override fun onResponse(
                    call: Call<GetCircularListResponse?>,
                    response: Response<GetCircularListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetCircularLisRsponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            GetCircularMutableLiveData.postValue(response.body())

//                            if (status == 1) {
//                                GetCircularMutableLiveData.postValue(response.body())
//                            } else {
//                                GetCircularMutableLiveData.postValue(response.body())
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetCircularMutableLiveData.postValue(null)
                    } else {
                        GetCircularMutableLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetCircularListResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetCircularMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val circularListResponseLiveData: LiveData<GetCircularListResponse?>
        get() = GetCircularMutableLiveData

    fun GetAssignmentListbyType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetAssignmentListbytype(jsonObject)
            ?.enqueue(object : Callback<GetAssignmentListResponse?> {
                override fun onResponse(
                    call: Call<GetAssignmentListResponse?>,
                    response: Response<GetAssignmentListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "AssignmentLisRsponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            GetAssignmentMutableLiveData.postValue(response.body())

//                            if (status == 1) {
//                                GetAssignmentMutableLiveData.postValue(response.body())
//                            } else {
//                                GetAssignmentMutableLiveData.postValue(response.body())
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetAssignmentMutableLiveData.postValue(null)
                    } else {
                        GetAssignmentMutableLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetAssignmentListResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetAssignmentMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val assignmentListResponseLiveData: LiveData<GetAssignmentListResponse?>
        get() = GetAssignmentMutableLiveData

    fun GetAssignmentmemberCount(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetAssignmentMemberCount(jsonObject)
            ?.enqueue(object : Callback<GetAssignmentCountResponse?> {
                override fun onResponse(
                    call: Call<GetAssignmentCountResponse?>,
                    response: Response<GetAssignmentCountResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "MemberCountRsponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            GetAssignmentCountMutableLivedata.postValue(response.body())

                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetAssignmentCountMutableLivedata.postValue(null)
                    } else {
                        GetAssignmentCountMutableLivedata.postValue(null)

                    }
                }

                override fun onFailure(call: Call<GetAssignmentCountResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetAssignmentCountMutableLivedata.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val assignmentCountResponseLiveData: LiveData<GetAssignmentCountResponse?>
        get() = GetAssignmentCountMutableLivedata

    fun GetAssignmentViewContent(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.ViewAssignmentContent(jsonObject)
            ?.enqueue(object : Callback<GetAssignmentViewContentResponse?> {
                override fun onResponse(
                    call: Call<GetAssignmentViewContentResponse?>,
                    response: Response<GetAssignmentViewContentResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "EventsResponse",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            GetAssignmentViewContentLivedata.postValue(response.body())

                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetAssignmentViewContentLivedata.postValue(null)

                    } else {
                        GetAssignmentViewContentLivedata.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetAssignmentViewContentResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    GetAssignmentViewContentLivedata.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val assignmentViewContentResponseLiveData: LiveData<GetAssignmentViewContentResponse?>
        get() = GetAssignmentViewContentLivedata


    fun GetAppreadStatus(jsonObject: JsonObject?, activity: Activity) {
//        progressDialog = CustomLoading.createProgressDialog(activity)
//        progressDialog!!.show()
        RestClient.apiInterfaces.AppReadStatus(jsonObject)
            ?.enqueue(object : Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>,
                    response: Response<StatusMessageResponse?>
                ) {
//                    progressDialog!!.dismiss()
                    Log.d(
                        "AppReadstatus",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status == 1) {
                                AppReadStatusLiveData.postValue(response.body())
                            } else {
                                AppReadStatusLiveData.postValue(response.body())
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        AppReadStatusLiveData.postValue(null)

                    } else {
                        AppReadStatusLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<StatusMessageResponse?>,
                    t: Throwable
                ) {
                    AppReadStatusLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    fun GetAppreadStatusContext(jsonObject: JsonObject?, activity: Context) {
//        progressDialog = CustomLoading.createProgressDialog(activity)
//        progressDialog!!.show()
        RestClient.apiInterfaces.AppReadStatus(jsonObject)
            ?.enqueue(object : Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>,
                    response: Response<StatusMessageResponse?>
                ) {
//                    progressDialog!!.dismiss()
                    Log.d(
                        "AppReadstatus",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status == 1) {
                                AppReadStatusLiveData.postValue(response.body())
                            } else {
                                AppReadStatusLiveData.postValue(response.body())
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        AppReadStatusLiveData.postValue(null)

                    } else {
                        AppReadStatusLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<StatusMessageResponse?>,
                    t: Throwable
                ) {
                    AppReadStatusLiveData.postValue(null)
                    t.printStackTrace()

                }
            })
    }

    val appreadstatusLiveData: LiveData<StatusMessageResponse?>
        get() = AppReadStatusLiveData

    fun GetOVerAllCount(jsonObject: JsonObject?, activity: Activity) {
//        progressDialog = CustomLoading.createProgressDialog(activity)
//        progressDialog!!.show()
        RestClient.apiInterfaces.GetOverallcountByMenuType(jsonObject)
            ?.enqueue(object : Callback<GetOverAllCountResposne?> {
                override fun onResponse(
                    call: Call<GetOverAllCountResposne?>,
                    response: Response<GetOverAllCountResposne?>
                ) {
//                    progressDialog!!.dismiss()
                    Log.d(
                        "GetOverAllCountRes",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            val status = response.body()!!.status

                            GetOverAllMenuCountMutableLiveData.postValue(response.body())

                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {


                        GetOverAllMenuCountMutableLiveData.postValue(null)


                    } else {
                        GetOverAllMenuCountMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetOverAllCountResposne?>,
                    t: Throwable
                ) {
                    GetOverAllMenuCountMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val OverAllCountMenuLiveData: LiveData<GetOverAllCountResposne?>
        get() = GetOverAllMenuCountMutableLiveData

    fun GetEventListBytType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetEventListByType(jsonObject)
            ?.enqueue(object : Callback<GetEventListbyTypeResponse?> {
                override fun onResponse(
                    call: Call<GetEventListbyTypeResponse?>,
                    response: Response<GetEventListbyTypeResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "ViewContentResAssignmet",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            GetEventListMutableData.postValue(response.body())

//                            if (status == 1) {
//                                GetEventListMutableData.postValue(response.body())
//                            } else {
//
//
//                                GetEventListMutableData.postValue(null)
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetEventListMutableData.postValue(null)

                    } else {
                        GetEventListMutableData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetEventListbyTypeResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    GetEventListMutableData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getEventListLiveData: LiveData<GetEventListbyTypeResponse?>
        get() = GetEventListMutableData

    fun GetCommunicationList(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCommunicationMessageBytype(jsonObject)
            ?.enqueue(object : Callback<GetCommunicationResponse?> {
                override fun onResponse(
                    call: Call<GetCommunicationResponse?>,
                    response: Response<GetCommunicationResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "Communicationresponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            if (status == 1) {
                                GetCommunicationMutableLiveData.postValue(response.body())
                            } else {
                                GetCommunicationMutableLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()

                        GetCommunicationMutableLiveData.postValue(response.body())

                    } else {
                        GetCommunicationMutableLiveData.postValue(response.body())

                    }
                }

                override fun onFailure(
                    call: Call<GetCommunicationResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    GetCommunicationMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getCommunicationLiveData: LiveData<GetCommunicationResponse?>
        get() = GetCommunicationMutableLiveData

    fun GetFacultyListReceiver(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetFacultyList(jsonObject)
            ?.enqueue(object : Callback<GetFacultyListResponse?> {
                override fun onResponse(
                    call: Call<GetFacultyListResponse?>,
                    response: Response<GetFacultyListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "FacultyResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetFacultyRecieverMutableLiveData.postValue(response.body())
                            } else {
                                GetFacultyRecieverMutableLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetFacultyRecieverMutableLiveData.postValue(null)

                    } else {
                        GetFacultyRecieverMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetFacultyListResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    GetFacultyRecieverMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getFacultyReceiverLiveData: LiveData<GetFacultyListResponse?>
        get() = GetFacultyRecieverMutableLiveData

    fun SemesterSectionListForApp(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetSemesterSectionlist(jsonObject)
            ?.enqueue(object : Callback<SemesterAndSectionListResposne?> {
                override fun onResponse(
                    call: Call<SemesterAndSectionListResposne?>,
                    response: Response<SemesterAndSectionListResposne?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "SemesterSectionRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                SemesterSectionMutableLiveData.postValue(response.body())
                            } else {
                                SemesterSectionMutableLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SemesterSectionMutableLiveData.postValue(null)

                    } else {
                        SemesterSectionMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<SemesterAndSectionListResposne?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    SemesterSectionMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getSemesterSectionListLiveData: LiveData<SemesterAndSectionListResposne?>
        get() = SemesterSectionMutableLiveData

    fun GetExamListReceiver(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetExamListByType(jsonObject)
            ?.enqueue(object : Callback<GetExamListResponse?> {
                override fun onResponse(
                    call: Call<GetExamListResponse?>,
                    response: Response<GetExamListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "ExamListResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                ExamListMutableLiveData.postValue(response.body())
                            } else {
                                ExamListMutableLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        ExamListMutableLiveData.postValue(response.body())

                    } else {
                        ExamListMutableLiveData.postValue(response.body())

                    }
                }

                override fun onFailure(
                    call: Call<GetExamListResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    ExamListMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getExamListLiveData: LiveData<GetExamListResponse?>
        get() = ExamListMutableLiveData

    fun ExamMarkList(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetExamMarkForStudent(jsonObject)
            ?.enqueue(object : Callback<ExamMarkListResponse?> {
                override fun onResponse(
                    call: Call<ExamMarkListResponse?>,
                    response: Response<ExamMarkListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "ExamMarkListRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                ExamMarkListMutableLiveData.postValue(response.body())
                            } else {
                                ExamMarkListMutableLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        ExamMarkListMutableLiveData.postValue(null)

                    } else {
                        ExamMarkListMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<ExamMarkListResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    ExamMarkListMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getExamMarkListLiveData: LiveData<ExamMarkListResponse?>
        get() = ExamMarkListMutableLiveData

    fun GetVideoList(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetVideoList(jsonObject)
            ?.enqueue(object : Callback<GetVideoListResponse?> {
                override fun onResponse(
                    call: Call<GetVideoListResponse?>,
                    response: Response<GetVideoListResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetVideoListResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            VideoListMutableLiveData.postValue(response.body())

//                            if (status == 1) {
//                                VideoListMutableLiveData.postValue(response.body())
//                            } else {
//                                VideoListMutableLiveData.postValue(null)
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        VideoListMutableLiveData.postValue(null)

                    } else {
                        VideoListMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetVideoListResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    VideoListMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getVideoListMutableLiveData: LiveData<GetVideoListResponse?>
        get() = VideoListMutableLiveData


    fun GetStaffDetails(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetstaffdetailsForApp(jsonObject)
            ?.enqueue(object : Callback<GetStaffDetailsResponse?> {
                override fun onResponse(
                    call: Call<GetStaffDetailsResponse?>,
                    response: Response<GetStaffDetailsResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetStaffDetailsRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            StaffForAppListMutableLiveData.postValue(response.body())

//                            if (status == 1) {
//                                VideoListMutableLiveData.postValue(response.body())
//                            } else {
//                                VideoListMutableLiveData.postValue(null)
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        VideoListMutableLiveData.postValue(null)

                    } else {
                        VideoListMutableLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetStaffDetailsResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    VideoListMutableLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getStaffListMutableLiveData: LiveData<GetStaffDetailsResponse?>
        get() = StaffForAppListMutableLiveData


    fun GetCategoryType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCategoryCreditsType(jsonObject)
            ?.enqueue(object : Callback<GetCategoryTypesResponse?> {
                override fun onResponse(
                    call: Call<GetCategoryTypesResponse?>,
                    response: Response<GetCategoryTypesResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "CategoryTypesResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                CategoryTypeLiveData.postValue(response.body())
                            } else {
                                CategoryTypeLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        CategoryTypeLiveData.postValue(null)

                    } else {
                        CategoryTypeLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetCategoryTypesResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    CategoryTypeLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getCategoryTypeLiveData: LiveData<GetCategoryTypesResponse?>
        get() = CategoryTypeLiveData


    fun GetCategoryCreditwise(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCategoryCreditWiseDetails(jsonObject)
            ?.enqueue(object : Callback<GetCategoryWiseCreditResponse?> {
                override fun onResponse(
                    call: Call<GetCategoryWiseCreditResponse?>,
                    response: Response<GetCategoryWiseCreditResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "CategoryCreditwseRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                CategoryWiseCreditLiveData.postValue(response.body())
                            } else {
                                CategoryWiseCreditLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        CategoryWiseCreditLiveData.postValue(null)

                    } else {
                        CategoryWiseCreditLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetCategoryWiseCreditResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    CategoryWiseCreditLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getCategoryWiseCreditLiveData: LiveData<GetCategoryWiseCreditResponse?>
        get() = CategoryWiseCreditLiveData

    fun GetSemesterType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetSemesterCreditsType(jsonObject)
            ?.enqueue(object : Callback<GetSemesterWiseTypeResponse?> {
                override fun onResponse(
                    call: Call<GetSemesterWiseTypeResponse?>,
                    response: Response<GetSemesterWiseTypeResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "SemesterTypesResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                SemesterTypeLiveData.postValue(response.body())
                            } else {
                                SemesterTypeLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SemesterTypeLiveData.postValue(null)

                    } else {
                        SemesterTypeLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetSemesterWiseTypeResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    SemesterTypeLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getSemesterTypeLiveData: LiveData<GetSemesterWiseTypeResponse?>
        get() = SemesterTypeLiveData


    fun GetSemesterWiseCredits(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetSemesterWiseCredits(jsonObject)
            ?.enqueue(object : Callback<GetSemesterWiseCreditResponse?> {
                override fun onResponse(
                    call: Call<GetSemesterWiseCreditResponse?>,
                    response: Response<GetSemesterWiseCreditResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "SemesterCreditRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                SemesterWiseCreditLiveData.postValue(response.body())
                            } else {
                                SemesterWiseCreditLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SemesterWiseCreditLiveData.postValue(null)

                    } else {
                        SemesterWiseCreditLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetSemesterWiseCreditResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    SemesterWiseCreditLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getSemesterwiseCreditsLiveData: LiveData<GetSemesterWiseCreditResponse?>
        get() = SemesterWiseCreditLiveData

    fun GetSemesterWiseCreditsAll(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetSemesterWiseCreditsAll(jsonObject)
            ?.enqueue(object : Callback<GetSemesterWiseCreditALLResponse?> {
                override fun onResponse(
                    call: Call<GetSemesterWiseCreditALLResponse?>,
                    response: Response<GetSemesterWiseCreditALLResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "SemesterCreditRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                SemesterWiseCreditAllLiveData.postValue(response.body())
                            } else {
                                SemesterWiseCreditAllLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SemesterWiseCreditAllLiveData.postValue(null)

                    } else {
                        SemesterWiseCreditAllLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<GetSemesterWiseCreditALLResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    SemesterWiseCreditAllLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getSemesterwiseCreditsAllLiveData: LiveData<GetSemesterWiseCreditALLResponse?>
        get() = SemesterWiseCreditAllLiveData

    fun GetAttendanceForParent(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetAttendanceForParent(jsonObject)
            ?.enqueue(object : Callback<AttendanceResponse?> {
                override fun onResponse(
                    call: Call<AttendanceResponse?>,
                    response: Response<AttendanceResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "SemesterCreditRes:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            AttendanceDatesiveData.postValue(response.body())

//                            if (status == 1) {
//                                progressDialog!!.dismiss()
//
//                                AttendanceDatesiveData.postValue(response.body())
//                            } else {
//                                progressDialog!!.dismiss()
//
//                                AttendanceDatesiveData.postValue(response.body())
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        AttendanceDatesiveData.postValue(null)

                    } else {
                        progressDialog!!.dismiss()

                        AttendanceDatesiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<AttendanceResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    AttendanceDatesiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getAttendanceLiveData: LiveData<AttendanceResponse?>
        get() = AttendanceDatesiveData

    fun GetLeaveHistoryParent(jsonObject: JsonObject?, activity: Activity) {
//        progressDialog = CustomLoading.createProgressDialog(activity)
//        progressDialog!!.show()
        RestClient.apiInterfaces.GetLeaveApplicationListForReceiverApp(jsonObject)
            ?.enqueue(object : Callback<LeaveHistoryResponse?> {
                override fun onResponse(
                    call: Call<LeaveHistoryResponse?>,
                    response: Response<LeaveHistoryResponse?>
                ) {
//                    progressDialog!!.dismiss()
                    Log.d(
                        "LeaveHistoryResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
//                            progressDialog!!.dismiss()
                            val status = response.body()!!.status

                            LeaveHistoryLiveData.postValue(null)
//
//                            if (status == 1) {
//                                LeaveHistoryLiveData.postValue(response.body())
//                            } else {
//                                LeaveHistoryLiveData.postValue(null)
//                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
//                        progressDialog!!.dismiss()
                        LeaveHistoryLiveData.postValue(null)

                    } else {
                        LeaveHistoryLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<LeaveHistoryResponse?>,
                    t: Throwable
                ) {
//                    progressDialog!!.dismiss()
                    LeaveHistoryLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getLeaveHistoryLiveData: LiveData<LeaveHistoryResponse?>
        get() = LeaveHistoryLiveData


    fun GetLeaveType(jsonObject: JsonObject?, activity: Activity) {
//        progressDialog = CustomLoading.createProgressDialog(activity)
//        progressDialog!!.show()
        RestClient.apiInterfaces.GetLeaveType(jsonObject)
            ?.enqueue(object : Callback<LeaveTypeResponse?> {
                override fun onResponse(
                    call: Call<LeaveTypeResponse?>,
                    response: Response<LeaveTypeResponse?>
                ) {
//                    progressDialog!!.dismiss()
                    Log.d(
                        "LeaveHistoryResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
//                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                LeaveTypeLiveData.postValue(response.body())
                            } else {
                                LeaveTypeLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
//                        progressDialog!!.dismiss()
                        LeaveTypeLiveData.postValue(null)

                    } else {
                        LeaveHistoryLiveData.postValue(null)

                    }
                }

                override fun onFailure(
                    call: Call<LeaveTypeResponse?>,
                    t: Throwable
                ) {
//                    progressDialog!!.dismiss()
                    LeaveTypeLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getLeaveTypeLiveData: LiveData<LeaveTypeResponse?>
        get() = LeaveTypeLiveData

    fun ChangePassword(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.ChangePassword(jsonObject)
            ?.enqueue(object : Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>,
                    response: Response<StatusMessageResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "LeaveHistoryResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                ChangePasswordLiveData.postValue(response.body())
                            } else {
                                ChangePasswordLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        ChangePasswordLiveData.postValue(null)
                    } else {
                        ChangePasswordLiveData.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<StatusMessageResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    ChangePasswordLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getChangePawwordLiveData: LiveData<StatusMessageResponse?>
        get() = ChangePasswordLiveData

    fun getAddFoCollege(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetAdsForCollege(jsonObject)
            ?.enqueue(object : Callback<GetAdvertisementResponse?> {
                override fun onResponse(
                    call: Call<GetAdvertisementResponse?>,
                    response: Response<GetAdvertisementResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "LeaveHistoryResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                AdvertisementLiveData.postValue(response.body())
                            } else {
                                AdvertisementLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        AdvertisementLiveData.postValue(null)
                    } else {
                        AdvertisementLiveData.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<GetAdvertisementResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    AdvertisementLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val getAdvertisementMutableData: LiveData<GetAdvertisementResponse?>
        get() = AdvertisementLiveData

    fun UpdateDeviceToken(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.UpdateDeviceToken(jsonObject)
            ?.enqueue(object : Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>,
                    response: Response<StatusMessageResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "UpdateDeviceToken:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                UpdateDeviceTokenLiveData.postValue(response.body())
                            } else {
                                UpdateDeviceTokenLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        UpdateDeviceTokenLiveData.postValue(null)
                    } else {
                        UpdateDeviceTokenLiveData.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<StatusMessageResponse?>,
                    t: Throwable
                ) {
                    progressDialog!!.dismiss()
                    UpdateDeviceTokenLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val UpdateDeviceTokenMutableLiveData: LiveData<StatusMessageResponse?>
        get() = UpdateDeviceTokenLiveData

    fun VideoRestriction(activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetVideoContentRestriction()
            ?.enqueue(object : Callback<VideoRestrictionContentResponse?> {
                override fun onResponse(
                    call: Call<VideoRestrictionContentResponse?>,
                    response: Response<VideoRestrictionContentResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "VideoRestriction:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                VideoRestrictionLiveData.postValue(response.body())
                            } else {
                                VideoRestrictionLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        VideoRestrictionLiveData.postValue(null)
                    } else {
                        VideoRestrictionLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<VideoRestrictionContentResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    VideoRestrictionLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val VideoRestrictionMutableLiveData: LiveData<VideoRestrictionContentResponse?>
        get() = VideoRestrictionLiveData

    fun GetDivisions(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetDivisions(jsonObject)
            ?.enqueue(object : Callback<GetDivisionResponse?> {
                override fun onResponse(
                    call: Call<GetDivisionResponse?>,
                    response: Response<GetDivisionResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetDivisionResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetDivisionLiveData.postValue(response.body())
                            } else {
                                GetDivisionLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetDivisionLiveData.postValue(null)
                    } else {
                        GetDivisionLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetDivisionResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetDivisionLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val GetDivisionMutableLiveData: LiveData<GetDivisionResponse?>
        get() = GetDivisionLiveData

    fun GetDepartmentData(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetDepartment(jsonObject)
            ?.enqueue(object : Callback<GetDepartmentResponse?> {
                override fun onResponse(
                    call: Call<GetDepartmentResponse?>,
                    response: Response<GetDepartmentResponse?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetDepartmentResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetDepartmentLiveData.postValue(response.body())
                            } else {
                                GetDepartmentLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetDepartmentLiveData.postValue(null)
                    } else {
                        GetDepartmentLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetDepartmentResponse?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetDepartmentLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val GetDepartmentMutableLiveData: LiveData<GetDepartmentResponse?>
        get() = GetDepartmentLiveData

    fun GetCoursesByDepartment(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.GetCoursesByDepartment(jsonObject)
            ?.enqueue(object : Callback<GetCourseDepartmentResposne?> {
                override fun onResponse(
                    call: Call<GetCourseDepartmentResposne?>,
                    response: Response<GetCourseDepartmentResposne?>
                ) {
                    progressDialog!!.dismiss()
                    Log.d(
                        "GetCourseResponse:",
                        response.code().toString() + " - " + response.toString()
                    )
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.status
                            if (status == 1) {
                                GetCourseLiveData.postValue(response.body())
                            } else {
                                GetCourseLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        GetCourseLiveData.postValue(null)
                    } else {
                        GetCourseLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetCourseDepartmentResposne?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    GetCourseLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val GetCourseMutableLiveData: LiveData<GetCourseDepartmentResposne?>
        get() = GetCourseLiveData

    fun SendSMSToEntireCollege(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.SendSMSToEntireCollege(jsonObject)
            ?.enqueue(object : Callback<SenderStatusMessageData?> {
                override fun onResponse(call: Call<SenderStatusMessageData?>, response: Response<SenderStatusMessageData?>) {
                    progressDialog!!.dismiss()
                    Log.d("SMSEntireCollege:", response.code().toString() + " - " + response.toString())
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.Status
                            if (status.equals("1")) {
                                SMSEntireCollegeLiveData.postValue(response.body())
                            } else {
                                SMSEntireCollegeLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SMSEntireCollegeLiveData.postValue(null)
                    } else {
                        SMSEntireCollegeLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<SenderStatusMessageData?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    SMSEntireCollegeLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val SendSMStoEntireCollegeMutableData: LiveData<SenderStatusMessageData?>
        get() = SMSEntireCollegeLiveData

    fun SendSMStoParticularType(jsonObject: JsonObject?, activity: Activity) {
        progressDialog = CustomLoading.createProgressDialog(activity)
        progressDialog!!.show()
        RestClient.apiInterfaces.SendSMSToParticularType(jsonObject)
            ?.enqueue(object : Callback<SenderStatusMessageData?> {
                override fun onResponse(call: Call<SenderStatusMessageData?>, response: Response<SenderStatusMessageData?>) {
                    progressDialog!!.dismiss()
                    Log.d("SMSParticularType:", response.code().toString() + " - " + response.toString())
                    if (response.code() == 200 || response.code() == 201) {
                        if (response.body() != null) {
                            progressDialog!!.dismiss()
                            val status = response.body()!!.Status
                            if (status.equals("1")) {
                                SMSParticularTypeLiveData.postValue(response.body())
                            } else {
                                SMSParticularTypeLiveData.postValue(null)
                            }
                        }
                    } else if (response.code() == 400 || response.code() == 404 || response.code() == 500) {
                        progressDialog!!.dismiss()
                        SMSParticularTypeLiveData.postValue(null)
                    } else {
                        SMSParticularTypeLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<SenderStatusMessageData?>, t: Throwable) {
                    progressDialog!!.dismiss()
                    SMSParticularTypeLiveData.postValue(null)
                    t.printStackTrace()
                    CommonUtil.ApiAlertFinish(
                        activity,
                        activity.getString(R.string.txt_no_record_found)
                    )
                }
            })
    }

    val SendSMStoParticularMutableData: LiveData<SenderStatusMessageData?>
        get() = SMSParticularTypeLiveData
}