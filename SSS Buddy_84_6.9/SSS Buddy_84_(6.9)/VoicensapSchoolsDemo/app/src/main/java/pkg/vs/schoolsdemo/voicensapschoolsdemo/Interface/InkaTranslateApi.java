package pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InkaTranslateApi {
    @Multipart
    @POST("v1/translate")
    Call<ResponseBody> translateAudioUrl(
            @Query("key") String apiKey,
            @Part("audio_url") RequestBody audioUrl,
            @Part("target_language") RequestBody targetLanguage
    );
}


