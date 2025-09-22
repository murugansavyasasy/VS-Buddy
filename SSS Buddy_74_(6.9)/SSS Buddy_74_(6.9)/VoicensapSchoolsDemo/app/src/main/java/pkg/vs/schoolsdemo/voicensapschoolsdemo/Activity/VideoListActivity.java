package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.VideoListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VideoClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;

public class VideoListActivity extends AppCompatActivity {
    RecyclerView rcyDocuments;
    VideoListAdapter schoolDocumentsAdapter;
    ArrayList<VideoClass> doclist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_recycle_layout);

        rcyDocuments = findViewById(R.id.rcyDocuments);
        documentsApi();

        schoolDocumentsAdapter = new VideoListAdapter(this, doclist);

        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(VideoListActivity.this);
        rcyDocuments.setLayoutManager(circularLayoutManager);
        rcyDocuments.setItemAnimator(new DefaultItemAnimator());
        rcyDocuments.setAdapter(schoolDocumentsAdapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void documentsApi() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        String userid = SharedPreference_class.getUserid(this);
        Log.d("userid", userid);
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);


        Call<JsonArray> call = apiService.GetVideoList(userid);
        Log.d("UserId", call.toString());

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("VideoList:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();
                    doclist.clear();
                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);
                            String VideoName = temp.getString("VideoName");
                            String VideoURL = temp.getString("VideoURL");
                            String videotype = temp.getString("VideoType");


                            Log.d("videourl", VideoURL);
                            VideoClass schoolDocumentsClass = new VideoClass(VideoName, VideoURL, videotype);
                            doclist.add(schoolDocumentsClass);
                            Log.d("videolink", doclist.toString());
                            schoolDocumentsAdapter.notifyDataSetChanged();

                        }

                    } else {
                        alertDialog("No Records Found");
                    }

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alertDialog(t.getMessage());
            }
        });
    }

    private void alertDialog(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(VideoListActivity.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }
}



//
//package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;
//
//
//        import static android.os.Environment.DIRECTORY_DOWNLOADS;
//
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.app.Activity;
//        import android.app.AlertDialog;
//        import android.app.DownloadManager;
//        import android.app.ProgressDialog;
//        import android.content.Context;
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.net.Uri;
//        import android.os.AsyncTask;
//        import android.os.Build;
//        import android.os.Bundle;
//        import android.os.Environment;
//        import android.util.Log;
//        import android.view.View;
//        import android.webkit.WebResourceRequest;
//        import android.webkit.WebSettings;
//        import android.webkit.WebView;
//        import android.webkit.WebViewClient;
//        import android.widget.ImageView;
//
//        import java.io.BufferedInputStream;
//        import java.io.File;
//        import java.io.FileOutputStream;
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.io.OutputStream;
//
//
//        import okhttp3.Callback;
//        import okhttp3.Response;
//        import okhttp3.ResponseBody;
//        import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
//        import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
//        import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
//        import retrofit2.Call;
//
//public class Vimeovideo_Player extends AppCompatActivity {
//
//    String VideoURL;
//    ImageView imgBack, img_download;
//
//    private String TAG = "TAG";
//    private String VSBUDDY = "//VS_Buddy.mp4";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vimeovideo_player);
//
//        Intent i = getIntent();
//        VideoURL = i.getStringExtra("VideoLink");
//        Log.d("videourlvimeo", VideoURL);
//
//        imgBack = (ImageView) findViewById(R.id.imgBack);
//        img_download = findViewById(R.id.img_download);
//
//
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//        String vimeoVideo = "<html><body><iframe width=\"380\" height=\"250\" src=\"" + VideoURL +
//                "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//
//        WebView webView = (WebView) findViewById(R.id.myWebView);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
//
//                webView.loadUrl(request.getUrl().toString());
//                return true;
//            }
//        });
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadData(vimeoVideo, "text/html", "utf-8");
//
//
//        img_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                downLoadFeeReceipt(VideoURL + "_VsBuddyVideo" + ".mp4", VSBUDDY);
//
//            }
//        });
//    }
//
//    private void downLoadFeeReceipt(String filename, String folder) {
//        final ProgressDialog mProgressDialog = new ProgressDialog(this);
//
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Downloading...");
//        mProgressDialog.setCancelable(false);
//        if (!pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Vimeovideo_Player.this.isFinishing())
//            mProgressDialog.show();
//
//        Log.d("File URL", VideoURL);
//
//        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
//        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlAsync(VideoURL);
//        call.enqueue(new okhttp3.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    Log.d("DOWNLOADING...", "server contacted and has file");
//
//                    new AsyncTask<Void, Void, Boolean>() {
//                        @Override
//                        protected Boolean doInBackground(Void... voids) {
//                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), folder, filename, pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Vimeovideo_Player.this);
//                            Log.d("DOWNLOADING...", "file download was a success? " + writtenToDisk);
//                            return writtenToDisk;
//                        }
//
//                        @Override
//                        protected void onPostExecute(Boolean status) {
//                            super.onPostExecute(status);
//                            if (status) {
//                                showAlert(pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Vimeovideo_Player.this, "Success", "File stored in: " + folder + "/" + filename);
//                            }
//                        }
//                    }.execute();
//                } else {
//                    Log.d("DOWNLOADING...", "server contact failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if (mProgressDialog.isShowing())
//                    mProgressDialog.dismiss();
//                Log.e("DOWNLOADING...", "error: " + t.toString());
//            }
//        });
//
//    }
//
//    public boolean writeResponseBodyToDisk(ResponseBody body, String folder, String fileName, Activity activity) {
//        try {
//            final ProgressDialog mProgressDialog = new ProgressDialog(this);
//
//            final File dir;
//            if (Build.VERSION_CODES.R > Build.VERSION.SDK_INT) {
//                dir = new File(Environment.getExternalStorageDirectory().getPath()
//                        + folder);
//            } else {
//                dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath()
//                        + folder);
//            }
//            System.out.println("body: " + body);
//
//            if (!dir.exists()) {
//                dir.mkdirs();
//                System.out.println("Dir: " + dir);
//            }
//
//            File futureStudioIconFile = new File(dir, fileName);
//
//            if (!futureStudioIconFile.exists()) {
//                File futureStudioIconFile1 = new File(dir, fileName);
//                futureStudioIconFile = futureStudioIconFile1;
//
//            }
//
//            System.out.println("futureStudioIconFile: " + futureStudioIconFile);
//
//            // todo change the file location/name according to your needs
//
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                byte[] fileReader = new byte[4096];
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(futureStudioIconFile);
//
//                while (true) {
//                    int read = inputStream.read(fileReader);
//
//                    if (read == -1) {
//                        break;
//                    }
//
//                    outputStream.write(fileReader, 0, read);
//
//
//                }
//
//                outputStream.flush();
//
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//
//                if (mProgressDialog.isShowing())
//                    mProgressDialog.dismiss();
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }
//
//    public static void showAlert(final Activity activity, String title, String msg) {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                activity);
//
//        alertDialog.setCancelable(false);
//        alertDialog.setTitle(title);
//        alertDialog.setMessage(msg);
//        // alertDialog.setIcon(R.drawable.ic_pdf);
//
////        alertDialog.setNeutralButton(R.string.teacher_btn_ok, new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int which) {
////
////            }
////        });
//
//        alertDialog.show();
//    }
//}

