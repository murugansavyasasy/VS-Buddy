package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class YoutubePlayerActivity extends AppCompatActivity {

    String VideoURL;
    ImageView imgBack;
    Button btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_tube_player);

        imgBack = findViewById(R.id.imgBack);
        btn_share = findViewById(R.id.btn_share);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        imgBack.setOnClickListener(v -> onBackPressed());

        VideoURL = getIntent().getStringExtra("VideoLink");

        if (VideoURL == null || VideoURL.isEmpty()) {
            Toast.makeText(this, "Invalid video link", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ OPEN YOUTUBE NORMALLY
        openYoutube(VideoURL);

        btn_share.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, VideoURL);
            startActivity(Intent.createChooser(intent, "Share"));
        });
    }

    private void openYoutube(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // close this activity
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open YouTube", Toast.LENGTH_SHORT).show();
        }
    }
}



//public class YoutubePlayerActivity extends AppCompatActivity {
//
//    private YouTubePlayerView youTubeView;
//    String VideoURL;
//    String VideoID;
//    ImageView imgBack;
//    Button btn_share;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.you_tube_player);
//
//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        btn_share = findViewById(R.id.btn_share);
//
//        WindowInsetsControllerCompat insetsController =
//                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
//        insetsController.setAppearanceLightStatusBars(true);
//
//
//        imgBack = (ImageView) findViewById(R.id.imgBack);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        Intent i = getIntent();
//        VideoURL = i.getStringExtra("VideoLink");
//        Log.d("testLink", VideoURL);
//        extractYTId(VideoURL);
//
//
//        // adding listener for our youtube player view.
//        youTubeView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                // loading the selected video into the YouTube Player
//                youTubePlayer.loadVideo(VideoID, 0);
//            }
//
//            @Override
//            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
//                // this method is called if video has ended,
//                super.onStateChange(youTubePlayer, state);
//            }
//        });
//
//        btn_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, VideoURL);
//                startActivity(Intent.createChooser(intent, "Share"));
//
//            }
//        });
//    }
//
//    public String extractYTId(String VideoURL) {
//        Log.d("VideoURL", VideoURL);
//
//        VideoID = null;
//        Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(VideoURL);
//        if (matcher.matches()) {
//            VideoID = matcher.group(1);
//        }
//
//        Log.d("VideoID", String.valueOf(VideoID));
//        return VideoID;
//    }
//}