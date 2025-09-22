package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import static pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common.MENU_VOICE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.HomeScreen;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Recordwelcomefile extends AppCompatActivity {

    Button btn_record, btnCancel, btnSubmit;
    //    Media Player
//    Button btnNext;
    RelativeLayout rlVoicePreview, rytButtons;
    ImageView ivRecord;
    ImageButton imgBtnPlayPause;
    SeekBar seekBar;
    String fileduration;

    TextView tvPlayDuration, tvRecordDuration, tvRecordTitle;
    LinearLayout linearLayoutParent;
    private MediaPlayer mediaPlayer;
    int mediaFileLengthInMilliseconds = 0;
    Handler handler = new Handler();
    int recTime;
    MediaRecorder recorder;
    Handler recTimerHandler = new Handler();
    boolean bIsRecording = false;
    int iMediaDuration = 0;
    String userId, schoolid;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    File fileRecordedFilePath;
    public static final String VOICE_FOLDER_NAME = "Demo App/Voice";
    public static final String VOICE_FILE_NAME = "demoVoice.mp3";
    int iRequestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordwelcomefile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);
        iRequestCode = getIntent().getExtras().getInt("Requestcode", 0);
        Log.d("iRequestCode", String.valueOf(iRequestCode));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("SchoolID");

        }
        userId = shpRemember.getString(SH_USERID, null);
        btn_record = (Button) findViewById(R.id.school_voicerecordsubmit);
        imgBtnPlayPause = (ImageButton) findViewById(R.id.myAudioPlayer_imgBtnPlayPause);
        rytButtons = findViewById(R.id.rytButtons);
        btnCancel = findViewById(R.id.btncancel);
        btnSubmit = findViewById(R.id.btnconfirm);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        rytButtons.setVisibility(View.GONE);
        if (iRequestCode == MENU_VOICE) {
            rytButtons.setVisibility(View.VISIBLE);
            btn_record.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Record Demo Voice");
        } else {
            rytButtons.setVisibility(View.GONE);
            btn_record.setVisibility(View.VISIBLE);
        }

        imgBtnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recVoicePlayPause();
            }
        });
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (!isNetworkConnected()) {

                welcomefileuploadretrofit();

//                } else {
//                    showToast("Check Your Internet Connection");
//                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert("Are you sure, Do you want to send", "1");
            }
        });
        seekBar = (SeekBar) findViewById(R.id.myAudioPlayer_seekBar);
        tvPlayDuration = (TextView) findViewById(R.id.myAudioPlayer_tvDuration);

        rlVoicePreview = (RelativeLayout) findViewById(R.id.voiceRec_rlPlayPreview);
        ivRecord = (ImageView) findViewById(R.id.voiceRec_ivRecord);
        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bIsRecording) {
                    stop_RECORD();
                    btnSubmit.setEnabled(true);
                } else {
                    start_RECORD();
                }
            }
        });
        tvRecordDuration = (TextView) findViewById(R.id.voiceRec_tvRecDuration);
        tvRecordTitle = (TextView) findViewById(R.id.voiceRec_tvRecTitle);

        btn_record.setEnabled(false);
        setupAudioPlayer();
        fetchSong();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);

