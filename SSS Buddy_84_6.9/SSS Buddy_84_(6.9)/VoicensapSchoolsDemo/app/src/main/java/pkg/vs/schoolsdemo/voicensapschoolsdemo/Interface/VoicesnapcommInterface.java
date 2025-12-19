package pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AttachmentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface VoicesnapcommInterface {

    @GET("attachment/report")
    Call<AttachmentResponse> getAttachmentReport(@Header("Authorization") String token);

}
