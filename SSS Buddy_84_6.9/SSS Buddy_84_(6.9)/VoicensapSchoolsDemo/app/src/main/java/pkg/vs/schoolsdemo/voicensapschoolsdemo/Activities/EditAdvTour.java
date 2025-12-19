package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TourExpenseItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditAdvTour extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {

    String item, Strid="0" ;
    String idTourExpenses;
    String strboarding1, strpetrol1, strmisc1, strBuisness1, strpostages1,
            strconvey1, strprinting1, strfood1, strtravel1;
    String cmd="Advance";
    String strboarding, strpetrol, strmisc, strBuisness, strpostages, strconvey, strprinting, strfood, strtravel;
    String strdate;
    String boardlodge,buisness,convtravel,food,fuel,postagecourier,printing,travel,misc;
    String resultMessage, MailMsg, WebUrl, AccountManagerMailId, idTourExpense, idDirectorExpense, idLocalExpense;
    String Strboard, Strbuisness, Strconv, Strfood, Strfuel, Strpostage, Strprinting, Strtravel, Strmisc,
            Strtourname, Strtourpurpose,Strmonth, Strstartdate, Strenddate, iduser,
            Strtourplace1, Strtourplace2, Strtourplace3, Strremarks, Strdescription, Strtotaltourexpense, Strprocesstype = "EditAdvanceTour",Stridclaim;
    EditText ettourname, etdescription, etpurposeoftour, etplace1, etplace2, etplace3, etboarding,
            etbuisness, etconveyance, etfood, etpetrol, etpostage, etprinting, ettravel, etmisc, etremarks, ettotal;
    ImageView imgstartdate, imgenddate;
    TextView etstartdate, etenddate,monthofclaim;
    Button btnsubmit;
    int result;
    int date;
    private List<TourExpenseItem> TourExpenseItemlist = new ArrayList<>();
    private Calendar calendar;
    int year, month, day;

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_adv_tour);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        Intent i=getIntent();
        idTourExpenses=i.getStringExtra("idTourExpenses");

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        iduser = SharedPreference_class.getUserid(EditAdvTour.this);
        Log.d("iduser",iduser);

        ettourname = (EditText) findViewById(R.id.tourname);
        monthofclaim = (TextView) findViewById(R.id.monthofclaim);
        etdescription = (EditText) findViewById(R.id.description);
        etpurposeoftour = (EditText) findViewById(R.id.purposeoftour);
        etstartdate = (TextView) findViewById(R.id.startdate);
        etenddate = (TextView) findViewById(R.id.enddate);
        etplace1 = (EditText) findViewById(R.id.place1);
        etplace2 = (EditText) findViewById(R.id.place2);
        etplace3 = (EditText) findViewById(R.id.place3);
        etboarding = (EditText) findViewById(R.id.boarding);
        etbuisness = (EditText) findViewById(R.id.buisness);
        etconveyance = (EditText) findViewById(R.id.conveyance);
        etfood = (EditText) findViewById(R.id.food);
        etpetrol = (EditText) findViewById(R.id.petrol);
        etpostage = (EditText) findViewById(R.id.postage);
        etprinting = (EditText) findViewById(R.id.printing);
        ettravel = (EditText) findViewById(R.id.travel);
        etmisc = (EditText) findViewById(R.id.misc);
        etremarks = (EditText) findViewById(R.id.remarks);
        ettotal = (EditText) findViewById(R.id.total);

        etboarding.addTextChangedListener(generalTextWatcher);
        etbuisness.addTextChangedListener(generalTextWatcher);
        etconveyance.addTextChangedListener(generalTextWatcher);
        etfood.addTextChangedListener(generalTextWatcher);
        etpetrol.addTextChangedListener(generalTextWatcher);
        etpostage.addTextChangedListener(generalTextWatcher);
        etprinting.addTextChangedListener(generalTextWatcher);
        ettravel.addTextChangedListener(generalTextWatcher);
        etmisc.addTextChangedListener(generalTextWatcher);

        imgstartdate = (ImageView) findViewById(R.id.img_startdate);
        imgstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strdate = "1";
                datePicker();
            }
        });
        imgenddate = (ImageView) findViewById(R.id.img_enddate);
        imgenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strdate = "2";
                datePicker();
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        btnsubmit = (Button) findViewById(R.id.btn_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Strstartdate=etstartdate.getText().toString();
                Strenddate=etenddate.getText().toString();

                date=Strstartdate.compareTo(Strenddate);
                if(date>0){
                    alertDialog("Please Enter Correct Date");
                }

               else if (ettourname.getText().toString().equals("")) {
                    alertDialog("Please Enter Tour Name");
                } else if (Strid.equals(" ")) {
                    alertDialog("Please Select Month");
                } else if (etdescription.getText().toString().equals("")) {
                    alertDialog("Please Enter Description");
                } else if (etpurposeoftour.getText().toString().equals("")) {
                    alertDialog("Please Enter Purpose Of Tour");
                } else if (etstartdate.getText().toString().equals("")) {
                    alertDialog("Please Select Start Date");
                } else if (etenddate.getText().toString().equals("")) {
                    alertDialog("Please Select End Date");
                } else if (etplace1.getText().toString().equals("")) {
                    alertDialog("Please Select Tour Place");
                }else if (etmisc.getText().toString().length() > 0 &&!etmisc.getText().toString().equals("0.0") && etremarks.getText().toString().isEmpty()) {
                    alertDialog("Please Enter Remarks");
                }

                    else {

                    Strtourname = ettourname.getText().toString();
                    Strdescription = etdescription.getText().toString();
                    Strtourpurpose = etpurposeoftour.getText().toString();
                    Strstartdate = etstartdate.getText().toString();
                    Strenddate = etenddate.getText().toString();
                    Strtourplace1 = etplace1.getText().toString();
                    Strtourplace2 = etplace2.getText().toString();
                    Strtourplace3 = etplace3.getText().toString();
                    Strtotaltourexpense = ettotal.getText().toString();
                    if (etboarding.getText().toString().isEmpty()) {
                        etboarding.setText("0");
                    } else {
                        Strboard = etboarding.getText().toString();
                    }
                    if (etbuisness.getText().toString().isEmpty()) {
                        etbuisness.setText("0");
                    } else {
                        Strbuisness = etbuisness.getText().toString();
                    }
                    if (etconveyance.getText().toString().isEmpty()) {
                        etconveyance.setText("0");
                    } else {
                        Strconv = etconveyance.getText().toString();
                    }
                    if (etfood.getText().toString().isEmpty()) {
                        etfood.setText("0");
                    } else {
                        Strfood = etfood.getText().toString();
                    }
                    if (etpetrol.getText().toString().isEmpty()) {
                        etpetrol.setText("0");
                    } else {
                        Strfuel = etpetrol.getText().toString();
                    }
                    if (etpostage.getText().toString().isEmpty()) {
                        etpostage.setText("0");
                    } else {
                        Strpostage = etpostage.getText().toString();
                    }
                    if (etprinting.getText().toString().isEmpty()) {
                        etprinting.setText("0");
                    } else {

                        Strprinting=etprinting.getText().toString();
                    }
                    if (ettravel.getText().toString().isEmpty()) {
                        ettravel.setText("0");
                    } else {
                        Strtravel = ettravel.getText().toString();
                    }
                    if (etmisc.getText().toString().isEmpty()) {
                        etmisc.setText("0");
                    } else {
                        Strmisc = etmisc.getText().toString();
                    }

                    addretrofit();
                    }
            }
        });

       editretrofit();
       detailsretrofit1();

    }
    private void addretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Intent i1=getIntent();
        idTourExpenses=i1.getStringExtra("idTourExpenses");

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("idTourExpense", idTourExpenses);
        jsonObject.addProperty("idUser", iduser);
        jsonObject.addProperty("TourPurpose", Strtourpurpose);
        jsonObject.addProperty("monthOfClaim", Strid);
        jsonObject.addProperty("TourName", Strtourname);
        jsonObject.addProperty("TourId", "");
        jsonObject.addProperty("StartDate", Strstartdate);
        jsonObject.addProperty("EndDate", Strenddate);
        jsonObject.addProperty("TourPlace1", Strtourplace1);
        jsonObject.addProperty("TourPlace2", Strtourplace2);
        jsonObject.addProperty("TourPlace3", Strtourplace3);
        jsonObject.addProperty("Remarks", Strremarks);
        jsonObject.addProperty("RemarksWithoutBill", "");
        jsonObject.addProperty("Description", Strdescription);
        jsonObject.addProperty("TotalTourExpense", Strtotaltourexpense);
        jsonObject.addProperty("processBy", iduser);
        jsonObject.addProperty("processType", Strprocesstype);


        JsonArray TourExpenseItem = new JsonArray();
        TourExpenseItem list = new TourExpenseItem(strboarding, strpetrol, strmisc, strBuisness, strpostages,
                strconvey, strprinting, strfood, strtravel,strboarding1, strpetrol1, strmisc1, strBuisness1, strpostages1,
                strconvey1, strprinting1, strfood1, strtravel1);
        TourExpenseItemlist.add(list);
        Log.d("list 1", String.valueOf((TourExpenseItemlist.size())));
        for (int i = 0; i < TourExpenseItemlist.size(); i++) {
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.addProperty("BoardLodge", Strboard);
            jsonObject1.addProperty("BusinessPromo", Strbuisness);
            jsonObject1.addProperty("ConvTravel", Strconv);
            jsonObject1.addProperty("Food", Strfood);
            jsonObject1.addProperty("Fuel", Strfuel);
            jsonObject1.addProperty("PostageCourier", Strpostage);
            jsonObject1.addProperty("Printing", Strprinting);
            jsonObject1.addProperty("Travel", Strtravel);
            jsonObject1.addProperty("Misc", Strmisc);


            Strboard=etboarding.getText().toString();
            Strbuisness=etbuisness.getText().toString();
            Strconv = etconveyance.getText().toString();
            Strfood = etfood.getText().toString();
            Strfuel = etpetrol.getText().toString();
            Strpostage = etpostage.getText().toString();
            Strprinting = etprinting.getText().toString();
            Strtravel = ettravel.getText().toString();
            Strmisc = etmisc.getText().toString();


            TourExpenseItem.add(jsonObject1);
            Log.d("Arraylist", jsonObject1.toString());
        }


        jsonObject.add("TourItemList", TourExpenseItem);
        Log.d("EditAdd:Request", jsonObject.toString());


        Call<JsonArray> call = apiService.ManageTourExpense(jsonObject);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("EditAdd:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {

                        JSONObject temp;
                        temp = jsonArrayorgList.getJSONObject(0);
                        result = Integer.parseInt(temp.getString("result"));
                        resultMessage = temp.getString("resultMessage");
                        MailMsg = temp.getString("MailMsg");
                        WebUrl = temp.getString("WebUrl");
                        AccountManagerMailId = temp.getString("AccountManagerMailId");
                        idTourExpense = temp.getString("idTourExpense");
                        idDirectorExpense = temp.getString("idDirectorExpense");
                        idLocalExpense = temp.getString("idLocalExpense");

                        if (result == 1) {
                            alertDialog1(resultMessage);
                        } else {

                            alertDialog1(resultMessage);
                        }
                    }else{
                        alert("No Records has found");
//                        Toast.makeText(EditAdvTour.this, "No Records has found", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("Server Response", strResponse);


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
//                showToast("Server Connection Failed");
                alert("Server Connection Failed");
            }
        });
    }

    private void editretrofit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("idTourExpenses", idTourExpenses);
        jsonObject.addProperty("cmd", cmd);
        Log.d("AdvanceTourInfo:req", jsonObject.toString());
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.editTour(idTourExpenses, cmd);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("AdvanceTourInfo:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {

                        JSONObject temp;
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);
//                            temp.getString("idTourExpense");
//                            temp.getString("idUser");
                            Strtourpurpose = temp.getString("TourPurpose");
                            Stridclaim = temp.getString("monthOfClaim");
                            Strtourname = temp.getString("TourName");