//            case R.id.menu_tohome:
//                finish();
//                Intent intent2 = new Intent(Addservices.this, Addaccount.class);
//                startActivity(intent2);//to start the activity
//                return (true);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    // Rec..

    private void start_RECORD() {
        ivRecord.setBackgroundResource(R.drawable.bg_record_stop);
        ivRecord.setImageResource(R.drawable.ic_stop);
        tvRecordTitle.setText(getText(R.string.txt_stop_record));
        btn_record.setEnabled(false);

        try {

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            recorder.setAudioEncodingBitRate(16);
            recorder.setAudioSamplingRate(44100);
            recorder.setOutputFile(getRecFilename());
            recorder.prepare();
            recorder.start();
            recTimeUpdation();
            bIsRecording = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRecFilename() {
        String filepath;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filepath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();

//            filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        } else {
            filepath = Environment.getExternalStorageDirectory().getPath();
        }
        //    String filepath = Environment.getExternalStorageDirectory().getPath();
        File fileDir = new File(filepath, VOICE_FOLDER_NAME);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File fileNamePath = new File(fileDir, VOICE_FILE_NAME);
        Log.d("FILE_PATH", fileNamePath.getPath());
        return (fileNamePath.getPath()); //+ System.currentTimeMillis()
    }

    public void recTimeUpdation() {
        recTime = 1;
        recTimerHandler.postDelayed(runson, 1000);
    }

    private Runnable runson = new Runnable() {
        @Override
        public void run() {
            tvRecordDuration.setText(milliSecondsToTimer(recTime * 1000));

            if (!tvRecordDuration.getText().toString().equals("00:00")) {
                ivRecord.setEnabled(true);
            }

            recTime = recTime + 1;
            if (recTime != 180)
                recTimerHandler.postDelayed(this, 1000);
            else
                stop_RECORD();

        }
    };

    private void stop_RECORD() {
        recorder.stop();
        recTimerHandler.removeCallbacks(runson);
        bIsRecording = false;
        tvRecordTitle.setText(getText(R.string.txt_start_record));
        btn_record.setEnabled(true);

        ivRecord.setBackgroundResource(R.drawable.bg_record_start);
        ivRecord.setImageResource(R.drawable.ic_mic);
        rlVoicePreview.setVisibility(View.VISIBLE);

//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        fetchSong();
    }
    // Play Voice

    private void setupAudioPlayer() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imgBtnPlayPause.setImageResource(R.drawable.ic_play);
                imgBtnPlayPause.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mediaPlayer.seekTo(0);
            }
        });

        seekBar.setMax(99); // It means 100% .0-99
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.myAudioPlayer_seekBar) {
//                    if (holder.mediaPlayer.isPlaying())
                    {
                        SeekBar sb = (SeekBar) v;
                        int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
//                        Log.d("Position: ", ""+playPositionInMillisecconds);
                        mediaPlayer.seekTo(playPositionInMillisecconds);
                    }
                }
                return false;
            }
        });
    }

    public void fetchSong() {
        Log.d("FetchSong", "Start***************************************");
        try {

            String filepath;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                filepath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();

            } else {
                filepath = Environment.getExternalStorageDirectory().getPath();
            }

            File file = new File(filepath, VOICE_FOLDER_NAME);
            File dir = new File(file.getAbsolutePath());

            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("Dir: " + dir);
            }

            fileRecordedFilePath = new File(dir, VOICE_FILE_NAME);
            System.out.println("FILE_PATH:" + fileRecordedFilePath.getPath());

            mediaPlayer.reset();
            mediaPlayer.setDataSource(fileRecordedFilePath.getPath());
            mediaPlayer.prepare();
            iMediaDuration = (int) (mediaPlayer.getDuration() / 1000.0);


        } catch (Exception e) {
            Log.d("in Fetch Song", e.toString());
        }

        Log.d("FetchSong", "END***************************************");
    }


    private void recVoicePlayPause() {

        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            imgBtnPlayPause.setImageResource(R.drawable.ic_pause);
            imgBtnPlayPause.setBackgroundColor(getResources().getColor(R.color.background1));
        } else {
            mediaPlayer.pause();
            imgBtnPlayPause.setImageResource(R.drawable.ic_play);
            imgBtnPlayPause.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds);
    }

    private void primarySeekBarProgressUpdater(final int fileLength) {
        int iProgress = (int) (((float) mediaPlayer.getCurrentPosition() / fileLength) * 100);
        seekBar.setProgress(iProgress); // This math construction give a percentage of "was playing"/"song length"

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    tvPlayDuration.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                    primarySeekBarProgressUpdater(fileLength);
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        String minutesString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to Minutes if it is one digit
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutesString + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    private void welcomefileuploadretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginId", userId);
        jsonObject.addProperty("schoolId", schoolid);
        Log.d("welcomefileupload:req", jsonObject.toString());

        File file = new File(fileRecordedFilePath.getPath());
        Log.d("FILE_Path", fileRecordedFilePath.getPath());
//        File file = FileUtils.getFile(this, Uri.parse(recFilePath));

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("voice", file.getName(), requestFile);

        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, jsonObject.toString());

        Call<JsonArray> call = apiService.UploadWelcomefile(requestBody, bodyFile);


        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("welcomefile:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                if (jsonObject.getString("Status").equals("1")) {

//                                    finish();

                                    String msg = jsonObject.getString("Message");
                                    alert(msg, "0");

//                                    showToast(jsonObject.getString("Message"));
                                } else {
                                    String msg1 = jsonObject.getString("Message");

                                    Alert(msg1);
//                                    Toast.makeText(Recordwelcomefile.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed");
            }
        });
    }

    private void InitiateCallForDemoid() {
        String demoid = getIntent().getStringExtra("demoid");
        String LoginSchlid = SharedPreference_class.getShSchlLoginid(Recordwelcomefile.this);
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        fileduration = tvRecordDuration.getText().toString();

        String time = fileduration; //mm:ss
        String[] units = time.split(":"); //will break the string up into an array
        int minutes = Integer.parseInt(units[0]); //first element
        int seconds = Integer.parseInt(units[1]); //second element
        int duration = 60 * minutes + seconds; //add up our values

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Demoid", demoid);
        jsonObject.addProperty("LoginID", LoginSchlid);
        jsonObject.addProperty("Duration", String.valueOf(duration));


        Log.d("welcomefileupload:req", jsonObject.toString());


        File file = new File(fileRecordedFilePath.getPath());
        Log.d("FILE_Path", fileRecordedFilePath.getPath());
//        File file = FileUtils.getFile(this, Uri.parse(recFilePath));

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.d("requestbody",requestFile.toString());

        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData("voice", file.getName(), requestFile);

        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, jsonObject.toString());

        Call<JsonArray> call = apiService.InitiateDemoCallByDemoID(requestBody, bodyFile);
        Log.d("callvalue",call.toString());


        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    Log.d("Response", response.toString());
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    {
                        Log.d("welcomefile:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
                            JSONArray js = new JSONArray(response.body().toString());
                            if (js.length() > 0) {
                                JSONObject jsonObject = js.getJSONObject(0);
                                if (jsonObject.getString("Status").equals("1")) {

//                                    finish();

                                    String msg = jsonObject.getString("Message");
                                    alert(msg, "0");

//                                    showToast(jsonObject.getString("Message"));
                                } else {
                                    String msg1 = jsonObject.getString("Message");

                                    Alert(msg1);
//                                    Toast.makeText(Recordwelcomefile.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed");
            }
        });
    }


    private void alert(String reason, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Recordwelcomefile.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(true);
        if (type.equals("1")) {
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    InitiateCallForDemoid();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });
            // builder.show();
        } else {
            builder.setTitle(reason);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i = new Intent(Recordwelcomefile.this, HomeScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();


                }
            });
        }
        builder.create().show();


    }

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Recordwelcomefile.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        builder.create().show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        finish();
    }
}
