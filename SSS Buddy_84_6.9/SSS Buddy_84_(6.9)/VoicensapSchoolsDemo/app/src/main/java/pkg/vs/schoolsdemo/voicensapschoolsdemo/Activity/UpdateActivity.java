package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class UpdateActivity extends AppCompatActivity {
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 11;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

         installStateUpdatedListener = new InstallStateUpdatedListener() {
                    @Override
                    public void onStateUpdate(InstallState state) {
                        if (state.installStatus() == InstallStatus.DOWNLOADED){
                            //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                            popupSnackbarForCompleteUpdate();
                        } else if (state.installStatus() == InstallStatus.INSTALLED){
                            if (mAppUpdateManager != null){
                                mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                            }

                        } else {
                            Log.d("InstallStater: ", String.valueOf(state.installStatus()));
                        }
                    }
                };
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate();
            } else {
                Log.e(String.valueOf(this), "checkForAppUpdateAvailability: something else");
            }
        });


    }

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.activity_splashscreen),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null){
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.bpWhite));
        snackbar.show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.d("onActivityResult","app download failed");
            }
        }
    }
    private void unregisterInstallStateUpdListener() {
        if (mAppUpdateManager != null && installStateUpdatedListener != null)
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
    }

}