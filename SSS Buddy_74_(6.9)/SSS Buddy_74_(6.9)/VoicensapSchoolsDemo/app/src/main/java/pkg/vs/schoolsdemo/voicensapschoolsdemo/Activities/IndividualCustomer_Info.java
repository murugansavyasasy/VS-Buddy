package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.PONumber_Pop_up_Adapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.CustomerListClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.PopUp_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;


public class IndividualCustomer_Info extends AppCompatActivity {

    TextView CustomeName, tallycustomerid, Contactperson, Contactnumber, cntctpersonDesignation, mailid, salespersonname, panNumber, Tinnumber, stcnumber, gstinnumber, billingaddress, billingcity, billingdistrict, billingstate, billingcountry, bilingpincode, billingphonenumber;

    TextView pannumber, tinnumber, stcnumber1, gstinnumber1;
    LinearLayout customerInfo, Individual_layout, layout_pan, layout_Tin, layout_STC, layout_Gst;
    View customer_view;

    String Custome_Name, tallycustomer_id, Contact_person, CustomerType, Contact_number, cntctperson_Designation, mail_id, salesperson_Name, pan_Number, Tin_number, stc_number, gstin_number, billing_address, billing_city, billing_district, billing_state, billing_country, biling_pincode, billing_phonenumber;
    private static final String TAG = IndividualCustomer_Info.class.getSimpleName();

    Button btn_GetPo;
    ImageView popclose;
    PopupWindow popupWindow;
    String idvalue, namevalue;

    String iduser;
    String customerid;
    CustomerListClass info;
    ArrayList<PopUp_class> namelist = new ArrayList<>();
    private RecyclerView recyclerView;
    PONumber_Pop_up_Adapter Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_individual_customer__info);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        CustomeName = (TextView) findViewById(R.id.customerName);
        tallycustomerid = (TextView) findViewById(R.id.tallycustomer);
//        Schoolid = (TextView) findViewById(R.id.schoolid);
        Contactperson = (TextView) findViewById(R.id.contactperson);
        Contactnumber = (TextView) findViewById(R.id.contactnumber);
        cntctpersonDesignation = (TextView) findViewById(R.id.contactPerson_Designaton);
        mailid = (TextView) findViewById(R.id.mailid);
        salespersonname = (TextView) findViewById(R.id.salesperson1);
        panNumber = (TextView) findViewById(R.id.PanNumber_2);
        Tinnumber = (TextView) findViewById(R.id.TinNumber_2);
        stcnumber = (TextView) findViewById(R.id.StcNumber_2);
        gstinnumber = (TextView) findViewById(R.id.GstNumber_2);
        billingaddress = (TextView) findViewById(R.id.billng_adrs);
        billingcity = (TextView) findViewById(R.id.billng_city);
        billingdistrict = (TextView) findViewById(R.id.billng_District);
        billingstate = (TextView) findViewById(R.id.billng_State);
        billingcountry = (TextView) findViewById(R.id.billng_Country);
        bilingpincode = (TextView) findViewById(R.id.billng_pincode);
        billingphonenumber = (TextView) findViewById(R.id.billng_phonenumber);


        pannumber = (TextView) findViewById(R.id.PanNumber1);
        tinnumber = (TextView) findViewById(R.id.TinNumber1);
        stcnumber1 = (TextView) findViewById(R.id.StcNumber1);
        gstinnumber1 = (TextView) findViewById(R.id.GstNumber1);

        customer_view = (View) findViewById(R.id.customer_detail_view);

        customerInfo = (LinearLayout) findViewById(R.id.layout_Customer_info);
        Individual_layout = (LinearLayout) findViewById(R.id.layout_Individual);
        layout_pan = (LinearLayout) findViewById(R.id.layout_pannumber);
        layout_Tin = (LinearLayout) findViewById(R.id.layout_Tinnumber);
        layout_STC = (LinearLayout) findViewById(R.id.layout_Stcnumber);
        layout_Gst = (LinearLayout) findViewById(R.id.layout_Gstnumber);

        btn_GetPo = (Button) findViewById(R.id.Get_po);


        iduser = SharedPreference_class.getUserid(IndividualCustomer_Info.this);
        Log.d("userid", iduser);

        info = (CustomerListClass) getIntent().getSerializableExtra("customeridetails");
        Log.d("customerid", "customer");
        customerid = info.getIdCustomer();


        Individual_Info();


        btn_GetPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Button", " button");

                Display_PO_NumberPopUp();

                final LayoutInflater inflater = (LayoutInflater) IndividualCustomer_Info.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.recyclerview_pop, null);

                popupWindow = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
                popupWindow.setContentView(layout);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                RecyclerView imageRecyclerview = (RecyclerView) layout.findViewById(R.id.recycler_popup);

                Adapter = new PONumber_Pop_up_Adapter(IndividualCustomer_Info.this,namelist);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                imageRecyclerview.setLayoutManager(mLayoutManager);
                imageRecyclerview.setItemAnimator(new DefaultItemAnimator());
                imageRecyclerview.setAdapter(Adapter);
                imageRecyclerview.getRecycledViewPool().setMaxRecycledViews(0, 80);



                if (popupWindow.isShowing()) {


                    Individual_layout.setVisibility(View.GONE);

                }else {
                    Individual_layout.setVisibility(View.VISIBLE);

                }
                Individual_layout.setVisibility(View.VISIBLE);


                popclose = (ImageView) layout.findViewById(R.id.pop_close);
                popclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("close_btn", "Button");
                        popupWindow.dismiss();
                        Individual_layout.setVisibility(View.VISIBLE);
                    }
                });
