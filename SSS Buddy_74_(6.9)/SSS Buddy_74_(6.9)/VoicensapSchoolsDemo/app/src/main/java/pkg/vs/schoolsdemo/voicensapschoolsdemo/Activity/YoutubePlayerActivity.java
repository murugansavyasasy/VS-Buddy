package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Config;

public class YoutubePlayerActivity extends AppCompatActivity {

    private YouTubePlayerView youTubeView;
    String VideoURL;
    String VideoID;
    ImageView imgBack;
    Button btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_tube_player);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        btn_share = findViewById(R.id.btn_share);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent i = getIntent();
        VideoURL = i.getStringExtra("VideoLink");
        Log.d("testLink", VideoURL);
        extractYTId(VideoURL);


        // adding listener for our youtube player view.
        youTubeView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // loading the selected video into the YouTube Player
                youTubePlayer.loadVideo(VideoID, 0);
            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                // this method is called if video has ended,
                super.onStateChange(youTubePlayer, state);
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

    public String extractYTId(String VideoURL) {
        Log.d("VideoURL", VideoURL);

        VideoID = null;
        Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(VideoURL);
        if (matcher.matches()) {
            VideoID = matcher.group(1);
        }

        Log.d("VideoID", String.valueOf(VideoID));
        return VideoID;
    }
}