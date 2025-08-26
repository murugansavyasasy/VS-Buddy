package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VisitDetail;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class TripDetailsAdapter extends BaseAdapter {
    private Context context;
    private TripDetails trip;
    private List<VisitDetail> visitList;

    public TripDetailsAdapter(Context context, TripDetails trip) {
        this.context = context;
        this.trip = trip;
        this.visitList = trip.getVisit_details();
    }

    @Override
    public int getCount() {
        return visitList.size();
    }

    @Override
    public Object getItem(int position) {
        return visitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trip_detail, parent, false);
        }

        VisitDetail visit = visitList.get(position);

        ((TextView) convertView.findViewById(R.id.tv_school_name)).setText("School: " + visit.getSchool_name());
        ((TextView) convertView.findViewById(R.id.tv_school_latitude)).setText("Lat: " + visit.getSchool_latitude());
        ((TextView) convertView.findViewById(R.id.tv_school_longitude)).setText("Long: " + visit.getSchool_longitude());
        ((TextView) convertView.findViewById(R.id.tv_person_name)).setText("Person: " + visit.getPerson_name());
        ((TextView) convertView.findViewById(R.id.tv_reason)).setText("Reason: " + visit.getReason_of_visit());
        ((TextView) convertView.findViewById(R.id.tv_remarks)).setText("Remarks: " + visit.getRemarks());

        return convertView;
    }
}
