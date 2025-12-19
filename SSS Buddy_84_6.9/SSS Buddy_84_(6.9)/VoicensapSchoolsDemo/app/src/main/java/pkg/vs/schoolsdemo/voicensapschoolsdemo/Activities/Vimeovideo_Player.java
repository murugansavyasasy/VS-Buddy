package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Objects;


import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Vimeovideo_Player extends AppCompatActivity {

    //   String URL = "https://player.vimeo.com/video/";
    String VideoURL;
    ImageView imgBack, img_download;

    Button btn_share;

    BufferedInputStream bis = null;
    FileOutputStream fos = null;
    InputStream is = null;
    private final int TIMEOUT_CONNECTION = 5000;
    private final int TIMEOUT_SOCKET = 30000;
    private String TAG = "TAG";
    private String VSBUDDY = "//VS_Buddy.mp4";
    DownloadManager manager;

    private static final int PERMISSION_STORAGE_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeovideo_player);

        Intent i = getIntent();
        VideoURL = i.getStringExtra("VideoLink");
        Log.d("videourlvimeo", VideoURL);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        img_download = findViewById(R.id.img_download);
        btn_share = findViewById(R.id.btn_share);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String vimeoVideo = "<html><body><iframe width=\"380\" height=\"250\" src=\"" + VideoURL +
                "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        WebView webView = (WebView) findViewById(R.id.myWebView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

                webView.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(vimeoVideo, "text/html", "utf-8");


        img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(VideoURL));
                String title = URLUtil.guessFileName(VideoURL, null, "video/mp4");
                Log.d("title", title);
                request.setTitle(title);
                request.setDescription("Downloading file please wait....");
                String Cookie = CookieManager.getInstance().getCookie(VideoURL);
                request.addRequestHeader("cookie", Cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Vimeo_" + title);

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);


            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, VideoURL);
                startActivity(Intent.createChooser(intent, "Share"));

            }
        });
    }
}

