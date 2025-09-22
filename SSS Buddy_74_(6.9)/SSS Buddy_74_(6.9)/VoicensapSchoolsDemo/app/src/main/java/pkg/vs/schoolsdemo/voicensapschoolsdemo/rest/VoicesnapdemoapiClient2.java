package pkg.vs.schoolsdemo.voicensapschoolsdemo.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devi on 4/2/2018.
 */

public class VoicesnapdemoapiClient2 {


    //    public static String BASE_URL = "http://vims.voicesnapforschools.com/api/";
    public static String BASE_URL = "https://buddy.savyasasy.com/";  //  live
//    public static String BASE_URL = "http://103.154.253.101:8088/";  //  live previous

    //   public static  String BASE_URL = "http://122.165.12.41:8094/api/";

    //  public static final String BASE_URL = "http://122.165.12.41:8094/api/";

    // public static final String BASE_URL = "http://192.168.1.42:8059/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getOK_Client())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getOK_Client() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }

    public static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .client(getOK_Client())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

//    public static void changeApiBaseUrl(String newApiBaseUrl) {
//        Log.d("Base URL", BASE_URL);
//        BASE_URL = newApiBaseUrl;
//        Log.d("Base URLnew", BASE_URL);
//        builder = new Retrofit.Builder()
//                .client(getOK_Client())
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//        ;
//
//        Log.d("Base URLnew1", BASE_URL);
//    }
}
