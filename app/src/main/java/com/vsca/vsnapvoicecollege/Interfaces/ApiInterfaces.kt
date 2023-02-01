package com.vsca.vsnapvoicecollege.Interfaces

import com.vsca.vsnapvoicecollege.Repository.ApiMethods
import com.vsca.vsnapvoicecollege.Repository.ApiRequestNames
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Model.*
import com.vsca.vsnapvoicecollege.Model.StatusMessageResponse
import com.vsca.vsnapvoicecollege.SenderModel.GetCourseDepartmentResposne
import com.vsca.vsnapvoicecollege.SenderModel.GetDepartmentResponse
import com.vsca.vsnapvoicecollege.SenderModel.GetDivisionResponse
import com.vsca.vsnapvoicecollege.SenderModel.SenderStatusMessageData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterfaces {
    @GET(ApiMethods.Country)
    fun Getcountrylist(@Query(ApiRequestNames.Req_AppId) AppId: Int): Call<CountryDetailsResponse?>?

    @GET(ApiMethods.VersionCheck)
    fun VersionCheck(@Query(ApiRequestNames.Req_VersionID) versionID: Int): Call<VersionCheckResposne?>?

    @GET(ApiMethods.GetProfileDetails)
    fun GetProfile(@Query(ApiRequestNames.Req_id) MemberId: Int): Call<GetProfileResponse?>?

    @POST(ApiMethods.Login)
    fun Login(@Body jsonObject: JsonObject?): Call<LoginResponse?>?

    @POST(ApiMethods.Dashboard)
    fun Dashboard(@Body jsonObject: JsonObject?): Call<DashBoardResponse?>?

    @POST(ApiMethods.UserMenus)
    fun GetUsermenu(@Body jsonObject: JsonObject?): Call<MenuResponse?>?

    @POST(ApiMethods.GetNotifications)
    fun GetNotifications(@Body jsonObject: JsonObject?): Call<GetNotificationsResponse?>?

    @POST(ApiMethods.GetCourseDetails)
    fun GetCourseDetails(@Body jsonObject: JsonObject?): Call<GetCourseDetailsResposne?>?

    @POST(ApiMethods.GetExamApplicationDetails)
    fun GetExamApplicationDetails(@Body jsonObject: JsonObject?): Call<GetExamApplicationResponse?>?

    @POST(ApiMethods.GetNoticeboardListbyType)
    fun GetNoticeboardlistbytpe(@Body jsonObject: JsonObject?): Call<GetNoticeboardResposne?>?

    @POST(ApiMethods.GetCircularListbyType)
    fun GetCircularListbyType(@Body jsonObject: JsonObject?): Call<GetCircularListResponse?>?

    @POST(ApiMethods.GetAssignmentListByType)
    fun GetAssignmentListbytype(@Body jsonObject: JsonObject?): Call<GetAssignmentListResponse?>?

    @POST(ApiMethods.GetAssignmentMemberCount)
    fun GetAssignmentMemberCount(@Body jsonObject: JsonObject?): Call<GetAssignmentCountResponse?>?

    @POST(ApiMethods.ViewAssignmentContent)
    fun ViewAssignmentContent(@Body jsonObject: JsonObject?): Call<GetAssignmentViewContentResponse?>?

    @POST(ApiMethods.Appreadstatus)
    fun AppReadStatus(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST(ApiMethods.GetEventListByType)
    fun GetEventListByType(@Body jsonObject: JsonObject?): Call<GetEventListbyTypeResponse?>?

    @POST(ApiMethods.GetCommunicationMessageBytype)
    fun GetCommunicationMessageBytype(@Body jsonObject: JsonObject?): Call<GetCommunicationResponse?>?

    @POST(ApiMethods.GetOverallcountByMenuType)
    fun GetOverallcountByMenuType(@Body jsonObject: JsonObject?): Call<GetOverAllCountResposne?>?

    @POST(ApiMethods.FacultyList)
    fun GetFacultyList(@Body jsonObject: JsonObject?): Call<GetFacultyListResponse?>?

    @POST(ApiMethods.semesterandsectionListforApp)
    fun GetSemesterSectionlist(@Body jsonObject: JsonObject?): Call<SemesterAndSectionListResposne?>?

    @POST(ApiMethods.GetExamListByType)
    fun GetExamListByType(@Body jsonObject: JsonObject?): Call<GetExamListResponse?>?

    @POST(ApiMethods.GetStudentMarkDetailsForApp)
    fun GetExamMarkForStudent(@Body jsonObject: JsonObject?): Call<ExamMarkListResponse?>?

    @POST(ApiMethods.GetVideoList)
    fun GetVideoList(@Body jsonObject: JsonObject?): Call<GetVideoListResponse?>?

    @POST(ApiMethods.getsemesterlistforcourseid)
    fun GetSemesterCreditsType(@Body jsonObject: JsonObject?): Call<GetSemesterWiseTypeResponse?>?

    @POST(ApiMethods.semesterwisestudentcreditdetails)
    fun GetSemesterWiseCredits(@Body jsonObject: JsonObject?): Call<GetSemesterWiseCreditResponse?>?

    @POST(ApiMethods.semesterwisestudentcreditdetailsall)
    fun GetSemesterWiseCreditsAll(@Body jsonObject: JsonObject?): Call<GetSemesterWiseCreditALLResponse?>?

    @POST(ApiMethods.getcategorylistforclgeid)
    fun GetCategoryCreditsType(@Body jsonObject: JsonObject?): Call<GetCategoryTypesResponse?>?

    @POST(ApiMethods.categorywisestudentcreditdetails)
    fun GetCategoryCreditWiseDetails(@Body jsonObject: JsonObject?): Call<GetCategoryWiseCreditResponse?>?

    @POST(ApiMethods.getattendanceforparent)
    fun GetAttendanceForParent(@Body jsonObject: JsonObject?): Call<AttendanceResponse?>?

    @POST(ApiMethods.GetLeaveApplicationListForReceiverApp)
    fun GetLeaveApplicationListForReceiverApp(@Body jsonObject: JsonObject?): Call<LeaveHistoryResponse?>?

    @POST(ApiMethods.GetLeaveType)
    fun GetLeaveType(@Body jsonObject: JsonObject?): Call<LeaveTypeResponse?>?

    @POST(ApiMethods.changepassword)
    fun ChangePassword(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST(ApiMethods.GetstaffdetailsForApp)
    fun GetstaffdetailsForApp(@Body jsonObject: JsonObject?): Call<GetStaffDetailsResponse?>?


    @POST(ApiMethods.GetAddsForCollege)
    fun GetAdsForCollege(@Body jsonObject: JsonObject?): Call<GetAdvertisementResponse?>?


    @POST(ApiMethods.UpdateDeviceToken)
    fun UpdateDeviceToken(@Body jsonObject: JsonObject?): Call<StatusMessageResponse?>?

    @POST(ApiMethods.GetVideoContentRestriction)
    fun GetVideoContentRestriction(): Call<VideoRestrictionContentResponse?>?


    @Streaming
    @GET
    fun downloadFileWithDynamicUrlAsync(@Url fileUrl: String?): Call<ResponseBody?>?

    @Headers("Content-Type: application/json", "Accept: application/vnd.vimeo.*+json;version=3.4")
    @POST("/me/videos")
    fun VideoUpload(@Body jsonObject: JsonObject?, @Header("Authorization") head: String?): Call<JsonObject>


    @Headers(
        "Tus-Resumable: 1.0.0",
        "Upload-Offset: 0",
        "Content-Type: application/offset+octet-stream",
        "Accept: application/vnd.vimeo.*+json;version=3.4"
    )
    @PUT("upload")
    fun patchVimeoVideoMetaData(
        @Query("ticket_id") ticketid: String?,
        @Query("video_file_id") videoid: String?,
        @Query("signature") signatureid: String?,
        @Query("v6") v6id: String?,
        @Query("redirect_url") redirecturl: String?,
        @Body file: RequestBody?
    ): Call<ResponseBody>

    //SenderApis

    @POST(ApiMethods.GetDivisions)
    fun GetDivisions(@Body jsonObject: JsonObject?): Call<GetDivisionResponse?>?

    @POST(ApiMethods.GetDepartment)
    fun GetDepartment(@Body jsonObject: JsonObject?): Call<GetDepartmentResponse?>?

    @POST(ApiMethods.GetCoursesByDepartment)
    fun GetCoursesByDepartment(@Body jsonObject: JsonObject?): Call<GetCourseDepartmentResposne?>?

    @POST(ApiMethods.SendSMSToEntireCollege)
    fun SendSMSToEntireCollege(@Body jsonObject: JsonObject?): Call<SenderStatusMessageData?>?

    @POST(ApiMethods.SendSMSToParticularType)
    fun SendSMSToParticularType(@Body jsonObject: JsonObject?): Call<SenderStatusMessageData?>?



}