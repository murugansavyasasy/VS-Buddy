package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class PreviewActivity extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    WebView webView;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        imageView = findViewById(R.id.previewImage);
        videoView = findViewById(R.id.previewVideo);
        webView = findViewById(R.id.previewWeb);
        loader = findViewById(R.id.previewLoader);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Preview");
        }

        String url = getIntent().getStringExtra("file_url");
        Log.d("Received file_url","file_url:" +url);
        String type = getIntent().getStringExtra("file_type");
        Log.d("Received file_type","file_type:"+type);

        switch (type.toUpperCase()) {

            case "IMAGE":
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(url).into(imageView);
                break;

            case "VIDEO":
                videoView.setVisibility(View.VISIBLE);
                MediaController mc = new MediaController(this);
                mc.setAnchorView(videoView);
                videoView.setMediaController(mc);
                videoView.setVideoPath(url);
                videoView.setOnPreparedListener(mp -> {
                    loader.setVisibility(View.GONE);

                    int videoWidth = mp.getVideoWidth();
                    int videoHeight = mp.getVideoHeight();
                    int screenWidth = videoView.getWidth();
                    int screenHeight = videoView.getHeight();

                    float scaleX = (float) screenWidth / videoWidth;
                    float scaleY = (float) screenHeight / videoHeight;
                    float scale = Math.min(scaleX, scaleY);

                    int newWidth = (int) (videoWidth * scale);
                    int newHeight = (int) (videoHeight * scale);

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(newWidth, newHeight);
                    params.gravity = android.view.Gravity.CENTER;
                    videoView.setLayoutParams(params);

                    videoView.start();
                });

                videoView.setOnErrorListener((mp, what, extra) -> {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(this, "Cannot play video", Toast.LENGTH_SHORT).show();
                    return true;
                });
                break;

            case "PDF":
            case "DOC":
            case "DOCX":
            case "PPT":
            case "PPTX":
            case "EXCEL":
            case "XLS":
            case "XLSX":
                loader.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        loader.setVisibility(View.GONE);
                    }
                });

                String googleViewer = "https://docs.google.com/gview?embedded=true&url=" + url;
                webView.loadUrl(googleViewer);
                break;

            default:
                Toast.makeText(this, "Unsupported File", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
