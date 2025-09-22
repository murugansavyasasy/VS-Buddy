package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.Usagecountadapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.Usagecountclass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;

public class Usagecount extends AppCompatActivity {

    //implements CalendarDatePickerDialogFragment.OnDateSetListener

    String schoolid, schoolname, voicecount, smscount, strusageyear = "1";
    Spinner spin_usageyear;
    Usagecountadapter usageadap;
    ArrayList<Usagecountclass> arrayList;
    RecyclerView rvusagelist;
    TextView tv_usageyear, lblSMS_Allocated;
    private ArrayList<Usagecountclass> datasListschool = new ArrayList<>();

    Button GetUsageCount;
    TextView FromDate, ToDate;
    String strDate, strCurrentDate, timeString, strTime;//strDuration
    int selDay, selMonth, selYear;
    String selHour, selMin;
    int minimumHour, minimumMinute;
    String strfromdate, strtodate, strreason, strdatevalue;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usagecount);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        rvusagelist = (RecyclerView) findViewById(R.id.rvUsagecount);
        spin_usageyear = (Spinner) findViewById(R.id.spin_usage);
        tv_usageyear = (TextView) findViewById(R.id.usage_textyear);
        lblSMS_Allocated = (TextView) findViewById(R.id.lblSMS_Allocated);

        String[] itemusageyear = {"2017-2018", "2018-2019"};
        final String[] itemIdusageyear = {"1", "0"};

        FromDate = (TextView) findViewById(R.id.FromDate);
        ToDate = (TextView) findViewById(R.id.ToDate);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        GetUsageCount = (Button) findViewById(R.id.GetUsageCount);
        FromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                selYear = mcurrentDate.get(Calendar.YEAR);
                selMonth = mcurrentDate.get(Calendar.MONTH);
                selDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(Usagecount.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        // String myFormat = "dd/MM/yyyy"; //Change as you need
                        String myFormat = "yyyy-MM-dd"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                        FromDate.setText(sdf.format(myCalendar.getTime()));


                        // holder.ExamDate.setText(sdf.format(myCalendar.getTime()));

                        Log.d("date", sdf.format(myCalendar.getTime()));

                        selDay = selectedday;
                        selMonth = selectedmonth;
                        selYear = selectedyear;
                    }
                }, selYear, selMonth, selDay);

                mDatePicker.show();

//                strdatevalue = "1";
//                datePicker();
            }
        });
        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                selYear = mcurrentDate.get(Calendar.YEAR);
                selMonth = mcurrentDate.get(Calendar.MONTH);
                selDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(Usagecount.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        // String myFormat = "dd/MM/yyyy"; //Change as you need
                        String myFormat = "yyyy-MM-dd"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                        ToDate.setText(sdf.format(myCalendar.getTime()));

                        // holder.ExamDate.setText(sdf.format(myCalendar.getTime()));

                        Log.d("date", sdf.format(myCalendar.getTime()));

                        selDay = selectedday;
                        selMonth = selectedmonth;
                        selYear = selectedyear;
                    }
                }, selYear, selMonth, selDay);

                mDatePicker.show();


//                strdatevalue = "2";
//                datePicker();
            }

        });

        setMinDateTime();

        GetUsageCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String From = FromDate.getText().toString();
                String To = ToDate.getText().toString();
                tv_usageyear.setText("Usage year From " + From + " To " + To);
                usagelistretrofit(From, To);


            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            schoolid = extras.getString("SCHOOLIDusage");
            schoolname = extras.getString("SCHOOLNAMEusage");
        }
        usageadap = new Usagecountadapter(datasListschool, schoolid, schoolname, Usagecount.this);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(Usagecount.this);
        rvusagelist.setLayoutManager(LayoutManager);
        rvusagelist.setItemAnimator(new DefaultItemAnimator());
        rvusagelist.setAdapter(usageadap);

        ArrayAdapter<String> adapterusageyear = new ArrayAdapter<String>(Usagecount.this, android.R.layout.simple_spinner_item, itemusageyear);
        adapterusageyear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_usageyear.setAdapter(adapterusageyear);
        spin_usageyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spin_usageyear.getSelectedItemPosition();
                strusageyear = itemIdusageyear[index];
