package pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by devi on 4/15/2019.
 */

public interface VimsInterface {

    @GET("api/AppDetails/VersionCheckForApp")
    Call<JsonObject>Getversion(@Query("VersionID") Integer version);

    @POST("api/AppDetails/ValidateLoginForApp")
    Call<JsonObject> Login(@Body JsonObject jsonObject);

    @GET("api/AppDetails/ChangePassword")
    Call<JsonArray>ChangePassword(@Query("idUser") String iduser,@Query("OldPassword")String Oldpassword,@Query("NewPassword")String NewPassword);

    @POST("api/AppDetails/GetCustomersList")
    Call<JsonArray>CustomerDetails(@Body JsonObject jsonObject);

    @GET("api/AppDetails/GetSalesPersonDD")
    Call<JsonArray>getsalespersonlist(@Query("idUser") String iduser);

    @POST("api/AppDetails/GetIndvidualCustomerInfo")
    Call<JsonArray>GetCustomer_Individual_Info(@Body JsonObject jsonObject);

    @GET("api/GetDetails/GetPONumbersByCustomer")
    Call<JsonArray>getPOlist(@Query("cusId") String customerId);

    @POST("api/AppDetails/GetIndvidualSchoolPoInfoForApp")
    Call<JsonArray>Individual_SchoolPO(@Body JsonObject jsonObject);

    @POST("api/AppDetails/GetIndvidualCorporatePoInfoForApp")
    Call<JsonArray>Individual_CorporatePO(@Body JsonObject jsonObject);

    @POST("api/AppDetails/GetIndvidualHuddlePoInfoForApp")
    Call<JsonArray>Individual_HuddlePO(@Body JsonObject jsonObject);

    @POST("api/AppDetails/ManageExpense")
    Call<JsonArray>Local_Expenses(@Body JsonObject jsonObject);

    @Multipart
    @POST("api/AppDetails/UploadExpenseFiles")
    Call<JsonArray> Upload_Image(@Part("FormData") RequestBody requestBody, @Part MultipartBody.Part[] file);

    @GET("api/AppDetails/ViewExpenses")
    Call<JsonArray>GetReport(@Query("idUser") String iduser);

    @GET("api/GetDetails/GetLocalExpenseSummary")
    Call<JsonArray>GetReport_Details(@Query("idLocalExpense") String report);

    @GET("api/GetDetails/editLocalExpenseInfo")
    Call<JsonArray>GetReport_Edit(@Query("idLocalExpense") String report);

    @POST("api/AppDetails/ManageExpense")
    Call<JsonArray>Edit_Local_Expenses(@Body JsonObject jsonObject);

    @POST("api/AppDetails/ManageDeclineExpense")
    Call<JsonArray>Delete_localExpense(@Body JsonObject jsonObject);

    //Advance Tour

    @GET("api/AppDetails/GetAdvanceTourExpenses")
    Call<JsonArray> AdvanceTour(@Query("idUser") String demoid);

    @GET("api/GetDetails/GetTourExpenseSummary")
    Call<JsonArray> Details(@Query("idTourExpense") String tourexpense, @Query("cmd") String cmd);

    @POST("api/AppDetails/ManageTourExpense")
    Call<JsonArray> ManageTour(@Body JsonObject jsonObject);

    @GET("api/GetDetails/editTourExpenseInfo")
    Call<JsonArray> editTour(@Query("idTourExpense") String tourexpense, @Query("cmd") String cmd);

    @GET(" api/GetDetails/GetTourExpenseSummary")
    Call<JsonArray> editTourSummary(@Query("idTourExpense") String tourexpense, @Query("cmd") String cmd);

    @POST("api/AppDetails/ManageTourExpense")
    Call<JsonArray> ManageTourExpense(@Body JsonObject jsonObject);

    @POST("api/AppDetails/ManageDeclineExpense")
    Call<JsonArray> Delete(@Body JsonObject jsonObject);

    @GET("api/AppDetails/GetTourExpenses")
    Call<JsonArray> TourSettlement(@Query("idUser") String demoid);

    @GET("api/GetDetails/GetTourExpenseSummary")
    Call<JsonArray> TourDetails(@Query("idTourExpense") String tourexpense, @Query("cmd") String cmd);

    @Multipart
    @POST("api/AppDetails/UploadExpenseFiles")
    Call<JsonArray> Uploadfile(@Part("Info") RequestBody requestBody, @Part MultipartBody.Part[] file);

    @POST("api/AppDetails/ManageFeedbackRequirements")
    Call<JsonArray> ManageFeedbackRequirements(@Body JsonObject jsonObject);

    @GET("api/AppDetails/GetFeedbackRequirements")
    Call<JsonArray> GetFeedbackRequirements(@Query("Userid") String userid);


    @GET("api/AppDetails/GetGetPOByValidityCountforAdmin")
    Call<JsonArray> GetGetPOByValidityCountforAdmin(@Query("idUser") String userid);

    @GET("api/AppDetails/GetPOByValidityCountforSalesPerson")
    Call<JsonArray> GetPOByValidityCountforSalesPerson(@Query("idUser") String userid);

    @POST("api/AppDetails/GetPObyValidity")
    Call<JsonArray> GetPObyValidity(@Body JsonObject jsonObject);

    @POST("api/AppDetails/GetPObyValidityforAdmin")
    Call<JsonArray> GetPObyValidityforAdmin(@Body JsonObject jsonObject);


    @GET("api/AppDetails/GetPurchaseOrderConversation")
    Call<JsonArray> GetPurchaseOrderConversation(@Query("PurchaseOrderID") String poid,@Query("idUser") String userid);


    @POST("api/AppDetails/ManageChat")
    Call<JsonArray> postText(@Body JsonObject jsonObject);

    @POST("api/AppDetails/InsertPOChatSeenBy ")
    Call<JsonArray> InsertPOChatSeenBy (@Body JsonArray jsonObject);


    @GET("api/AppDetails/GetUnreadPOChatCountforAdmin")
    Call<JsonArray> GetUnreadPOChatCountforAdmin(@Query("idUser") String userid);

    @GET("api/AppDetails/GetUnreadPOChatCountforSalesPerson")
    Call<JsonArray> GetUnreadPOChatCountforSalesPerson(@Query("idUser") String userid);

    @GET("api/AppDetails/GetSchoolDocuments")
    Call<JsonArray> GetSchoolDocuments(@Query("UserId") String userid);



    @GET("api/AppDetails/GetVideos")
    Call<JsonArray> GetVideoList(@Query("UserId") String userid);


    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
}





