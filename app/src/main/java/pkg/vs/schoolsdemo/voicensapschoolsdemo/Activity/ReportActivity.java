package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.TripDetailsAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.ReportingMember;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VisitDetail;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private Spinner spinnerReport;
    private ListView lvReportList;
    private List<ReportingMember> reportingMembers = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private TripDetailsAdapter tripAdapter;
    private Voicesnapdemointerface apiService;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmenu);
        spinnerReport = findViewById(R.id.spinner_report_details);
        lvReportList = findViewById(R.id.lv_report_list);
        apiService = VimsClient.getClient().create(Voicesnapdemointerface.class);
        TripDetails allTrips = getDummyTripDetails(null);
        displayTripDetails(allTrips);

        String userId = SharedPreference_class.getUserid(ReportActivity.this);
        loadReportingMembers(userId);

        spinnerReport.setOnTouchListener((v, event) -> {
            loadDummyReportingMembers();
            return false;
        });
    }

    private void displayTripDetails(TripDetails trip) {
        tripAdapter = new TripDetailsAdapter(this, trip);
        lvReportList.setAdapter(tripAdapter);
    }

    private List<VisitDetail> getDummyVisitDetailsForMember(String memberName) {
        List<VisitDetail> list = new ArrayList<>();

        if (memberName == null || memberName.isEmpty()) {
            list.add(createVisit("Sunrise Public School", "Mr. Nik", "Inspection", "Everything was good.", "12.9611", "77.6387"));
            list.add(createVisit("Green Valley High", "Mrs. Chandru", "Follow-up", "Need more books.", "12.9352", "77.6245"));
            list.add(createVisit("City Central School", "Mr. BaluSaren", "Routine Visit", "Teachers well trained.", "12.9456", "77.6100"));
            list.add(createVisit("Sunset Academy", "Ms. Meera", "Library Check", "Library well maintained.", "12.9520", "77.6201"));
            list.add(createVisit("Riverside School", "Mr. Saran", "Staff Meeting", "Staff performance good.", "12.9600", "77.6300"));
            list.add(createVisit("Sunrise Public School", "Ms. Meera", "Library Check", "Library well maintained.", "12.9520", "77.6201"));
            list.add(createVisit("Sunset Academy", "Mr. Nik", "Inspection", "Everything was good.", "12.9611", "77.6387"));
            list.add(createVisit("Sunrise Public School", "Mrs. Chandru", "Follow-up", "Need more books.", "12.9352", "77.6245"));
            list.add(createVisit("Sunset Academy", "Mr. BaluSaren", "Routine Visit", "Teachers well trained.", "12.9456", "77.6100"));
            list.add(createVisit("Sunrise Public School", "Mr. Saran", "Staff Meeting", "Staff performance good.", "12.9600", "77.6300"));

        } else {
            switch (memberName) {
                case "Ms. Meera":
                    list.add(createVisit("Sunset Academy", "Ms. Meera", "Library Check", "Library well maintained.", "12.9520", "77.6201"));
                    list.add(createVisit("Sunrise Public School", "Ms. Meera", "Library Check", "Library well maintained.", "12.9520", "77.6201"));
                    break;
                case "Mr. Nik":
                    list.add(createVisit("Sunrise Public School", "Mr. Nik", "Inspection", "Everything was good.", "12.9611", "77.6387"));
                    list.add(createVisit("Sunset Academy", "Mr. Nik", "Inspection", "Everything was good.", "12.9611", "77.6387"));
                    break;
                case "Mrs. Chandru":
                    list.add(createVisit("Green Valley High", "Mrs. Chandru", "Follow-up", "Need more books.", "12.9352", "77.6245"));
                    list.add(createVisit("Sunrise Public School", "Mrs. Chandru", "Follow-up", "Need more books.", "12.9352", "77.6245"));
                    break;
                case "Mr. BaluSaren":
                    list.add(createVisit("City Central School", "Mr. BaluSaren", "Routine Visit", "Teachers well trained.", "12.9456", "77.6100"));
                    list.add(createVisit("Sunset Academy", "Mr. BaluSaren", "Routine Visit", "Teachers well trained.", "12.9456", "77.6100"));
                    break;
                case "Mr. Saran":
                    list.add(createVisit("Riverside School", "Mr. Saran", "Staff Meeting", "Staff performance good.", "12.9600", "77.6300"));
                    list.add(createVisit("Sunrise Public School", "Mr. Saran", "Staff Meeting", "Staff performance good.", "12.9600", "77.6300"));
                    break;
            }
        }
        return list;
    }

    private VisitDetail createVisit(String school, String person, String reason, String remarks, String lat, String lng) {
        VisitDetail v = new VisitDetail();
        v.setSchool_name(school);
        v.setPerson_name(person);
        v.setReason_of_visit(reason);
        v.setRemarks(remarks);
        v.setSchool_latitude(lat);
        v.setSchool_longitude(lng);
        return v;
    }

    private TripDetails getDummyTripDetails(String memberName) {
        TripDetails trip = new TripDetails();
        trip.setStatus(1);
        trip.setUsername(memberName == null ? "All" : memberName);
        trip.setVisit_details(getDummyVisitDetailsForMember(memberName));
        return trip;
    }

    private void loadDummyReportingMembers() {
        List<String> names = new ArrayList<>();
        names.add("Mr. Nik");
        names.add("Mrs. Chandru");
        names.add("Mr. BaluSaren");
        names.add("Ms. Meera");
        names.add("Mr. Saran");

        spinnerAdapter = new ArrayAdapter<>(ReportActivity.this,
                android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReport.setAdapter(spinnerAdapter);

        spinnerReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMember = (String) parent.getItemAtPosition(position);
                TripDetails memberTrip = getDummyTripDetails(selectedMember);
                displayTripDetails(memberTrip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void loadReportingMembers(String userId) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading...", true);

        apiService.getReportingMembers(userId).enqueue(new Callback<List<ReportingMember>>() {
            @Override
            public void onResponse(Call<List<ReportingMember>> call, Response<List<ReportingMember>> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    reportingMembers = response.body();
                    List<String> names = new ArrayList<>();
                    for (ReportingMember m : reportingMembers) {
                        names.add(m.getMembername());
                    }

                    spinnerAdapter = new ArrayAdapter<>(ReportActivity.this,
                            android.R.layout.simple_spinner_item, names);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerReport.setAdapter(spinnerAdapter);

                    spinnerReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = names.get(position);
                            TripDetails filteredTrip = getDummyTripDetails(selectedName);
                            displayTripDetails(filteredTrip);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
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
}
