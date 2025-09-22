package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.ContactNumbersAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.ContactNumbers;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class ContactsNumbersActivity extends AppCompatActivity {
    RecyclerView RecyclerList;
    ContactNumbersAdapter contactsNumbersAdapter;
    String s2;
     ArrayList<String[]> snumbers=new ArrayList<String[]>();

    private ArrayList<ContactNumbers> circulardatalist = new ArrayList<>();
    private ArrayList<String> stringlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_numbers);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        RecyclerList=(RecyclerView) findViewById(R.id.RecyclerList);
        contactsNumbersAdapter = new ContactNumbersAdapter(stringlist,ContactsNumbersActivity.this);

        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(ContactsNumbersActivity.this);
        RecyclerList.setLayoutManager(circularLayoutManager);
        RecyclerList.setItemAnimator(new DefaultItemAnimator());
        RecyclerList.setAdapter(contactsNumbersAdapter);

        LoadContactsNumbers();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void LoadContactsNumbers() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("LoginID", "2342");

        //Log.d("Circularlist:req", jsonObject.toString());

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.GetImportantInfo();

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
                        stringlist.clear();

                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);

                            ContactNumbers tempData1 = new ContactNumbers();

                            Iterator<String> iter = temp.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();

                                stringlist.add(key);


                                try {
                                    //Object value = temp.get(key);
                                    String val=temp.getString(key);

                                     // String s=value.toString();
                                    //  s2=String.valueOf(s);
                                    s2=val;
                                    String currentString = s2;

                                    String[] items = currentString.split(",");
                                    for (String item : items)
                                    {
                                        stringlist.add(item);
                                        Log.d("testLink",item);
                                        Log.d("items", String.valueOf(items));
                                          System.out.println("item = " + item);

                                    }

                                    } catch (JSONException e) {
                                    // Something went wrong!
                                }
                                }

                            Log.d("ListSize", String.valueOf(stringlist.size()));
                            Log.d("Server Array", temp.toString());

                          //  circulardatalist.add(tempData1);
                        }
                        circulardatalist = new ArrayList<>();
                        circulardatalist.addAll(circulardatalist);

                        contactsNumbersAdapter.notifyDataSetChanged();
                    } else {
                        alert("No data Received. Try Again.");
                    }

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
            }
        });
    }

//    private void (String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactsNumbersActivity.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}
