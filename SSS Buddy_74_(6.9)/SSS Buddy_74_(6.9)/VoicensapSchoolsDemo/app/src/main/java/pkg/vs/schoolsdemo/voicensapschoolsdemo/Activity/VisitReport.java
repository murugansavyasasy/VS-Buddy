package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.Priority;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.Today_Visit_NewSchool;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripRequest;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripResponse;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VisitReport extends AppCompatActivity {
    LinearLayout linearnewschool;
    Button btnnewschool, btn_submit, btnStartVisit, btnEndVisit;

    ImageView imgDotStart;
    SharedPreferences shpRemember;
    SharedPreferences.Editor ed;
    static String currentdate, currentdate1;
    LinearLayout Lner_Date;
//    static TextView lbl_resonoflateupdate;
    TextView lbl_specificPersionmet;
    TextView lbl_specificstatus;
    EditText visit_SpecificStatus;
    EditText visit_Specific, visit_District;
//    static EditText visit_lateUpdate;
    static TextView startdate;
    Spinner spinner, spinnerPersionmet;
    private ArrayList<LinearLayout> linearlayoutlist = new ArrayList<>();
    EditText et_schoolname, et_schoolarea, et_princiname, et_princino, et_remarks;
    String schoolname, Date, princiname, princino, remarks, userId, schoolarea, district, Date_oflate_update, Date_oflate_updatenew, Status_Specify, PersonMet_Specify;
    private List<Today_Visit_NewSchool> Today_Visit_NewSchool = new ArrayList<>();
    Today_Visit_NewSchool data1;
    private static final String SH_USERS = "userInfo";
    private static final String SH_USERID = "UserId";
    ArrayList<String> Status = new ArrayList<String>();
    ArrayList<String> PersonmetSpinner = new ArrayList<String>();
    TextView Newschooldate, lbl_resonoflateupdateNewSchool, lbl_specificPersionmetNewSchool, lbl_specificstatusNewSchool;
    EditText Datelateupdate, schoolnamenew, areanewschool, Districtnewschool, personnamenewschool, contactnamenewschool, personmetnewschool, Remarknewschool, statusnewschool;
    Spinner personmetnew, statusnew;
    String ReasonSpinner, PersonmetSpinning, ReasonSpinnerSchool, PersonmetSpinningSchool, Latitude, Longitude;
    String schoolnamenew12, area, personname, contactnumber, Remaek, Dateofview, DistrictnewSchool, Lateupdate, ReasonSpinnerNewSchool, PersonmetSpinningNewSchool;
    String Entervisitername, EnterResonofVisit;

    private FusedLocationProviderClient fusedLocationProviderClient;
    double latitude = 0.0, longitude = 0.0;
    String address = "";

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int GPS_REQUEST_CODE = 101;
    private static final String KEY_VISIT_STARTED = "visitStarted";
    private static final String KEY_VISIT_ENDED = "visitEnded";


    private Location startLocation;
    private List<Location> schoolLocations = new ArrayList<>();
    private Location endLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_report);
        initViews();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        checkGPSAndRequestLocation();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        shpRemember = getSharedPreferences("SH_USERS", MODE_PRIVATE);
        boolean isStarted = shpRemember.getBoolean(KEY_VISIT_STARTED, false);
        boolean isEnded = shpRemember.getBoolean(KEY_VISIT_ENDED, false);
        if (isStarted && !isEnded) {
            imgDotStart.setVisibility(View.VISIBLE);
            btnStartVisit.setEnabled(false);
            btnEndVisit.setEnabled(true);
            enableFieldsAfterStart();
        } else {
            imgDotStart.setVisibility(View.GONE);
            btnStartVisit.setEnabled(true);
            btnEndVisit.setEnabled(false);
            disableFieldsBeforeStart();
        }

        Status.add(" ");
        Status.add("Couldn't meet");
        Status.add("Met, couldn't give demo");
        Status.add("Met and demo given");
        Status.add("To sign/set up POC");
        Status.add("To sign PO");
        Status.add("Training to existing POC/live school");
        Status.add("General visit");
        Status.add("Payment collection");
        Status.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Status);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spinner.getSelectedItemPosition();
                ReasonSpinner = spinner.getSelectedItem().toString();
                if (ReasonSpinner.equals(" ")) {
                    ReasonSpinner = "Select";
                }
                if (index == 9) {
                    lbl_specificstatus.setVisibility(View.VISIBLE);
                    visit_SpecificStatus.setVisibility(View.VISIBLE);
                } else {
                    lbl_specificstatus.setVisibility(View.GONE);
                    visit_SpecificStatus.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        PersonmetSpinner.add(" ");
        PersonmetSpinner.add("Owner");
        PersonmetSpinner.add("Chairman");
        PersonmetSpinner.add("Principle");
        PersonmetSpinner.add("Admin");
        PersonmetSpinner.add("Others");

        ArrayAdapter<String> PersonmetAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PersonmetSpinner);
        PersonmetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPersionmet.setAdapter(PersonmetAdapter);

        spinnerPersionmet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spinnerPersionmet.getSelectedItemPosition();
                PersonmetSpinning = spinnerPersionmet.getSelectedItem().toString();
                if (PersonmetSpinning.equals(" ")) {
                    PersonmetSpinning = "Select";
                }
                if (index == 5) {
                    visit_Specific.setVisibility(View.VISIBLE);
                    lbl_specificPersionmet.setVisibility(View.VISIBLE);
                } else {
                    visit_Specific.setVisibility(View.GONE);
                    lbl_specificPersionmet.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        String date_n = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        startdate.setText(date_n);
        currentdate = startdate.getText().toString();

        Lner_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(VisitReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = sdf.format(selectedDate.getTime());

                        startdate.setText(formattedDate);

                        if (currentdate.equals(startdate.getText().toString())) {
                            // visit_lateUpdate.setVisibility(View.GONE);
                            // lbl_resonoflateupdate.setVisibility(View.GONE);
                        } else {
                            // visit_lateUpdate.setVisibility(View.VISIBLE);
                            // lbl_resonoflateupdate.setVisibility(View.VISIBLE);
                        }
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
//        shpRemember = getSharedPreferences(SH_USERS, MODE_PRIVATE);


        btnStartVisit.setOnClickListener(v -> {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                new AlertDialog.Builder(VisitReport.this)
                        .setTitle("Enable Location")
                        .setMessage("Location is turned off. Please enable location to start the visit.")
                        .setPositiveButton("Enable", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                new AlertDialog.Builder(VisitReport.this)
                        .setTitle("Start Visit")
                        .setMessage("Are you sure you want to start this visit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            checkGPSAndRequestLocation();

                            fetchCurrentLocation((lat, lng, addr) -> {
                                String userId = SharedPreference_class.getUserid(VisitReport.this);

                                SharedPreferences.Editor editor = shpRemember.edit();
                                editor.putBoolean(KEY_VISIT_STARTED, true);
                                editor.putBoolean(KEY_VISIT_ENDED, false);
                                editor.putString("START_LOCATION", lat + "," + lng + " (" + addr + ")");
                                editor.apply();

                                imgDotStart.setVisibility(View.VISIBLE);
                                btnStartVisit.setEnabled(false);
                                btnEndVisit.setEnabled(true);
//                                btnEndVisit.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                                enableFieldsAfterStart();
                                sendTripDetails(userId, "start", lat, lng);
                                Toast.makeText(this, "Visit Started", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
                schoolLocations.clear();
            }
        });


        btnEndVisit.setOnClickListener(v -> {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isGPSEnabled) {
                new AlertDialog.Builder(VisitReport.this)
                        .setTitle("Enable Location")
                        .setMessage("Location is turned off. Please enable location to end the visit.")
                        .setPositiveButton("Enable", (dialog, which) -> {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                new AlertDialog.Builder(VisitReport.this)
                        .setTitle("End Visit")
                        .setMessage("Are you sure you want to end this visit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            checkGPSAndRequestLocation();
                            fetchCurrentLocation((lat, lng, addr) -> {
                                String userId = SharedPreference_class.getUserid(VisitReport.this);
                                SharedPreferences.Editor editor = shpRemember.edit();
                                editor.putBoolean(KEY_VISIT_ENDED, true);
                                editor.putBoolean(KEY_VISIT_STARTED, false);
                                editor.putString("END_LOCATION", lat + "," + lng + " (" + addr + ")");
                                editor.apply();
                                imgDotStart.setVisibility(View.GONE);
                                btnStartVisit.setEnabled(true);
                                btnEndVisit.setEnabled(false);
//                                btnEndVisit.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundsplash));
                                disableFieldsBeforeStart();
                                sendTripDetails(userId, "stop", lat, lng);
                                Toast.makeText(this, "Visit End", Toast.LENGTH_SHORT).show();
                            });
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!isGPSEnabled) {
                    new AlertDialog.Builder(VisitReport.this)
                            .setTitle("Enable Location")
                            .setMessage("Location is turned off. Please enable location to submit your visit.")
                            .setPositiveButton("Enable", (dialog, which) -> {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .show();
                    return;
                }
                String userId = SharedPreference_class.getUserid(VisitReport.this);
                if (userId == null || userId.isEmpty()) {
                    Toast.makeText(VisitReport.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (linearlayoutlist.size() == 0) {
                    schoolname = et_schoolname.getText().toString().trim();
                    princiname = et_princiname.getText().toString().trim();
                    princino = et_princino.getText().toString().trim();
                    remarks = et_remarks.getText().toString().trim();
                    district = visit_District.getText().toString().trim();
                    schoolarea = et_schoolarea.getText().toString().trim();
//                    Date_oflate_update = visit_lateUpdate.getText().toString().trim();
                    Status_Specify = visit_SpecificStatus.getText().toString().trim();
                    PersonMet_Specify = visit_Specific.getText().toString().trim();
                    Date = startdate.getText().toString().trim();
                        if (schoolname.isEmpty()) {
                            et_schoolname.setError("Enter the school name");
                        } else {
                            if (schoolarea.isEmpty()) {
                                et_schoolarea.setError("Enter the School Area");
                            } else {
                                if (district.isEmpty()) {
                                    visit_District.setError("Enter the District");
                                } else {
                                    if (PersonmetSpinning.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the PersonMet");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (PersonmetSpinning.equals("Others")) {

                                        if (visit_Specific.getVisibility() == View.VISIBLE && PersonMet_Specify.isEmpty()) {
                                            visit_Specific.setError("Enter the Person");
                                        } else {
                                            PersonmetSpinning = "Others /" + visit_Specific.getText().toString();
                                        }
                                    }
                                    if (ReasonSpinner.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the Reason");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (ReasonSpinner.equals("Others")) {
                                        if (visit_SpecificStatus.getVisibility() == View.VISIBLE && Status_Specify.isEmpty()) {
                                            visit_SpecificStatus.setError("Enter the Reason");
                                        } else {
                                            ReasonSpinner = "Others / " + visit_SpecificStatus.getText().toString();
                                            AlertDialog.Builder adb = new AlertDialog.Builder(VisitReport.this);
                                            adb.setTitle("Are you sure to submit");
                                            adb.setIcon(android.R.drawable.ic_dialog_alert);
                                            adb.setPositiveButton("OK", (dialog, which) -> {
                                                fetchCurrentLocation((lat, lng, addr) -> {
                                                    Location loc = new Location("");
                                                    loc.setLatitude(lat);
                                                    loc.setLongitude(lng);
                                                    schoolLocations.add(loc);
                                                    Toast.makeText(VisitReport.this, "Visit Submitted", Toast.LENGTH_SHORT).show();
                                                    todayvisitretrofit(userId, String.valueOf(lat), String.valueOf(lng));
                                                });
                                            });
                                            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            });
                                            adb.show();
                                        }
                                    } else {
                                        AlertDialog.Builder adb = new AlertDialog.Builder(VisitReport.this);
                                        adb.setTitle("Are you sure to submit");
                                        adb.setIcon(android.R.drawable.ic_dialog_alert);
                                        adb.setPositiveButton("OK", (dialog, which) -> {
                                                fetchCurrentLocation((lat, lng, addr) -> {
                                                    Location loc = new Location("");
                                                    loc.setLatitude(lat);
                                                    loc.setLongitude(lng);
                                                    schoolLocations.add(loc);
                                                    Toast.makeText(VisitReport.this, "Visit Submitted", Toast.LENGTH_SHORT).show();
                                                    todayvisitretrofit(userId, String.valueOf(lat), String.valueOf(lng));
                                                });
                                        });
                                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                        adb.show();
                                    }
                                }
                            }
                    }

                } else {
                    schoolnamenew12 = schoolnamenew.getText().toString();
                    area = areanewschool.getText().toString();
                    personname = personnamenewschool.getText().toString();
                    contactnumber = contactnamenewschool.getText().toString();
                    Remaek = Remarknewschool.getText().toString();
                    Dateofview = Datelateupdate.getText().toString();
                    DistrictnewSchool = Districtnewschool.getText().toString();
                    Lateupdate = Newschooldate.getText().toString();
                    ReasonSpinnerNewSchool = ReasonSpinnerSchool;
                    PersonmetSpinningNewSchool = PersonmetSpinningSchool;
                    Entervisitername = personmetnewschool.getText().toString();
                    EnterResonofVisit = statusnewschool.getText().toString();
                    if (Datelateupdate.getVisibility() == View.VISIBLE && Dateofview.isEmpty()) {
                        Datelateupdate.setError("Enter your late update Reason");
                    } else {
                        if (schoolnamenew12.isEmpty()) {
                            schoolnamenew.setError("Enter the school name");
                        } else {
                            if (area.isEmpty()) {
                                areanewschool.setError("Enter the School Area");
                            } else {
                                if (DistrictnewSchool.isEmpty()) {
                                    Districtnewschool.setError("Enter the District");
                                } else {
                                    if (PersonmetSpinningSchool.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the PersonMet");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (PersonmetSpinningSchool.equals("Others")) {
                                        if (personmetnewschool.getVisibility() == View.VISIBLE && Entervisitername.isEmpty()) {
                                            personmetnewschool.setError("Enter the Person");
                                        } else {
                                            PersonmetSpinningSchool = "Others /" + personmetnewschool.getText().toString();
                                        }
                                    }
                                    if (ReasonSpinnerSchool.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the Reason");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (ReasonSpinnerSchool.equals("Others")) {
                                        if (statusnewschool.getVisibility() == View.VISIBLE && EnterResonofVisit.isEmpty()) {
                                            statusnewschool.setError("Enter the Reason");
                                        } else {
                                            ReasonSpinnerSchool = "Others / " + statusnewschool.getText().toString();
                                            if (btnnewschool.getText().equals("SAVE & ADD NEW SCHOOL")) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                                builder.setTitle("save your visit school data");
                                                builder.setCancelable(false);
                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        builder.create().dismiss();
                                                    }
                                                });
                                                builder.create().show();
                                            } else {
                                                AlertDialog.Builder adb = new AlertDialog.Builder(VisitReport.this);
                                                adb.setTitle("Are you sure to submit");
                                                adb.setIcon(android.R.drawable.ic_dialog_alert);
                                                adb.setPositiveButton("OK", (dialog, which) -> {
                                                });
                                                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                });
                                                adb.show();
                                            }
                                        }
                                    } else {
                                        if (btnnewschool.getText().equals("SAVE & ADD NEW SCHOOL")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                            builder.setTitle("save your visit school data");
                                            builder.setCancelable(false);
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    builder.create().dismiss();
                                                }
                                            });
                                            builder.create().show();
                                        } else {
                                            AlertDialog.Builder adb = new AlertDialog.Builder(VisitReport.this);
                                            adb.setTitle("Are you sure to submit");
                                            adb.setIcon(android.R.drawable.ic_dialog_alert);
                                            adb.setPositiveButton("OK", (dialog, which) -> {
                                            });
                                            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            });
                                            adb.show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        btnnewschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (linearlayoutlist.size() > 0) {
                    schoolnamenew12 = schoolnamenew.getText().toString();
                    area = areanewschool.getText().toString();
                    personname = personnamenewschool.getText().toString();
                    contactnumber = contactnamenewschool.getText().toString();
                    Remaek = Remarknewschool.getText().toString();
                    Dateofview = Datelateupdate.getText().toString();
                    DistrictnewSchool = Districtnewschool.getText().toString();
                    Lateupdate = Newschooldate.getText().toString();
                    ReasonSpinnerNewSchool = ReasonSpinnerSchool;
                    PersonmetSpinningNewSchool = PersonmetSpinningSchool;
                    Entervisitername = personmetnewschool.getText().toString();
                    EnterResonofVisit = statusnewschool.getText().toString();
                    if (Datelateupdate.getVisibility() == View.VISIBLE && Dateofview.isEmpty()) {
                        Datelateupdate.setError("Enter your late update Reason");
                    } else {
                        if (schoolnamenew12.isEmpty()) {
                            schoolnamenew.setError("Enter the school name");
                        } else {
                            if (area.isEmpty()) {
                                areanewschool.setError("Enter the School Area");
                            } else {
                                if (DistrictnewSchool.isEmpty()) {
                                    Districtnewschool.setError("Enter the District");
                                } else {
                                    if (PersonmetSpinningSchool.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the PersonMet");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (PersonmetSpinningSchool.equals("Others")) {

                                        if (personmetnewschool.getVisibility() == View.VISIBLE && Entervisitername.isEmpty()) {
                                            personmetnewschool.setError("Enter the Person");
                                        } else {
                                            PersonmetSpinningSchool = "Others /" + personmetnewschool.getText().toString();
                                        }
                                    }
                                    if (ReasonSpinnerSchool.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the Reason");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (ReasonSpinnerSchool.equals("Others")) {
                                        if (statusnewschool.getVisibility() == View.VISIBLE && EnterResonofVisit.isEmpty()) {
                                            statusnewschool.setError("Enter the Reason");
                                        } else {
                                            ReasonSpinnerSchool = "Others / " + statusnewschool.getText().toString();
                                            Add_New_School();
                                        }
                                    } else {
                                        Add_New_School();
                                    }
                                }
                            }
                        }
                    }

                } else {
                    schoolname = et_schoolname.getText().toString().trim();
                    princiname = et_princiname.getText().toString().trim();
                    princino = et_princino.getText().toString().trim();
                    remarks = et_remarks.getText().toString().trim();
                    district = visit_District.getText().toString().trim();
                    schoolarea = et_schoolarea.getText().toString().trim();
//                    Date_oflate_update = visit_lateUpdate.getText().toString().trim();
                    Status_Specify = visit_SpecificStatus.getText().toString().trim();
                    PersonMet_Specify = visit_Specific.getText().toString().trim();
                    Date = startdate.getText().toString().trim();
                        if (schoolname.isEmpty()) {
                            et_schoolname.setError("Enter the school name");
                        } else {
                            if (schoolarea.isEmpty()) {
                                et_schoolarea.setError("Enter the School Area");
                            } else {
                                if (district.isEmpty()) {
                                    visit_District.setError("Enter the District");
                                } else {
                                    if (PersonmetSpinning.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the PersonMet");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();
                                    } else if (PersonmetSpinning.equals("Others")) {

                                        if (visit_Specific.getVisibility() == View.VISIBLE && PersonMet_Specify.isEmpty()) {
                                            visit_Specific.setError("Enter the Person");
                                        } else {
                                            PersonmetSpinning = "Others /" + visit_Specific.getText().toString();
                                        }
                                    }
                                    if (ReasonSpinner.equals("Select")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitReport.this);
                                        builder.setTitle("Select the Reason");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                builder.create().dismiss();
                                            }
                                        });
                                        builder.create().show();

                                    } else if (ReasonSpinner.equals("Others")) {
                                        if (visit_SpecificStatus.getVisibility() == View.VISIBLE && Status_Specify.isEmpty()) {
                                            visit_SpecificStatus.setError("Enter the Reason");
                                        } else {
                                            ReasonSpinner = "Others / " + visit_SpecificStatus.getText().toString();
                                            Add_New_School();
                                        }
                                    } else {
                                        Add_New_School();
                                    }
                                }
                            }
                        }
                    }
                }
        });
    }

    private void initViews() {
        linearnewschool = (LinearLayout) findViewById(R.id.llnewschool);
        btnnewschool = (Button) findViewById(R.id.btnstatus_addnewschool);
        btnStartVisit = findViewById(R.id.btnStartVisit);
        btnEndVisit = findViewById(R.id.btnEndVisit);
        imgDotStart = findViewById(R.id.imgDotStart);
        btn_submit = (Button) findViewById(R.id.btntodayvisitstatus_submit);
        et_schoolname = (EditText) findViewById(R.id.visit_status_schoolname1);
        et_schoolarea = (EditText) findViewById(R.id.visit_status_schoolnamearea1);
        et_princiname = (EditText) findViewById(R.id.visit_status_principalname1);
        et_princino = (EditText) findViewById(R.id.visit_status_principalno1);
        et_remarks = (EditText) findViewById(R.id.visit_status_dailyremarks1);
        spinner = (Spinner) findViewById(R.id.spinner);
        Lner_Date = (LinearLayout) findViewById(R.id.Lner_Date);
        startdate = (TextView) findViewById(R.id.startdate);
        spinnerPersionmet = (Spinner) findViewById(R.id.spinnerPersionmet);
        visit_District = (EditText) findViewById(R.id.visit_District);
//        visit_lateUpdate = (EditText) findViewById(R.id.visit_lateUpdate);
//        lbl_resonoflateupdate = (TextView) findViewById(R.id.lbl_resonoflateupdate);
        lbl_specificstatus = (TextView) findViewById(R.id.lbl_specificstatus);
        visit_SpecificStatus = (EditText) findViewById(R.id.visit_SpecificStatus);
        visit_Specific = (EditText) findViewById(R.id.visit_Specific);
        lbl_specificPersionmet = (TextView) findViewById(R.id.lbl_specificPersionmet);
    }

    private void disableFieldsBeforeStart() {
        et_schoolname.setEnabled(false);
        et_schoolarea.setEnabled(false);
        et_princiname.setEnabled(false);
        et_princino.setEnabled(false);
        et_remarks.setEnabled(false);
        visit_District.setEnabled(false);
//        visit_lateUpdate.setEnabled(false);
        visit_Specific.setEnabled(false);
        visit_SpecificStatus.setEnabled(false);
        spinner.setEnabled(false);
        spinnerPersionmet.setEnabled(false);
        btn_submit.setEnabled(false);
        btnnewschool.setEnabled(false);

    }

    private void enableFieldsAfterStart() {
        et_schoolname.setEnabled(true);
        et_schoolarea.setEnabled(true);
        et_princiname.setEnabled(true);
        et_princino.setEnabled(true);
        et_remarks.setEnabled(true);
        visit_District.setEnabled(true);
//        visit_lateUpdate.setEnabled(true);
        visit_Specific.setEnabled(true);
        visit_SpecificStatus.setEnabled(true);
        spinner.setEnabled(true);
        spinnerPersionmet.setEnabled(true);
        btn_submit.setEnabled(true);
        btnnewschool.setEnabled(true);
    }

    private void clearAllFields() {
        et_schoolname.setText("");
        et_schoolarea.setText("");
        et_princiname.setText("");
        et_princino.setText("");
        et_remarks.setText("");
        visit_District.setText("");
//        visit_lateUpdate.setText("");
        visit_Specific.setText("");
        visit_SpecificStatus.setText("");
        startdate.setText("");
        spinner.setSelection(0);
        spinnerPersionmet.setSelection(0);
        if (schoolnamenew != null) schoolnamenew.setText("");
        if (areanewschool != null) areanewschool.setText("");
        if (personnamenewschool != null) personnamenewschool.setText("");
        if (contactnamenewschool != null) contactnamenewschool.setText("");
        if (Remarknewschool != null) Remarknewschool.setText("");
        if (Datelateupdate != null) Datelateupdate.setText("");
        if (Districtnewschool != null) Districtnewschool.setText("");
        if (Newschooldate != null) Newschooldate.setText("");
        if (personmetnewschool != null) personmetnewschool.setText("");
        if (statusnewschool != null) statusnewschool.setText("");
        if (spinner != null) spinner.setSelection(0);
        if (spinnerPersionmet != null) spinnerPersionmet.setSelection(0);
    }


    @SuppressLint("MissingPermission")
    private void fetchCurrentLocation(LocationCallbackListener listener) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        handleLocation(location, listener);
                    } else {
                        requestNewLocation(fusedLocationClient, listener);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Location fetch failed", Toast.LENGTH_SHORT).show();
                    requestNewLocation(fusedLocationClient, listener);
                });
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocation(FusedLocationProviderClient fusedLocationClient, LocationCallbackListener listener) {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMaxUpdates(1)
                .build();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    handleLocation(location, listener);
                }
                fusedLocationClient.removeLocationUpdates(this);
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void handleLocation(Location location, LocationCallbackListener listener) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String addressStr = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                addressStr = addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onLocationFetched(latitude, longitude, addressStr);
    }


    private void sendLocationToListener(Location location, LocationCallbackListener listener) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String addr = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && !addresses.isEmpty()) {
                addr = addresses.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("VisitReport", "Latitude: " + lat + ", Longitude: " + lng + ", Address: " + addr);
        listener.onLocationFetched(lat, lng, addr);
    }


    interface LocationCallbackListener {
        void onLocationFetched(double lat, double lng, String address);
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setInterval(5000);
                        locationRequest.setFastestInterval(2000);
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    Location location = locationResult.getLastLocation();
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
//                                    Toast.makeText(VisitReport.this, "latitude " + latitude, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(VisitReport.this, "longitude " + longitude, Toast.LENGTH_SHORT).show();
                                    Log.d("latitude", "Latitude: " + latitude);
                                    Log.d("Longitude", "Longitude: " + longitude);
                                    try {
                                        Geocoder geocoder = new Geocoder(VisitReport.this, Locale.getDefault());
                                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                        if (addresses != null && !addresses.isEmpty()) {
                                            address = addresses.get(0).getAddressLine(0);
                                            Log.d("Address", "Address: " + address);
//                                            Toast.makeText(VisitReport.this, "Location: " + address, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.e("Report", "Geocoder error: " + e.getMessage());
                                    }
//                                    imgDotStart.setVisibility(View.VISIBLE);
                                    fusedLocationProviderClient.removeLocationUpdates(this);
                                }
                            }
                        }, Looper.getMainLooper());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(VisitReport.this, "Location permission denied", Toast.LENGTH_SHORT).show();
                        Log.w("VisitReport", "Location permission denied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void checkLocationSettingsAndFetchLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, locationSettingsResponse -> {
            getCurrentLocation();
        });
        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ((ResolvableApiException) e).startResolutionForResult(this, GPS_REQUEST_CODE);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void checkPermissionsAndStartLocationFlow() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            checkLocationSettingsAndFetchLocation(); // Permission already granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettingsAndFetchLocation();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkGPSAndRequestLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, locationSettingsResponse -> {
        });
        task.addOnFailureListener(this, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ((ResolvableApiException) e).startResolutionForResult(this, GPS_REQUEST_CODE);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Unable to access GPS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Add_New_School() {
        if (btnnewschool.getText().toString().equals("ADD NEW SCHOOL")) {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.addnewschoolstatus, null);
            Newschooldate = (TextView) addView.findViewById(R.id.startdateNewSchool);
            lbl_specificstatusNewSchool = (TextView) addView.findViewById(R.id.lbl_specificstatusNewSchool);
//            lbl_resonoflateupdateNewSchool = (TextView) addView.findViewById(R.id.lbl_resonoflateupdateNewSchool);
            lbl_specificPersionmetNewSchool = (TextView) addView.findViewById(R.id.lbl_specificPersionmetNewSchool);
//            Datelateupdate = (EditText) addView.findViewById(R.id.visit_lateUpdateNewSchool);
            schoolnamenew = (EditText) addView.findViewById(R.id.visit_status_schoolname1NewSchool);
            areanewschool = (EditText) addView.findViewById(R.id.visit_status_schoolnamearea1NewSchool);
            Districtnewschool = (EditText) addView.findViewById(R.id.visit_DistrictNewSchool);
            personnamenewschool = (EditText) addView.findViewById(R.id.visit_status_principalname1NewSchool);
            contactnamenewschool = (EditText) addView.findViewById(R.id.visit_status_principalno1NewSchool);
            personmetnewschool = (EditText) addView.findViewById(R.id.visit_SpecificNewSchool);
            Remarknewschool = (EditText) addView.findViewById(R.id.visit_status_dailyremarks1NewSchool);
            statusnewschool = (EditText) addView.findViewById(R.id.visit_SpecificStatusNewSchool);
            personmetnew = (Spinner) addView.findViewById(R.id.spinnerPersionmetNewSchool);
            statusnew = (Spinner) addView.findViewById(R.id.spinnerNewSchool);
            String date_n = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());
            Newschooldate.setText(date_n);
            currentdate1 = Newschooldate.getText().toString();
            Newschooldate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(VisitReport.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Newschooldate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            if (currentdate1.equals(Newschooldate.getText().toString())) {
//                                lbl_resonoflateupdateNewSchool.setVisibility(View.GONE);
                                Datelateupdate.setVisibility(View.GONE);
                            } else {
//                                lbl_resonoflateupdateNewSchool.setVisibility(View.VISIBLE);
                                Datelateupdate.setVisibility(View.VISIBLE);

                            }
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });

            ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.schoolremove);
            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout) addView.getParent()).removeView(addView);
                    btnnewschool.setText("ADD NEW SCHOOL");
                }
            });

            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Status);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            statusnew.setAdapter(dataAdapter1);
            statusnew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int index = statusnew.getSelectedItemPosition();
                    String ReasonSpinnerNew = statusnew.getSelectedItem().toString();
                    ReasonSpinnerSchool = ReasonSpinnerNew;
                    if (ReasonSpinnerSchool.equals(" ")) {
                        ReasonSpinnerSchool = "Select";
                    }
                    if (index == 9) {
                        lbl_specificstatusNewSchool.setVisibility(View.VISIBLE);
                        statusnewschool.setVisibility(View.VISIBLE);
                    } else {
                        lbl_specificstatusNewSchool.setVisibility(View.GONE);
                        statusnewschool.setVisibility(View.GONE);
                    }
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });


            ArrayAdapter<String> PersonmetAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, PersonmetSpinner);
            PersonmetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            personmetnew.setAdapter(PersonmetAdapter);
            personmetnew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int index = personmetnew.getSelectedItemPosition();
                    String PersonmetSpinningNew = personmetnew.getSelectedItem().toString();
                    PersonmetSpinningSchool = PersonmetSpinningNew;
                    if (PersonmetSpinningSchool.equals(" ")) {
                        PersonmetSpinningSchool = "Select";
                    }
                    if (index == 5) {
                        lbl_specificPersionmetNewSchool.setVisibility(View.VISIBLE);
                        personmetnewschool.setVisibility(View.VISIBLE);
                    } else {
                        lbl_specificPersionmetNewSchool.setVisibility(View.GONE);
                        personmetnewschool.setVisibility(View.GONE);
                    }
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            linearnewschool.addView(addView);
            linearlayoutlist.add(linearnewschool);
            btnnewschool.setText("SAVE & ADD NEW SCHOOL");
            Log.d("Liner_LayoutSize", String.valueOf(linearlayoutlist.size()));
        } else {
            String schoolnamenew1 = schoolnamenew.getText().toString();
            String area = areanewschool.getText().toString();
            String personname = personnamenewschool.getText().toString();
            String contactnumber = contactnamenewschool.getText().toString();
            String Remaek = Remarknewschool.getText().toString();
            String Dateofview = Datelateupdate.getText().toString();
            String DistrictnewSchool = Districtnewschool.getText().toString();
            String Lateupdate = Newschooldate.getText().toString();
            String ReasonSpinnerNewSchool = ReasonSpinnerSchool;
            String PersonmetSpinningNewSchool = PersonmetSpinningSchool;
            if (schoolnamenew1.isEmpty() || area.isEmpty() || DistrictnewSchool.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter all the mandatory fields", Toast.LENGTH_SHORT).show();
            } else {
                data1 = new Today_Visit_NewSchool(schoolnamenew1, area, personname, contactnumber, Remaek, Lateupdate, DistrictnewSchool, Dateofview, ReasonSpinnerNewSchool, PersonmetSpinningNewSchool, Latitude, Longitude);
                Today_Visit_NewSchool.add(data1);
                btnnewschool.setText("ADD NEW SCHOOL");
            }
        }
    }

    private void todayvisitretrofit(String userId, String latitude, String longitude) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Voicesnapdemointerface apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);
        Today_Visit_NewSchool data = new Today_Visit_NewSchool(schoolname, schoolarea, princiname, princino, remarks, Date, district, Date_oflate_update, ReasonSpinner, PersonmetSpinning, latitude, longitude);

        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("login_id", userId);
        jsonRequest.addProperty("school_name", data.getSchoolname());
        jsonRequest.addProperty("area", data.getArea());
        jsonRequest.addProperty("district", data.getDistrict());
        jsonRequest.addProperty("person_name", data.getPersonname());
        jsonRequest.addProperty("contact_number", data.getContactnumber());
        jsonRequest.addProperty("remarks", data.getRemark());
        jsonRequest.addProperty("reason_for_delay_entry", data.getReasonOflateEntry());
        jsonRequest.addProperty("reason_of_visit", data.getReasonOfvisit());
        jsonRequest.addProperty("person_met", data.getPersonmet());
        jsonRequest.addProperty("date_of_visit", data.getDateofVisit());
        jsonRequest.addProperty("latitude", latitude);
        jsonRequest.addProperty("longitude", longitude);

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonRequest);

        Call<JsonArray> call = apiService.updateDailyVisitWithLocation(jsonArray);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JsonArray resArray = response.body();
                        JsonObject resObj = resArray.get(0).getAsJsonObject();
                        String status = resObj.get("result").getAsString();
                        String message = resObj.get("resultMessage").getAsString();

                        alert(message);

                        if ("1".equals(status)) {
                            clearAllFields();
                        }

                    } catch (Exception e) {
                        Log.e("Response Exception", e.getMessage());
                    }
                } else {
                    alert("Server Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                alert("Server Connection Failed");
            }
        });

    }

    private void sendTripDetails(String userId, String type, double latitude, double longitude) {
        TripRequest request = new TripRequest(userId, type, latitude, longitude);
        Voicesnapdemointerface apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(type.equals("start") ? "Starting trip..." : "Ending trip...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<TripResponse> call = apiService.ManageTripDetails(request);

        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    TripResponse tripResponse = response.body();
                    Log.d("API_RESPONSE", "Status=" + tripResponse.getStatus() + ", Msg=" + tripResponse.getMessage());
                    Toast.makeText(VisitReport.this, tripResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e("API_ERROR_BODY", errorBody);
                        Toast.makeText(VisitReport.this, "Server Error: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("API_CALL", "Failure", t);
                Toast.makeText(VisitReport.this, "API Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void alert(String message) {
        new AlertDialog.Builder(this)
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
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
                Today_Visit_NewSchool.clear();
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

}