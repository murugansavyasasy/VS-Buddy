package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.ExpandableMemberListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.MemberListHeader;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.MemberListSub;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListActivity extends AppCompatActivity {


    String DemoId, txtMessage, content, UserId="";
    SharedPreferences shpRemember;

    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";

    ExpandableListView rvRecyle;
    ExpandableMemberListAdapter mAdapter;

    List<MemberListHeader> listDataHeader;
    HashMap<MemberListHeader, List<MemberListSub>> listDataChild;

    String vimsUserTypeId = "";
    Call<JsonArray> call;
    String Count = "";
    private int lastExpandedPosition = -1;
    EditText txtSearchName;
    ImageView imgSearch;
    RelativeLayout rytNoRecords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlist_screen);

        rvRecyle = (ExpandableListView) findViewById(R.id.rvExp);
        txtSearchName = (EditText) findViewById(R.id.txtSearchName);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        rytNoRecords = (RelativeLayout) findViewById(R.id.rytNoRecords);

        vimsUserTypeId = SharedPreference_class.getSh_v_Usertype(MemberListActivity.this);
        UserId = SharedPreference_class.getUserid(MemberListActivity.this);
        Log.d("UserId",UserId);

        Count = getIntent().getExtras().getString("Count", "");

        listDataHeader = new ArrayList<MemberListHeader>();
        listDataChild = new HashMap<MemberListHeader, List<MemberListSub>>();


        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        txtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (mAdapter == null)
                    return;

                if (mAdapter.getGroupCount() < 1) {
                    rytNoRecords.setVisibility(View.VISIBLE);
                    rvRecyle.setVisibility(View.GONE);


                    if (txtSearchName.getText().toString().isEmpty()) {
                        rvRecyle.setVisibility(View.VISIBLE);
                        rytNoRecords.setVisibility(View.GONE);
                    }

                } else {
                    rytNoRecords.setVisibility(View.GONE);
                    rvRecyle.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    imgSearch.setVisibility(View.GONE);
                } else {
                    imgSearch.setVisibility(View.VISIBLE);
                }
                filterlist(editable.toString());
            }
        });



        mAdapter = new ExpandableMemberListAdapter(this, listDataHeader, listDataChild);
        rvRecyle.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    rvRecyle.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        // setting list adapter
        rvRecyle.setAdapter(mAdapter);


    }

    @Override
    public void onResume(){
        super.onResume();
        MembersList();

    }

    private void filterlist(String s) {
        List<MemberListHeader> temp = new ArrayList();
        for (MemberListHeader d : listDataHeader) {

            if (d.getName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(d);
            }

        }
        //update recyclerview
        mAdapter.updateList(temp);
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


    private void MembersList() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        if (!this.isFinishing())
            mProgressDialog.show();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ValidityPeriod", Count);
        jsonObject.addProperty("IdUser", UserId);

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        if (vimsUserTypeId.equals("13") || vimsUserTypeId.equals("14")) {
            call = apiService.GetPObyValidity(jsonObject);
        } else {
            call = apiService.GetPObyValidityforAdmin(jsonObject);
        }

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.d("TextMsg:Code", response.code() + " - " + response.toString());
                if (response.code() == 200 || response.code() == 201)
                    Log.d("TextMsg:Res", response.body().toString());

                try {
                    JSONArray js = new JSONArray(response.body().toString());
                    if (js.length() > 0) {

                        listDataHeader.clear();

                        for (int i = 0; i < js.length(); i++) {
                            JSONObject jsonObject = js.getJSONObject(i);
                            String ID = jsonObject.getString("SalesPersonID");
                            String name = jsonObject.getString("SalesPersonName");

                            MemberListHeader header;
                            header = new MemberListHeader(ID, name);
                            listDataHeader.add(header);

                            if(listDataHeader.size()>1){
                                rvRecyle.setVisibility(View.VISIBLE);
                                rytNoRecords.setVisibility(View.GONE);
                            }

                            JSONArray Details = new JSONArray(jsonObject.getString("POList"));
                            MemberListSub model;
                            Log.d("json length", js.length() + "");

                            List<MemberListSub> subject_details = new ArrayList<MemberListSub>();

                            //     mAdapter.clearAllData();

                            subject_details.clear();

                            for (int j = 0; j < Details.length(); j++) {

                                JSONObject jsonObject1 = Details.getJSONObject(j);

                                model = new MemberListSub(jsonObject1.getString("CustomerId"),
                                        jsonObject1.getString("CustomerName"), jsonObject1.getString("CustomerOtherName"),
                                        jsonObject1.getString("POId"), jsonObject1.getString("PONumber"),
                                        jsonObject1.getString("SchoolPOId"), jsonObject1.getString("GoLiveDate"),
                                        jsonObject1.getString("POValidFrom"), jsonObject1.getString("POValidTo"),
                                        jsonObject1.getString("RemainingDays"));

                                subject_details.add(model);

                            }
                            listDataChild.put(listDataHeader.get(i), subject_details);
                            mAdapter.notifyDataSetChanged();


                        }
                    } else {

                        Alert("No Records Found");
                    }

                } catch (Exception e) {
                    Log.e("TextMsg:Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Log.d("TextMsg:Failure", t.toString());
            }

        });
    }


    private void Alert(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MemberListActivity.this);
        builder.setTitle(msg);
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

}
