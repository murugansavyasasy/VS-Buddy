package pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.AWS.PreSignedUrl;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AlertClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.ReportingMember;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripRequest;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by devi on 11/8/2017.
 */

public interface Voicesnapdemointerface {

    @POST("DemoValidateLogin")
    Call<JsonArray> Login(@Body JsonObject jsonObject);

    @POST("DemoCreateOrEditDemo")
    Call<JsonArray> CreateDemo(@Body JsonObject jsonObject);

    @POST("SchoolUsageReport")
    Call<JsonArray> UnusedSchoolList(@Body JsonObject jsonObject);

    @POST("DemoListAllDemos")
    Call<JsonArray> ListDemo(@Body JsonObject jsonObject);

    @POST("DemoGetPOCSchoolsToApprove")
    Call<JsonArray> ApprovePocList(@Body JsonObject jsonObject);

    @POST("ListAllPOCForSupportVerify")
    Call<JsonArray> ListAllPOCForSupportVerify(@Body JsonObject jsonObject);

    @POST("DemoChangeNewPassword")
    Call<JsonArray> ChangePassword(@Body JsonObject jsonObject);

    @POST("GetVoiceCircularReport")
    Call<JsonArray> CircularList(@Body JsonObject jsonObject);

    @POST("DemoApproveDeclinePOC")
    Call<JsonArray> Approveordecline(@Body JsonObject jsonObject);

    @POST("ManageSupportTeamVerify")
    Call<JsonArray> Approveordeclinesupport(@Body JsonObject jsonObject);

    //    @Multipart
    @POST("ManagePOC")
    Call<JsonArray> CreatePOC(@Body JsonObject jsonObject);//, @Part MultipartBody.Part file);

    @POST("UploadWelcomeFile")
    Call<JsonArray> UploadWelcomefile(@Body JsonObject jsonObject);

    @POST("AddCallorSMSCredits")
    Call<JsonArray> Credits(@Body JsonObject jsonObject);

    @POST("EnableFeaturesByApp")
    Call<JsonArray> Features(@Body JsonObject jsonObject);

    @POST("GetExistingFeaturesbyApp")
    Call<JsonArray> Featurelist(@Body JsonObject jsonObject);

    @POST("DemoMySchoolList")
    Call<JsonArray> Myschoollist(@Body JsonObject jsonObject);

    @POST("DemoAddSeniorManagement")
    Call<JsonArray> Seniormanagement(@Body JsonObject jsonObject);

    @POST("DemoTodaysVisit")
    Call<JsonArray> TodayVisit(@Body JsonArray jsonObject);

//    @POST("api/AppDetails/UpdateDailyVisit")
//    Call<JsonArray> TodayVisit1(@Body JsonArray jsonObject);

    @POST("api/AppDetails/UpdateDailyVisitWithLocation")
    Call<JsonArray> updateDailyVisitWithLocation(@Body JsonArray body);



    @POST("DemoOverallStatusReport")
    Call<JsonArray> Overallstatus(@Body JsonObject jsonObject);

    @POST("DemoGetAllStandards")
    Call<JsonArray> Getstandards(@Body JsonObject jsonObject);

    @POST("DemoGetAllGroups")
    Call<JsonArray> Getgroups(@Body JsonObject jsonObject);

    @POST("SendSMSEntireSchool")
    Call<JsonArray> Sendsmstoentireschool(@Body JsonObject jsonObject);

    @POST("SendSMSStandardGroup")
    Call<JsonArray> sendsmstogroupsandstandards(@Body JsonObject jsonObject);

    @GET("GetManagementNumbers")
    Call<JsonArray> GetManagementNumbers(@Query("Schoolid") String Schoolid);
    //Record collection

    @GET("GetDetails/DemoGetCustomerList")
    Call<JsonArray> DemoGetCustomerList(@Query("CustomerType") String CustomerType,@Query("LoginId") String LoginId);
    @Multipart
    @POST("api/ManageDetails/DemoInsertPayment")
    Call<JsonArray> DemoInsertPayment(@Part("Info") RequestBody requestBody, @Part MultipartBody.Part file);

    @POST("GetDetails/DemoGetInvoiceByCustomerID")
    Call<JsonArray> DemoGetInvoiceByCustomerID(@Query("customerId") String CustomerType);



    @POST("GetUsageReport")
    Call<JsonArray> GetUsageReport(@Body JsonObject jsonObject);

    @POST("DemoSMS")
    Call<JsonArray> DemoSMS(@Body JsonObject jsonObject);


    @GET("GetImportantInfo")
    Call<JsonArray> GetImportantInfo();

    @GET("GetDemoDetailsByDemoId")
    Call<JsonArray>GetDemoDetailsByDemoId(@Query("Demoid") String demoid);

    @GET("GetDemosByLoginId")
    Call<JsonArray>GetDemosByLoginId(@Query("LoginID") String demoid);

    @GET("api/GetDetails/getFinancialYearForApp")
    Call<JsonArray> getFinancialYearForApp ();

    @POST("alert_messages")
    Call<AlertClass>alert_messages();

    @POST("InitiateDemoCallByDemoID")
    Call<JsonArray> InitiateDemoCallByDemoID(@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("api/AppDetails/ManageTripDetails")
    Call<TripResponse> ManageTripDetails(@Body TripRequest request);


    @GET("api/AppDetails/GetReportingMembersByHierarchy")
    Call<List<ReportingMember>> getReportingMembers(@Query("UserId") String userId);

    @GET("api/AppDetails/GetOverallTripDetails")
    Call<List<TripDetails>> getOverallTripDetails(@Query("UserId") String userId
    );

    @GET("get-s3-presigned-url")
    Call<PreSignedUrl> getPreSignedUrl(@Query("bucket") String bucket,
                                       @Query("fileName") String fileName,
                                       @Query("bucketPath") String bucketPath,
                                       @Query("fileType") String fileType);

}