//                            temp.getString("TourId");
                            Strstartdate = temp.getString("StartDate");
                            Strenddate = temp.getString("EndDate");
                            Strtourplace1 = temp.getString("TourPlace1");
                            Strtourplace2 = temp.getString("TourPlace2");
                            Strtourplace3 = temp.getString("TourPlace3");
                            Strremarks = temp.getString("Remarks");
//                            temp.getString("RemarksWithoutBill");
                            Strdescription = temp.getString("Description");
                            Strtotaltourexpense = temp.getString("TotalTourExpense");
//                            temp.getString("processBy");
//                            temp.getString("processType");

                            ettourname.setText(Strtourname);
                            etdescription.setText(Strdescription);
                            etpurposeoftour.setText(Strtourpurpose);
                            etstartdate.setText(Strstartdate);
                            etenddate.setText(Strenddate);
                            etplace1.setText(Strtourplace1);
                            etplace2.setText(Strtourplace2);
                            etplace3.setText(Strtourplace3);

                            etremarks.setText(Strremarks);
                            ettotal.setText(Strtotaltourexpense);
//                          if(Stridclaim!="0") {
//                              String[] monthid = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
//                              String[] month = {"Select Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//                              Strmonth = month[Integer.parseInt(Stridclaim)];
//                              Strid = monthid[Integer.parseInt(Stridclaim)];
//                              Log.d("Strid", Stridclaim);
//                              monthofclaim.setText(Strmonth);
//                              Log.d("Strmonth", Strmonth);
//
//                          }
                            spinner();
                        }
                    }else{
                        alert("No Records has found");
//                        Toast.makeText(EditAdvTour.this, "No Records has found", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
//                showToast("Server Connection Failed");
                alert("Server Connection Failed");
            }


        });
            }
        private void spinner(){
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final  String[] month = {"Select Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        final String[] monthid = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 String item = parent.getItemAtPosition(position).toString();
                if(Strid=="0") {
                    Strid = monthid[Integer.parseInt(Stridclaim)];
                    Strmonth = month[Integer.parseInt(Stridclaim)];
                    Log.d("Strid", Stridclaim);
                    monthofclaim.setText(Strmonth);
                    Log.d("Strmonth", Strmonth);

                }
                else{
                    int index = spinner.getSelectedItemPosition();
                    Strid = monthid[index];
                    Log.d("index",Strid);
                    item = spinner.getSelectedItem().toString();
                    Log.d("month",item);
                    monthofclaim.setText(item);

                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void alertDialog(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(EditAdvTour.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }
    private void alertDialog1(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(EditAdvTour.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(result==1){
                    finish();
                }
            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }

    private void detailsretrofit1() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("idTourExpense", idTourExpenses);
        jsonObject.addProperty("cmd", cmd);
        Log.d("AdvanceTourSummary:req", jsonObject.toString());
        VimsInterface apiService =VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.editTourSummary(idTourExpenses, cmd);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("AdvanceTourSummary:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {

                        JSONObject temp;

                        temp = jsonArrayorgList.getJSONObject(0);
                        etboarding = (EditText) findViewById(R.id.boarding);
                        etbuisness = (EditText) findViewById(R.id.buisness);
                        etconveyance = (EditText) findViewById(R.id.conveyance);
                        etfood = (EditText) findViewById(R.id.food);
                        etpetrol = (EditText) findViewById(R.id.petrol);
                        etpostage = (EditText) findViewById(R.id.postage);
                        etprinting = (EditText) findViewById(R.id.printing);
                        ettravel = (EditText) findViewById(R.id.travel);
                        etmisc = (EditText) findViewById(R.id.misc);
                        etremarks = (EditText) findViewById(R.id.remarks);
                        ettotal = (EditText) findViewById(R.id.total);


//                        jsonObject1.addProperty("BoardLodge", Strboard);
//                        jsonObject1.addProperty("BusinessPromo", Strbuisness);
//                        jsonObject1.addProperty("ConvTravel", Strconv);
//                        jsonObject1.addProperty("Food", Strfood);
//                        jsonObject1.addProperty("Fuel", Strfuel);
//                        jsonObject1.addProperty("PostageCourier", Strpostage);
//                        jsonObject1.addProperty("Printing", Strprinting);
//                        jsonObject1.addProperty("Travel", Strtravel);
//                        jsonObject1.addProperty("Misc", Strmisc);

                        boardlodge = temp.getString("BoardLodge");
                        buisness = temp.getString("BusinessPromo");
                        convtravel = temp.getString("ConvTravel");
                        food =  temp.getString("Food");
                        fuel = temp.getString("Fuel");
                        postagecourier = temp.getString("PostageCourier");
                        printing = temp.getString("Printing");
                        travel = temp.getString("Travel");
                        misc = temp.getString("Misc");

                        etboarding.setText(boardlodge);
                        etbuisness.setText(buisness);
                        etconveyance.setText(convtravel);
                        etfood.setText(food);
                        etpetrol.setText(fuel);
                        etpostage.setText(postagecourier);
                        etprinting.setText(printing);
                        ettravel.setText(travel);
                        etmisc.setText(misc);

//                        Strboard = temp.getString("BoardLodge");
//                        Strbuisness = temp.getString("BusinessPromo");
//                        Strconv = temp.getString("ConvTravel");
//                        Strfood =  temp.getString("Food");
//                        Strfuel = temp.getString("Fuel");
//                        Strpostage = temp.getString("PostageCourier");
//                        Strprinting = temp.getString("Printing");
//                        Strtravel = temp.getString("Travel");
//                        Strmisc = temp.getString("Misc");
//
//                        etboarding.setText(Strboard);
//                        etbuisness.setText(Strbuisness);
//                        etconveyance.setText(Strconv);
//                        etfood.setText(Strfood);
//                        etpetrol.setText(Strfuel);
//                        etpostage.setText(Strpostage);
//                        etprinting.setText(Strprinting);
//                        ettravel.setText(Strtravel);
//                        etmisc.setText(Strmisc);


                    }else{
                        alert("No Records has found");
//                        Toast.makeText(EditAdvTour.this, "No Records has found", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("Server Response", strResponse);


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                  alert("Server Connection Failed");
            }

//            private void showToast(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
        });
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

    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("beforeTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("onTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("afterTextChanged:", String.valueOf(s));
            totaltourexpenses(String.valueOf(s));
        }
    };
    private void totaltourexpenses(String s) {

        float board, buisness, conveyance, food, fuel, postage, printing, travel, misc;

        Strboard = etboarding.getText().toString();
        Strbuisness = etbuisness.getText().toString();
        Strconv = etconveyance.getText().toString();
        Strfood = etfood.getText().toString();
        Strfuel = etpetrol.getText().toString();
        Strpostage = etpostage.getText().toString();
        Strprinting = etprinting.getText().toString();
        Strtravel = ettravel.getText().toString();
        Strmisc = etmisc.getText().toString();

        if (Strboard.length() > 0) {
            board = Float.parseFloat(Strboard);
        } else {
            board = 0;
        }

        if (Strbuisness.length() > 0) {
            buisness = Float.parseFloat(Strbuisness);
        } else {
            buisness = 0;
        }
        if (Strconv.length() > 0) {
            conveyance = Float.parseFloat(Strconv);
        } else {
            conveyance = 0;
        }
        if (Strfood.length() > 0) {
            food = Float.parseFloat(Strfood);
        } else {
            food = 0;
        }
        if (Strfuel.length() > 0) {
            fuel = Float.parseFloat(Strfuel);
        } else {
            fuel = 0;
        }
        if (Strpostage.length() > 0) {
            postage = Float.parseFloat(Strpostage);
        } else {
            postage = 0;
        }
        if (Strprinting.length() > 0) {
            printing = Float.parseFloat(Strprinting);
        } else {
            printing = 0;
        }
        if (Strtravel.length() > 0) {
            travel = Float.parseFloat(Strtravel);
        } else {
            travel = 0;
        }
        if (Strmisc.length() > 0) {
            misc = Float.parseFloat(Strmisc);
        } else {
            misc = 0;
        }
        float totaltourexpenses = (board + buisness + conveyance + food + fuel + postage + printing + travel + misc);
        ettotal.setText(String.valueOf(totaltourexpenses));
    }

    private void datePicker() {

        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setThemeCustom(R.style.MyBetterPickersRadialTimePickerDialog)
                .setOnDateSetListener(EditAdvTour.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText("OK")
                .setCancelText("Cancel");
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    private String dateFormater(long dateInMillis, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        String dateString = formatter.format(new Date(dateInMillis));
        Log.d("dateString", dateString);
        return dateString;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

        Strstartdate = dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd/MM/yyyy");
        Strenddate = dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd/MM/yyyy");
        if (strdate.equals("1")) {
            etstartdate.setText(Strstartdate);
        } else if (strdate.equals("2")) {
            etenddate.setText(Strenddate);
        }
    }
    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAdvTour.this);
        builder.setTitle(reason);
//        builder.setMessage(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                finish();
            }
        });
        builder.create().show();


    }

}