//                financialyear = Spin_financialyear.getSelectedItem().toString();
                Log.d("usageyear", strusageyear);

                //  usagelistretrofit(strusageyear,"");
                if (strusageyear.equals("1")) {
                    tv_usageyear.setText("Usage year from 2017 to 2018");
                } else {
                    tv_usageyear.setText("Usage year from 2018 to 2019");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // usagelistretrofit(strusageyear,"");
    }

    private void setMinDateTime() {
//        if(strdatevalue.equals("1")){
//        FromDate.setText(dateFormater(System.currentTimeMillis(), "dd MMM yyyy"));
//        strfromdate = dateFormater(System.currentTimeMillis(), "dd-MM-yyyy");

        FromDate.setText(dateFormater(System.currentTimeMillis(), "yyyy-MM-dd"));
        strfromdate = dateFormater(System.currentTimeMillis(), "yyyy-MM-dd");

//        }
//        else{
        ToDate.setText(dateFormater(System.currentTimeMillis(), "yyyy-MM-dd"));
        strtodate = dateFormater(System.currentTimeMillis(), "yyyy-MM-dd");
//        }

//        strCurrentDate = strDate;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);

        selDay = cal.get(Calendar.DAY_OF_MONTH);
        selMonth = cal.get(Calendar.MONTH);// - 1;
        selYear = cal.get(Calendar.YEAR);

        minimumHour = cal.get(Calendar.HOUR_OF_DAY);
        minimumMinute = cal.get(Calendar.MINUTE);

        selHour = Integer.toString(minimumHour);
        selMin = Integer.toString(minimumMinute);


//        tvTime.setText(timeFormater(minimumHour, minimumMinute));
//        strDuration = "30";
    }

    private String dateFormater(long dateInMillis, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        String dateString = formatter.format(new Date(dateInMillis));
        Log.d("dateString", dateString);
        return dateString;
    }

    private void usagelistretrofit(String FromDate, String ToDate) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("schoolID", schoolid);
        //jsonObject.addProperty("Code", usagecode);
        jsonObject.addProperty("FromDate", FromDate);
        jsonObject.addProperty("ToDate", ToDate);
        Log.d("Listdemo:req", jsonObject.toString());
        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<JsonArray> call = apiService.GetUsageReport(jsonObject);

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                try {
                    Log.d("Listdemo;Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        datasListschool.clear();
                        Usagecountclass tempData1 = new Usagecountclass();
                        for (int i = 0; i < jsonArrayorgList.length(); i++) {
                            temp = jsonArrayorgList.getJSONObject(i);

                            tempData1.setTotal_voiceusage(temp.getString("VoiceUsage"));
                            tempData1.setVoiceAllocated(temp.getString("VoiceAllocated"));
                            tempData1.setTotal_smsusage(temp.getString("SMSUsage"));
                            tempData1.setSMSAllocated(temp.getString("SMSAllocated"));


                            if (temp.has("erp_module_usage")) {
                                tempData1.setErp_module_usage(temp.getString("erp_module_usage"));
                            } else {

                            }


                            if (temp.has("AppUsageCount")) {
                                tempData1.setAppUsage(temp.getString("AppUsageCount"));
                            } else {
                                tempData1.setAppUsage("");
                            }

                            Log.d("Server Array", temp.toString());
                            datasListschool.add(tempData1);

                        }

                        arrayList = new ArrayList<>();
                        arrayList.addAll(datasListschool);

                        usageadap.notifyDataSetChanged();
                    } else {
                        Alert("No data Received. Try Again.");
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

    private void Alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Usagecount.this);
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
//    private void showToast(String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

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


//    @Override
//    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
//
//        selDay = dayOfMonth;
//        selMonth = monthOfYear;
//        selYear = year;
//
//        if (strdatevalue.equals("1")) {
//            FromDate.setText(dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd MMM yyyy"));
//            strfromdate = dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd-MM-yyyy");
//        } else if (strdatevalue.equals("2")) {
//            ToDate.setText(dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd MMM yyyy"));
//            strtodate = dateFormater(dialog.getSelectedDay().getDateInMillis(), "dd-MM-yyyy");
//
//        }
//
//    }


}
