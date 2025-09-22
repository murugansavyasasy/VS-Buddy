package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class ImageViewActivity extends AppCompatActivity {

    ImageView imageView, img_worngback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.img_viewimage);
        img_worngback = findViewById(R.id.img_worngback);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Intent intent = getIntent();
        String imageurl = intent.getExtras().getString("Imageview");

        img_worngback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Report.class);
               startActivity(intent);
            }
        });

        Glide.with(this)
                .load(imageurl)
                .into(imageView);

    }
}