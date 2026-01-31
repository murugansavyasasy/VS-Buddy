package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splashscreen extends AppCompatActivity {
    Integer versionId = 55;
    String Ver_UpdateAvailable = "";
    String Force_UpdateReq = "";
    String emp_id, emp_pass;
    String Updation;
    String vimsurl, schoolurl;
    int RC_APP_UPDATE = 1;
    AppUpdateManager appUpdateManager;
    TextView lblVersionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        appUpdateManager = AppUpdateManagerFactory.create(Splashscreen.this);
        lblVersionId = (TextView) findViewById(R.id.lblVersion);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Log.d("packagename", getPackageName());
        if (!isNetworkConnected()) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Splashscreen.this);
            dlgAlert.setMessage("Not Connected to Network");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.show();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Updation = SharedPreference_class.getShUpdate(Splashscreen.this);
                    Log.d("flag_update", Updation);
                    emp_id = SharedPreference_class.getShEmployeeid(Splashscreen.this);
                    Log.d("empid", emp_id);
                    emp_pass = SharedPreference_class.getShPassword(Splashscreen.this);
                    Log.d("password", emp_pass);

                    VersionApi();
                    //  appUpdateManager.registerListener(listener);

                }
            }, 2000);

        }
    }

    private void VersionApi() {
        Log.d("versionCheckApi", "test");
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonObject> call = apiService.Getversion(versionId);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.d("URL", String.valueOf(response.code()));
                    Log.d("response", response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    Ver_UpdateAvailable = object.getString("IsVersionUpdateAvailable");
                    Force_UpdateReq = object.getString("IsForceUpdateRequired");
                    schoolurl = object.getString("SchoolURL");
                    vimsurl = object.getString("VimsURL");

                    Util_common.isSchoolUrl=schoolurl;
                    Util_common.isVimesUrl=vimsurl;

                    VimsClient.changeApiBaseUrl(vimsurl);
                    VoicesnapdemoapiClient.changeApiBaseUrl(schoolurl);

                    if ((Ver_UpdateAvailable.equals("0")) && (Force_UpdateReq.equals("0"))) {
                        Intent mainIntent = new Intent(Splashscreen.this, Login.class);
                        startActivity(mainIntent);
                        finish();
                        //  Log.d("Versions", Ver_UpdateAvailable + Force_UpdateReq);

                    } else {
                        Log.d("VersionsCheck", Ver_UpdateAvailable + Force_UpdateReq);
                        appUpdateManager.registerListener(listener);
                        checkUpdate();
                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert1("Server Connection Failed");
            }
        });
    }

    InstallStateUpdatedListener listener = state -> {
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            Log.d("downloading", "downloading");

        } else if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Log.d("isDOWNLOADED", String.valueOf(state.installStatus()));
            popupSnackbarForCompleteUpdate();

        } else if (state.installStatus() == InstallStatus.INSTALLED) {
            Log.d("INSTALLED", String.valueOf(state.installStatus()));
            unregisterInstallStateUpdListener();

        } else if (state.installStatus() == InstallStatus.INSTALLING) {
            Log.d("INSTALLING", String.valueOf(state.installStatus()));

        } else if (state.installStatus() == InstallStatus.CANCELED) {
            Log.d("CANCELED", String.valueOf(state.installStatus()));

        } else {
            Log.d("InstallListener: state:", String.valueOf(state.installStatus()));
        }
    };

    private void checkUpdate() {
        Log.d("update", "Check Update");
        // appUpdateManager.registerListener(listener);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        Log.d("CheckUpdate", "CheckUpdate");
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d("addOnSuccessListener", "addOnSuccessListener");
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d("UpdateAvailability", Ver_UpdateAvailable);
                Log.d("UpAvaForce", Force_UpdateReq);

                if ((Ver_UpdateAvailable.equals("1")) && (Force_UpdateReq.equals("0"))) {
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        Log.d("FLEXIBLEUpdate", "FLEXIBLE update");
                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }

                } else if ((Ver_UpdateAvailable.equals("1")) && (Force_UpdateReq.equals("1"))) {
                    if (appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                        Log.d("IMMEDIATEUpdate", "Immediate update");
                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.d("ElsePart", "ElsePart");
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("StatusDownloaded", String.valueOf(InstallStatus.DOWNLOADED));
                popupSnackbarForCompleteUpdate();
            } else {
                Log.d("Update not available", "update not available");

                final Intent mainIntent = new Intent(Splashscreen.this, Login.class);
                Splashscreen.this.startActivity(mainIntent);
                Splashscreen.this.finish();


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "App download starts...", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("RESULT_CANCELED", "download failed");
                Toast.makeText(this, "App download canceled.", Toast.LENGTH_LONG).show();
                if ((Ver_UpdateAvailable.equals("1")) && (Force_UpdateReq.equals("0"))) {
                    Log.d("No_thanks", "download canceled");
                    final Intent mainIntent = new Intent(Splashscreen.this, Login.class);
                    Splashscreen.this.startActivity(mainIntent);
                    finishAffinity();

                } else if ((Ver_UpdateAvailable.equals("1")) && (Force_UpdateReq.equals("1"))) {
                    checkUpdate();
                }


            } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
                Log.d("download failed", "download failed");

                checkUpdate();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume", "resume");
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("DOWNLOADED", String.valueOf(InstallStatus.DOWNLOADED));
                popupSnackbarForCompleteUpdate();
            }

            if ((Ver_UpdateAvailable.equals("1")) && (Force_UpdateReq.equals("1"))) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    Log.d("onesume_appUpdateInfo", String.valueOf(UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS));
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void popupSnackbarForCompleteUpdate() {
        Log.d("popupSnackbar", "popupSnackbarForCompleteUpdate");
        try {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_splashscreen),
                    "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("RESTART", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appUpdateManager.completeUpdate();
                    final Intent mainIntent = new Intent(Splashscreen.this, Login.class);
                    Splashscreen.this.startActivity(mainIntent);
                    finishAffinity();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.bpWhite));
            snackbar.show();
        } catch (Exception e) {
            Log.e("snackbarException", e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterInstallStateUpdListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterInstallStateUpdListener();
    }

    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && listener != null)
            appUpdateManager.unregisterListener(listener);
    }

    private void Alert1(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Splashscreen.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

