package pkg.vs.schoolsdemo.voicensapschoolsdemo.rest;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devi on 11/8/2017.
 */

public class VoicesnapdemoapiClient {

    //  public static String BASE_URL = "https://vs3.voicesnapforschools.com/api/SalesDemo/";   //live
//    public static String BASE_URL = "http://vs5.voicesnapforschools.com/nodejs/api/SalesDemo/";   //live previous
    public static String BASE_URL = "https://api.schoolchimes.com/nodejs/api/SalesDemo/";   //live


    // public static String BASE_URL = "http://vstest3.voicesnapforschools.com/nodejs/api/SalesDemo/"; // testing


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

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        Log.d("Base URL", BASE_URL);
        BASE_URL = newApiBaseUrl;
        Log.d("Base URLnew", BASE_URL);
        builder = new Retrofit.Builder()
                .client(getOK_Client())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
        ;

        Log.d("Base URLnew1", BASE_URL);
    }
}
