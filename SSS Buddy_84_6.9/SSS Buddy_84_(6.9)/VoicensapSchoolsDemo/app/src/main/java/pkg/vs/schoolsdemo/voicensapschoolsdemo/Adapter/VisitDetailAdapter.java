package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VisitDetail;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.DistanceCalculator;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.LocationUtils;

public class VisitDetailAdapter extends RecyclerView.Adapter<VisitDetailAdapter.ViewHolder> {

    private Context context;
    private List<VisitDetail> visits;

    public VisitDetailAdapter(Context context, List<VisitDetail> visits) {
        this.context = context;
        this.visits = visits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_visit_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VisitDetail visit = visits.get(position);

        setColoredText(holder.tvSchoolName, "School Name: ", visit.getSchool_name());

        setColoredText(holder.tvPersonName, "Person Name: ", visit.getPerson_name());

        setColoredText(holder.tvReason, "Reason Of Visit: ", visit.getReason_of_visit());

        setColoredText(holder.tvRemarks, "Remarks: ", visit.getRemarks());

        String address = "-";
        if(visit.getSchool_latitude() != null && visit.getSchool_longitude() != null) {
            Double lat = DistanceCalculator.parseCoordinate(visit.getSchool_latitude());
            Double lang = DistanceCalculator.parseCoordinate(visit.getSchool_longitude() );
            address = LocationUtils.getAddressFromLatLng(context, lat, lang);

        }
        setColoredText(holder.tvAddress, "Address: ", address);
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

    private void setColoredText(TextView textView, String header, String value) {
        if (value == null) value = "";
        SpannableString spannable = new SpannableString(header + value);
        spannable.setSpan(
                new ForegroundColorSpan(Color.GRAY),
                0,
                header.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                new ForegroundColorSpan(Color.BLACK),
                header.length(),
                header.length() + value.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        textView.setText(spannable);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchoolName, tvPersonName, tvReason, tvRemarks,tvAddress;

        ViewHolder(View itemView) {
            super(itemView);
            tvSchoolName = itemView.findViewById(R.id.tv_school_name);
            tvPersonName = itemView.findViewById(R.id.tv_person_name);
            tvReason = itemView.findViewById(R.id.tv_reason);
            tvRemarks = itemView.findViewById(R.id.tv_remarks);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
