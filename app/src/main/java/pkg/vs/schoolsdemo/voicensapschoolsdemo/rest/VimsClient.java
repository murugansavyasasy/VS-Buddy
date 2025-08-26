package pkg.vs.schoolsdemo.voicensapschoolsdemo.rest;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VimsClient {
    //http://13.235.219.250/backend/api/SalesDemo
    // public static final String URL = "http://122.165.12.41:8094/";
    //public static final String URL = "http://122.165.12.41:8094/";
    //public static final String URL = "http://192.168.1.42:8059/";

    //  public static String URL = "http://vims.voicesnap.com/";
//    public static String URL = "http://103.154.253.101:8088/";  //Live previous
    public static String URL = "https://buddy.savyasasy.com/";  //Live
    public static Retrofit.Builder builder = new Retrofit.Builder()
            .client(getOk_client())
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .client(getOk_client())
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOk_client() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);
        OkHttpClient client1;
        client1 = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.MINUTES)
                .writeTimeout(20, TimeUnit.MINUTES)
                .build();
        return client1;
    }

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        URL = newApiBaseUrl;
        Log.d("Base URLnew", newApiBaseUrl);
        builder = new Retrofit.Builder()
                .client(getOk_client())
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());

    }
}