////
//
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                recyclerView.setLayoutManager(mLayoutManager);
//                Adapter = new PONumber_Pop_up_Adapter(IndividualCustomer_Info.this, namelist);
//                recyclerView.setAdapter(Adapter);
//                Adapter.notifyDataSetChanged();
//
//
            }
        });
//

    }



    private void Individual_Info() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        JsonObject object = new JsonObject();
        object.addProperty("idUser", iduser);
        object.addProperty("selectedUser", "0");
        object.addProperty("customerId", customerid);
        Call<JsonArray> call = apiService.GetCustomer_Individual_Info(object);
        call.enqueue(new Callback<JsonArray>() {


            @Override

            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();


                    Log.d("URL", String.valueOf(response.code()));
                    Log.d("response", response.body().toString());

                    JSONArray Array = new JSONArray(response.body().toString());


                    if (Array.length() > 0) {


                        for (int i = 0; i < Array.length(); i++) {

                            JSONObject jsonObject = Array.getJSONObject(i);

                            String strMsg=jsonObject.getString("resultMessage");

                            if(jsonObject.getString("result").equals("1")) {


                                Custome_Name = jsonObject.getString("customerName");
                                CustomeName.setText(Custome_Name);

                                SharedPreference_class.putCustomerName(IndividualCustomer_Info.this, Custome_Name);


                                tallycustomer_id = jsonObject.getString("tallyCustomerId");
                                tallycustomerid.setText(tallycustomer_id);


                                CustomerType = jsonObject.getString("customerType");
                                SharedPreference_class.putCustomertype(IndividualCustomer_Info.this, CustomerType);


//                            School_id = jsonObject.getString("SchoolServerId");
//                            Schoolid.setText(School_id);


                                Contact_person = jsonObject.getString("contactPerson");
                                Contactperson.setText(Contact_person);
                                Contact_number = jsonObject.getString("contactNumber");
                                Contactnumber.setText(Contact_number);
                                cntctperson_Designation = jsonObject.getString("contactPersonDesignation");
                                cntctpersonDesignation.setText(cntctperson_Designation);
                                mail_id = jsonObject.getString("mailId");
                                mailid.setText(mail_id);

                                salesperson_Name = jsonObject.getString("salesPersonName");
                                salespersonname.setText(salesperson_Name);


                                pan_Number = jsonObject.getString("PANNumber");
                                panNumber.setText(pan_Number);
                                Tin_number = jsonObject.getString("TINNumber");
                                Tinnumber.setText(Tin_number);
                                stc_number = jsonObject.getString("STCNumber");
                                stcnumber.setText(stc_number);
                                gstin_number = jsonObject.getString("GSTINNumber");
                                gstinnumber.setText(gstin_number);


                                billing_address = jsonObject.getString("billingAddress");
                                billingaddress.setText(billing_address);

                                billing_city = jsonObject.getString("billingCity");
                                billingcity.setText(billing_city);

                                billing_district = jsonObject.getString("billingDistrict");
                                billingdistrict.setText(billing_district);

                                billing_state = jsonObject.getString("billingState");
                                billingstate.setText(billing_state);

                                billing_country = jsonObject.getString("billingCountry");
                                billingcountry.setText(billing_country);

                                biling_pincode = jsonObject.getString("billingPincode");
                                bilingpincode.setText(biling_pincode);

                                billing_phonenumber = jsonObject.getString("billingPhoneNumber");
                                billingphonenumber.setText(billing_phonenumber);


                                if (Contact_person.isEmpty()) {
                                    Contactperson.setVisibility(View.GONE);
                                } else {
                                    Contactperson.setVisibility(View.VISIBLE);
                                }

                                if (Contact_number.isEmpty()) {
                                    Contactnumber.setVisibility(View.GONE);
                                } else {
                                    Contactnumber.setVisibility(View.VISIBLE);
                                }

                                if (cntctperson_Designation.isEmpty()) {
                                    cntctpersonDesignation.setVisibility(View.GONE);
                                } else {
                                    cntctpersonDesignation.setVisibility(View.VISIBLE);
                                }
                                if (mail_id.isEmpty()) {
                                    mailid.setVisibility(View.GONE);
                                } else {
                                    mailid.setVisibility(View.VISIBLE);
                                }
                                if (salesperson_Name.isEmpty()) {
                                    salespersonname.setVisibility(View.GONE);
                                } else {
                                    salespersonname.setVisibility(View.VISIBLE);
                                }

                                if (pan_Number.isEmpty()) {
//                                layout_pan.setVisibility(View.GONE);
////                                layout_Tin.setVisibility(View.GONE);
////                                layout_STC.setVisibility(View.GONE);
////                                layout_Gst.setVisibility(View.GONE);
                                    panNumber.setVisibility(View.GONE);
                                    pannumber.setVisibility(View.GONE);
                                    customerInfo.setVisibility(View.GONE);
                                    customer_view.setVisibility(View.GONE);
                                } else {
                                    layout_Tin.setVisibility(View.GONE);
                                    layout_STC.setVisibility(View.GONE);
                                    layout_Gst.setVisibility(View.GONE);
//                                layout_pan.setVisibility(View.VISIBLE);
                                    panNumber.setVisibility(View.VISIBLE);
                                    pannumber.setVisibility(View.VISIBLE);
                                    customerInfo.setVisibility(View.VISIBLE);
                                    customer_view.setVisibility(View.VISIBLE);

                                }
                                if (Tin_number.isEmpty()) {
//                                layout_Tin.setVisibility(View.GONE);
//                                layout_STC.setVisibility(View.GONE);
//                                layout_Gst.setVisibility(View.GONE);
                                    Tinnumber.setVisibility(View.GONE);
                                    tinnumber.setVisibility(View.GONE);
                                    customerInfo.setVisibility(View.GONE);
                                    customer_view.setVisibility(View.GONE);


                                } else {
//                                layout_Tin.setVisibility(View.VISIBLE);
                                    Tinnumber.setVisibility(View.VISIBLE);
                                    tinnumber.setVisibility(View.VISIBLE);
                                    customerInfo.setVisibility(View.VISIBLE);
                                    customer_view.setVisibility(View.VISIBLE);


                                }
                                if (stc_number.isEmpty()) {
                                    stcnumber.setVisibility(View.GONE);
                                    stcnumber1.setVisibility(View.GONE);
                                    customerInfo.setVisibility(View.GONE);
                                    customer_view.setVisibility(View.GONE);


                                } else {
                                    stcnumber.setVisibility(View.VISIBLE);
                                    stcnumber1.setVisibility(View.VISIBLE);
                                    customerInfo.setVisibility(View.VISIBLE);
                                    customer_view.setVisibility(View.VISIBLE);


                                }
                                if (gstin_number.isEmpty()) {
//                                layout_pan.setVisibility(View.GONE);
//                                layout_Tin.setVisibility(View.GONE);
//                                layout_STC.setVisibility(View.GONE);
//                                layout_Gst.setVisibility(View.GONE);
                                    gstinnumber.setVisibility(View.GONE);
                                    gstinnumber1.setVisibility(View.GONE);
                                    customerInfo.setVisibility(View.GONE);
                                    customer_view.setVisibility(View.GONE);

                                } else {
                                    layout_pan.setVisibility(View.GONE);
                                    layout_STC.setVisibility(View.GONE);
                                    layout_Tin.setVisibility(View.GONE);

                                    gstinnumber.setVisibility(View.VISIBLE);
                                    gstinnumber1.setVisibility(View.VISIBLE);
                                    customerInfo.setVisibility(View.VISIBLE);
                                    customer_view.setVisibility(View.VISIBLE);


                                }
                                if (billing_address.isEmpty()) {
                                    billingaddress.setVisibility(View.GONE);
                                } else {
                                    billingaddress.setVisibility(View.VISIBLE);
                                }
                                if (billing_city.isEmpty()) {
                                    billingcity.setVisibility(View.GONE);
                                } else {
                                    billingcity.setVisibility(View.VISIBLE);
                                }
                                if (billing_district.isEmpty()) {
                                    billingdistrict.setVisibility(View.GONE);
                                } else {
                                    billingdistrict.setVisibility(View.VISIBLE);
                                }
                                if (billing_state.isEmpty()) {
                                    billingstate.setVisibility(View.GONE);
                                } else {
                                    billingstate.setVisibility(View.VISIBLE);
                                }
                                if (biling_pincode.isEmpty()) {
                                    bilingpincode.setVisibility(View.GONE);
                                } else {
                                    bilingpincode.setVisibility(View.VISIBLE);
                                }
                                if (billing_phonenumber.isEmpty()) {
                                    billingphonenumber.setVisibility(View.GONE);
                                } else {
                                    billingphonenumber.setVisibility(View.VISIBLE);
                                }
                            }else {
                                alert(strMsg);

                            }

                        }
                    }else{

                        alert("No Records has found");
//                        Toast.makeText(IndividualCustomer_Info.this, "No Records has found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e(TAG, t.toString());


            }


        });


    }

    private void Display_PO_NumberPopUp() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.getPOlist(customerid);
        Log.d("customerid", "customerID");


        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                try {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    {
                        Log.d("customer:code-res", response.code() + " - " + response.toString());
                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("Response", response.body().toString());
//                            Log.d("URL", String.valueOf(response.code()));

                            JSONArray array = new JSONArray(response.body().toString());
                            namelist.clear();


                            if (array.length() > 0) {

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject object1 = array.getJSONObject(i);
                                    namevalue = object1.getString("nameValue");


                                    if (Integer.parseInt(object1.getString("idValue")) != 0) {

                                        idvalue = object1.getString("idValue");
                                        namevalue = object1.getString("nameValue");

                                        PopUp_class name = new PopUp_class(namevalue, idvalue);
                                        namelist.add(name);
                                        Log.d("listsize", String.valueOf(namelist.size()));

//                                        if (popupWindow.isShowing()) {
//
//                                            Individual_layout.setVisibility(View.GONE);
//                                        }
////
////                                        }else {
////
//                                            Individual_layout.setVisibility(View.VISIBLE);
//
//                                        }


                                    } else if (Integer.parseInt(object1.getString("idValue")) == 0) {
//                                        popupWindow.dismiss();
                                        Individual_layout.setVisibility(View.GONE);
                                        Alert();
                                    }

                                }
                                Adapter.notifyDataSetChanged();
                            } else {

                                alert("No Records has found");
//                                Toast.makeText(IndividualCustomer_Info.this, "No Records has found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e(TAG, t.toString());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
                Log.e(TAG, t.toString());


            }


        });


    }


    private void Alert() {
        popupWindow.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualCustomer_Info.this);
        builder.setTitle(namevalue);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Individual_layout.setVisibility(View.VISIBLE);


            }
        });
        builder.create().show();
//        if (popupWindow.isShowing()) {
//
//            Individual_layout.setVisibility(View.GONE);
//        }else {
//            Individual_layout.setVisibility(View.VISIBLE);
//
//        }
//        popupWindow.dismiss();


    }
    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualCustomer_Info.this);
        builder.setTitle(reason);

        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                Individual_layout.setVisibility(View.VISIBLE);
                onBackPressed();

                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
