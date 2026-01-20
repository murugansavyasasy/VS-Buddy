package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.InkaTranslateApi;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.InkaClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayAudioActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer, miniMediaPlayer;
    private ImageView imgVoicePlay, imgMiniPlay;
    private SeekBar seekBar, miniSeekBar;
    private TextView lblCurrentTime, lblTotalDuration, tvSchoolName, lblMiniCurrentTime, lblMiniTotalDuration;
    private Button btnTranslate;
    private Spinner spinnerLanguage;

    private ProgressBar progressLoader;

    private ConstraintLayout audioPlayerLayout, containerAudio, rlaAudioPlayer, miniAudioPlayer;
    private String voiceUrl = "";
    private Handler handler = new Handler();

    private InkaTranslateApi inkaApi;

    private boolean languageSelected = false;
    private String translatedAudioUrl = "";

    private String API_KEY = "AIzaSyDbFzfq2__upP6pmCwIWIvShdZhohMNBUU";

    private String lastTranslatedLang = "";

    private String originalVoiceUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);

        tvSchoolName = findViewById(R.id.tvSchoolName);
        String schoolName = getIntent().getStringExtra("SchoolName");
        Log.d("URL", "Received SchoolName: " + schoolName);
        voiceUrl = getIntent().getStringExtra("voiceFile");
        Log.d("URL", "Received voiceFile: " + voiceUrl);
        originalVoiceUrl = voiceUrl;

        if (voiceUrl != null) {
            voiceUrl = voiceUrl.replace("http://", "https://");
            originalVoiceUrl = voiceUrl; //Normalize the received URL
            Log.d("TranslateAPI", "Original voice URL saved: " + originalVoiceUrl);
        }

        if (voiceUrl != null && !voiceUrl.isEmpty()) {
            prepareMiniAudio(voiceUrl);//Api URL prepare
        } else {
            Toast.makeText(this, "Audio file not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (schoolName != null && !schoolName.isEmpty()) {
            tvSchoolName.setText(schoolName);
        }


        getSupportActionBar().setTitle("Play Audio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = findViewById(R.id.spinnerLanguage);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        btnTranslate = findViewById(R.id.btnTranslate);
        imgVoicePlay = findViewById(R.id.imgVoicePlay);
        seekBar = findViewById(R.id.seekBar);
        lblCurrentTime = findViewById(R.id.lblCurrentTime);
        lblTotalDuration = findViewById(R.id.lblTotalDuration);
        audioPlayerLayout = findViewById(R.id.rlaAudioPlayer);
        containerAudio = findViewById(R.id.containerAudio);
        rlaAudioPlayer = findViewById(R.id.rlaAudioPlayer);
        miniAudioPlayer = findViewById(R.id.miniAudioPlayer);
        imgMiniPlay = findViewById(R.id.imgMiniPlay);
        miniSeekBar = findViewById(R.id.miniSeekbar);
        lblMiniCurrentTime = findViewById(R.id.lblMiniCurrentTime);
        lblMiniTotalDuration = findViewById(R.id.lblMiniTotalDuration);
        progressLoader = findViewById(R.id.progressLoader);
        setAudioControlsEnabled(false);
        inkaApi = InkaClient.getClient().create(InkaTranslateApi.class);

        btnTranslate.setOnClickListener(v -> {
            int position = spinnerLanguage.getSelectedItemPosition();
            if (position == 0) {
                Toast.makeText(this, "Please select a language first!", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedLang = spinnerLanguage.getSelectedItem().toString();
            String langCode = getLanguageCode(selectedLang);
            translateAudioUrl(langCode);
        });

        String[] languages = {"Select Language", "English", "Hindi", "Tamil", "Telugu", "Malayalam", "Kannada", "Bengali", "Marathi", "Punjabi", "Spanish", "French", "German"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    languageSelected = true;
                } else {
                    languageSelected = false;
                    setAudioControlsEnabled(false);
                    resetAudio();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        imgVoicePlay.setOnClickListener(v -> {
            if (!languageSelected) {
                Toast.makeText(this, "Please select a language first!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (miniMediaPlayer != null && miniMediaPlayer.isPlaying()) {
                miniMediaPlayer.pause();
                imgMiniPlay.setImageResource(R.drawable.play_icon_voice);
                handler.removeCallbacksAndMessages(null);
            }
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
                } else {
                    mediaPlayer.start();
                    imgVoicePlay.setImageResource(R.drawable.pause_icon);
                    updateSeekBar();
                }
            }
        });
        imgMiniPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
                handler.removeCallbacksAndMessages(null);
            }
            if (miniMediaPlayer != null) {
                try {
                    if (miniMediaPlayer.isPlaying()) {
                        miniMediaPlayer.pause();
                        imgMiniPlay.setImageResource(R.drawable.play_icon_voice);
                    } else {
                        miniMediaPlayer.start();
                        imgMiniPlay.setImageResource(R.drawable.pause_icon);
                        updateMiniSeekBar();
                    }
                } catch (Exception e) {
                    Log.e("MiniPlayError", "Play Error: " + e.getMessage());
                    Toast.makeText(PlayAudioActivity.this, "Mini audio play error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        miniSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && miniMediaPlayer != null) {
                    miniMediaPlayer.seekTo(progress);
                    lblMiniCurrentTime.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return "en";
            case "Hindi":
                return "hi";
            case "Tamil":
                return "ta";
            case "Telugu":
                return "te";
            case "Malayalam":
                return "ml";
            case "Kannada":
                return "kn";
            case "Bengali":
                return "bn";
            case "Marathi":
                return "mr";
            case "Punjabi":
                return "pa";
            case "Spanish":
                return "es";
            case "French":
                return "fr";
            case "German":
                return "de";
            default:
                return "";
        }
    }


    private void prepareMiniAudio(String url) {
        try {
            Log.d("MiniAudioURL", "MiniAudioURL: " + url);

            if (miniMediaPlayer != null) {
                miniMediaPlayer.release();
                miniMediaPlayer = null;
            }

            miniMediaPlayer = new MediaPlayer();
            miniMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            miniMediaPlayer.setDataSource(url);

            miniMediaPlayer.setOnPreparedListener(mp -> {
                miniSeekBar.setMax(miniMediaPlayer.getDuration());
                lblMiniTotalDuration.setText(formatTime(miniMediaPlayer.getDuration()));
                lblMiniCurrentTime.setText("00:00");
            });


            miniMediaPlayer.setOnCompletionListener(mp -> {
                Log.d("MiniAudio", "Mini player prepared successfully");
                imgMiniPlay.setImageResource(R.drawable.play_icon_voice);
                miniMediaPlayer.seekTo(0);
                miniSeekBar.setProgress(0);
            });

            miniMediaPlayer.prepareAsync();

        } catch (Exception e) {
            Log.e("MiniPlayerError", "Error: " + e.getMessage());
            Toast.makeText(this, "Failed to load mini audio!", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateMiniSeekBar() {
        if (miniMediaPlayer != null) {
            int currentPos = miniMediaPlayer.getCurrentPosition();
            miniSeekBar.setProgress(currentPos);
            lblMiniCurrentTime.setText(formatTime(currentPos));

            if (miniMediaPlayer.isPlaying()) {
                handler.postDelayed(this::updateMiniSeekBar, 500);
            }
        }
    }
    private void translateAudioUrl(String lang) {

        progressLoader.setVisibility(View.VISIBLE);
        btnTranslate.setEnabled(false);
        btnTranslate.setText("Translating...");

        RequestBody audioUrlBody = RequestBody.create(MultipartBody.FORM, originalVoiceUrl);
        RequestBody langBody = RequestBody.create(MultipartBody.FORM, lang);

        // ===== ADDED LOG (REQUEST BODY AS JSON) =====
        try {
            JSONObject logJson = new JSONObject();
            logJson.put("audio_url", originalVoiceUrl);
            logJson.put("language", lang);

            Log.e("API_LOG", "================ REQUEST ================");
            Log.e("API_LOG", "BODY : " + logJson.toString());
            Log.e("API_LOG", "=========================================");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // ==========================================

        Log.e("DEBUG_API", "TRANSLATE REQUEST → Target language: " + lang);
        Log.e("DEBUG_API", "Original URL: " + voiceUrl);

        Call<ResponseBody> call = inkaApi.translateAudioUrl(API_KEY, audioUrlBody, langBody);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressLoader.setVisibility(View.GONE);

                // ===== ADDED LOG (RESPONSE INFO) =====
                Log.e("API_LOG", "================ RESPONSE ================");
                Log.e("API_LOG", "CODE    : " + response.code());
                Log.e("API_LOG", "SUCCESS : " + response.isSuccessful());
                // ====================================

                Log.d("response", String.valueOf(response));

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        byte[] audioBytes = response.body().bytes();

                        File translatedFile = saveAudioToCache(audioBytes);
                        translatedAudioUrl = translatedFile.getAbsolutePath();
                        voiceUrl = translatedAudioUrl;

                        Log.e("TRANSLATED", "New translation saved: " + voiceUrl);

                        resetAudio();
                        prepareAudio();

                        mediaPlayer.setOnPreparedListener(mp -> {
                            seekBar.setMax(mp.getDuration());
                            lblTotalDuration.setText(formatTime(mp.getDuration()));
                            lblCurrentTime.setText("00:00");
                            seekBar.setProgress(0);
                            setAudioControlsEnabled(true);
                            imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
                            Toast.makeText(
                                    PlayAudioActivity.this,
                                    "Translation ready! Tap play",
                                    Toast.LENGTH_LONG
                            ).show();
                        });

                    } catch (Exception e) {
                        Log.e("AUDIO_ERROR", "Failed to save audio", e);
                    }
                    btnTranslate.setEnabled(true);
                    btnTranslate.setText("Translate");

                } else {
                    String error = "Unknown error";
                    if (response.errorBody() != null) {
                        try {
                            error = response.errorBody().string();
                        } catch (Exception ignored) {}
                    }

                    Log.e("API_LOG", "ERROR BODY : " + error);

                    Log.e("DEBUG_API", "Translation failed: " + response.code() + " → " + error);
                    Toast.makeText(
                            PlayAudioActivity.this,
                            "Translation failed",
                            Toast.LENGTH_LONG
                    ).show();

                    btnTranslate.setEnabled(true);
                    btnTranslate.setText("Translate");
                }

                Log.e("API_LOG", "=========================================");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progressLoader.setVisibility(View.GONE);

                Log.e("API_LOG", "================ FAILURE ================");
                Log.e("API_LOG", "ERROR : " + t.getMessage());
                Log.e("API_LOG", "=========================================");

                Log.e("DEBUG_API", "Network failed: " + t.getMessage());
                Toast.makeText(
                        PlayAudioActivity.this,
                        "No internet connection",
                        Toast.LENGTH_SHORT
                ).show();

                btnTranslate.setEnabled(true);
                btnTranslate.setText("Translate");
            }
        });
    }



//    private void translateAudioUrl(String lang) {
//        progressLoader.setVisibility(View.VISIBLE);
//        btnTranslate.setEnabled(false);
//        btnTranslate.setText("Translating...");
//
//        RequestBody audioUrlBody = RequestBody.create(MultipartBody.FORM, originalVoiceUrl);
//        RequestBody langBody = RequestBody.create(MultipartBody.FORM, lang);
//        Log.e("DEBUG_API", "TRANSLATE REQUEST → Target language: " + lang);
//        Log.e("DEBUG_API", "Original URL: " + voiceUrl);
//
//        Call<ResponseBody> call = inkaApi.translateAudioUrl(API_KEY, audioUrlBody, langBody);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                progressLoader.setVisibility(View.GONE);
//                Log.d("response", String.valueOf(response));
//                if (response.isSuccessful() && response.body() != null) {
//                    try {
//                        byte[] audioBytes = response.body().bytes();
//
//                        File translatedFile = saveAudioToCache(audioBytes);
//                        translatedAudioUrl = translatedFile.getAbsolutePath();
//                        voiceUrl = translatedAudioUrl;
//
//                        Log.e("TRANSLATED", "New translation saved: " + voiceUrl);
//
//                        resetAudio();
//                        prepareAudio();//Audio Prepare
//
//                        mediaPlayer.setOnPreparedListener(mp -> {
//                            seekBar.setMax(mp.getDuration());
//                            lblTotalDuration.setText(formatTime(mp.getDuration()));
//                            lblCurrentTime.setText("00:00");
//                            seekBar.setProgress(0);
//                            setAudioControlsEnabled(true);
//                            imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
//                            Toast.makeText(PlayAudioActivity.this, "Translation ready! Tap play", Toast.LENGTH_LONG).show();
//                        });
//                        btnTranslate.setEnabled(true);
//                        btnTranslate.setText("Translate");
//
//                    } catch (Exception e) {
//                        Log.e("AUDIO_ERROR", "Failed to save audio", e);
//                        Toast.makeText(PlayAudioActivity.this, "Failed to process audio", Toast.LENGTH_SHORT).show();
//                        btnTranslate.setEnabled(true);
//                        btnTranslate.setText("Translate");
//                    }
//                } else {
//                    String error = "Unknown error";
//                    if (response.errorBody() != null) {
//                        try {
//                            error = response.errorBody().string();
//                        } catch (Exception ignored) {
//                        }
//                    }
//                    Log.e("DEBUG_API", "Translation failed: " + response.code() + " → " + error);
//                    Toast.makeText(PlayAudioActivity.this, "Translation failed", Toast.LENGTH_LONG).show();
//
//                    btnTranslate.setEnabled(true);
//                    btnTranslate.setText("Translate");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressLoader.setVisibility(View.GONE);
//                Log.e("DEBUG_API", "Network failed: " + t.getMessage());
//                Toast.makeText(PlayAudioActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
//
//                btnTranslate.setEnabled(true);
//                btnTranslate.setText("Translate");
//            }
//        });
//    }

    private File saveAudioToCache(byte[] audioBytes) throws IOException {
        File cacheDir = new File(getCacheDir(), "translated_audio");
        if (!cacheDir.exists()) cacheDir.mkdirs();

        File outputFile = new File(cacheDir, "translated_" + System.currentTimeMillis() + ".mp3");

        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(audioBytes);
        fos.flush();
        fos.close();
        Log.e("AUDIO_SAVED", "Saved translated audio: " + outputFile.getAbsolutePath());
        return outputFile;
    }


    private void setAudioControlsEnabled(boolean enabled) {
        audioPlayerLayout.setAlpha(enabled ? 1f : 0.4f);
        audioPlayerLayout.setClickable(true);
        imgVoicePlay.setAlpha(enabled ? 1f : 0.4f);
        seekBar.setEnabled(enabled);
    }

    private void prepareAudio() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(voiceUrl); // FINAL BINARY URL
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(mp -> {
                seekBar.setMax(mediaPlayer.getDuration());
                lblTotalDuration.setText(formatTime(mediaPlayer.getDuration()));
                seekBar.setProgress(0);
                lblCurrentTime.setText("00:00");
            });

            mediaPlayer.setOnCompletionListener(mp -> {
                handler.removeCallbacksAndMessages(null);
                imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
                lblCurrentTime.setText("00:00");
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                        lblCurrentTime.setText(formatTime(progress));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Audio loading failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            lblCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
            if (mediaPlayer.isPlaying()) {
                handler.postDelayed(this::updateSeekBar, 500);
            }
        }
    }


    private void resetAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        seekBar.setProgress(0);
        lblCurrentTime.setText("00:00");
        lblTotalDuration.setText("00:00");
        imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imgVoicePlay.setImageResource(R.drawable.play_icon_voice);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            if (miniMediaPlayer != null) {
                miniMediaPlayer.stop();
                miniMediaPlayer.release();
                miniMediaPlayer = null;
            }

            handler.removeCallbacksAndMessages(null);

        } catch (Exception e) {
            Log.e("AudioStop", "Error stopping: " + e.getMessage());
        }

        super.onBackPressed();
    }


    private String formatTime(int millis) {
        int minutes = millis / 60000;
        int seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
