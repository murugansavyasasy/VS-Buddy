package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.TripDetailsAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.ReportingMember;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private Spinner spinnerReport;
    private ListView lvReportList;
    private LinearLayout noDataLayout;

    private List<ReportingMember> reportingMembers = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private TripDetailsAdapter tripAdapter;
    private Voicesnapdemointerface apiService;
    private List<TripDetails> allTrips = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmenu);

        spinnerReport = findViewById(R.id.spinner_report_details);
        lvReportList = findViewById(R.id.lv_report_list);
        noDataLayout = findViewById(R.id.no_data_layout);

        lvReportList.setEmptyView(noDataLayout);

        apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);

        String userId = SharedPreference_class.getUserid(this);
        loadReportingMembers(userId);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Report Page");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void displayTripDetails(List<TripDetails> trips) {
        if (tripAdapter == null) {
            tripAdapter = new TripDetailsAdapter(this, trips);
            lvReportList.setAdapter(tripAdapter);
        } else {
            tripAdapter.updateData(trips);
        }
    }

    private void loadReportingMembers(String userId) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading members...", true);

        apiService.getReportingMembers(userId).enqueue(new Callback<List<ReportingMember>>() {
            @Override
            public void onResponse(Call<List<ReportingMember>> call, Response<List<ReportingMember>> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    reportingMembers = response.body();
                    List<String> names = new ArrayList<>();
                    names.add("All Members");
                    for (ReportingMember m : reportingMembers) {
                        names.add(m.getMembername());
                    }

                    spinnerAdapter = new ArrayAdapter<>(ReportActivity.this,
                            android.R.layout.simple_spinner_item, names);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerReport.setAdapter(spinnerAdapter);

                    loadTripDetails(userId, null);

                    spinnerReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = names.get(position);
                            if (selectedName.equals("All Members")) {
                                displayTripDetails(allTrips);
                            } else {
                                List<TripDetails> filteredTrips = new ArrayList<>();
                                for (TripDetails trip : allTrips) {
                                    if (trip.getUsername() != null &&
                                            trip.getUsername().equalsIgnoreCase(selectedName)) {
                                        filteredTrips.add(trip);
                                    }
                                }
                                displayTripDetails(filteredTrips);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) { }
                    });
                } else {
                    Toast.makeText(ReportActivity.this, "Failed to load members", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReportingMember>> call, Throwable t) {
                dialog.dismiss();
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(ReportActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTripDetails(String userId, String memberName) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading trips...", true);

        Call<List<TripDetails>> call = (memberName == null || memberName.isEmpty()) ?
                apiService.getOverallTripDetails(userId, "") :
                apiService.getOverallTripDetails(userId, memberName);

        call.enqueue(new Callback<List<TripDetails>>() {
            @Override
            public void onResponse(Call<List<TripDetails>> call, Response<List<TripDetails>> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    allTrips.clear();
                    allTrips.addAll(response.body());
                    displayTripDetails(allTrips);
                } else {
                    displayTripDetails(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<TripDetails>> call, Throwable t) {
                dialog.dismiss();
                Log.e("API_ERROR", t.getMessage(), t);
                displayTripDetails(new ArrayList<>());
                Toast.makeText(ReportActivity.this, "Error loading trips", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (spinnerAdapter != null && spinnerReport != null) {
            spinnerReport.setSelection(0);
        }
    }
}
