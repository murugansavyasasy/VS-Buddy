package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;

public class RequirementSendScreen extends AppCompatActivity {

    Button Send;
    EditText txt_requirement, general_content;
    String userID, Topic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reqiurement_send);

        Send = (Button) findViewById(R.id.Send);
        txt_requirement = (EditText) findViewById(R.id.txt_requirement);
        general_content = (EditText) findViewById(R.id.general_content);

        userID = SharedPreference_class.getUserid(RequirementSendScreen.this);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Topic = general_content.getText().toString();
                if (!txt_requirement.getText().toString().equals("")) {
                    sendApi(txt_requirement.getText().toString());
                } else {
                    Toast.makeText(RequirementSendScreen.this, "Please enter your Feedback here", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void sendApi(String description) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", userID);
        jsonObject.addProperty("Topic", Topic);
        jsonObject.addProperty("Description", description);
        jsonObject.addProperty("ProcessType", "add");

        Log.d("Circularlist:req", jsonObject.toString());

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.ManageFeedbackRequirements(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Circularlist:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(0);
                            String status = temp.getString("result");
                            String resultMessage = temp.getString("resultMessage");
                            if (status.equals("1")) {
                                Alert(resultMessage, status);
                            } else {
                                Alert(resultMessage, status);
                            }


                        }
                    } else {
                        String msg = "No Data Received...";
                        Alert(msg, "");


                    }


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Alert("Server Connection Failed", "");
            }
        });
    }

    private void Alert(String msg, final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RequirementSendScreen.this);
        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (status.equals("1")) {
                    dialog.cancel();
                    finish();
                } else {
                    dialog.cancel();
                }


            }
        });

        builder.create().show();


    }

}
