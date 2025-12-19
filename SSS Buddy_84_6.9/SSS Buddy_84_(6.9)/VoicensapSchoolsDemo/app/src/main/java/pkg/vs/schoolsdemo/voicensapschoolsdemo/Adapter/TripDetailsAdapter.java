package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VisitDetail;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.DistanceCalculator;

public class TripDetailsAdapter extends RecyclerView.Adapter<TripDetailsAdapter.ViewHolder> {

    private static final String TAG = "TripDetailsAdapter";
    private static final boolean AUTO_FIX_SUSPICIOUS_COORDS = true;
    private static final double SAME_LAT_LNG_EPS = 1e-6;
    private static final double LONGITUDE_JUMP_DEGREES = 5.0;

    private Context context;
    private List<TripDetails> trips = new ArrayList<>();
    private int expandedPosition = -1;

    public TripDetailsAdapter(Context context, List<TripDetails> trips) {
        this.context = context;
        setData(trips);
    }

    public void updateData(List<TripDetails> trips) {
        setData(trips);
    }

    private void setData(List<TripDetails> trips) {
        this.trips.clear();
        if (trips != null) {
            this.trips.addAll(trips);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TripDetails trip = trips.get(position);

        holder.tvUsername.setText(trip.getUsername());
        holder.tvStartTime.setText(trip.getStart_time() != null && !trip.getStart_time().isEmpty() ? trip.getStart_time() : "--"
        );

//        Double startLat = trip.getStart_latitude() != null && !trip.getStart_latitude().isEmpty() ? parseCoordinate(trip.getStart_latitude()) : null;
//        Double startLng = trip.getStart_longitude() != null && !trip.getStart_longitude().isEmpty() ? parseCoordinate(trip.getStart_longitude()) : null;
//        Double endLat = trip.getEnd_latitude() != null && !trip.getEnd_latitude().isEmpty() ? parseCoordinate(trip.getEnd_latitude()) : null;
//        Double endLng = trip.getEnd_longitude() != null && !trip.getEnd_longitude().isEmpty() ? parseCoordinate(trip.getEnd_longitude()) : null;
//

        if(trip.getStart_latitude() != null && trip.getStart_longitude() !=null) {
            Double starting_lat = DistanceCalculator.parseCoordinate(trip.getStart_latitude());
            Double starting_lang = DistanceCalculator.parseCoordinate(trip.getStart_longitude());

            List<double[]> points = new ArrayList<>();
            // Home
            points.add(new double[]{starting_lat, starting_lang});

            List<VisitDetail> visitDetails = trip.getVisit_details();
            for (VisitDetail detail : visitDetails) {
                if (detail.getSchool_latitude() != null && detail.getSchool_longitude() != null) {
                    Double visit_lat = DistanceCalculator.parseCoordinate(detail.getSchool_latitude());
                    Double visit_lang = DistanceCalculator.parseCoordinate(detail.getSchool_longitude());
                    points.add(new double[]{visit_lat, visit_lang});
                }
            }

            if(trip.getEnd_latitude() != null && trip.getStart_longitude() != null) {
               Double ending_lat = DistanceCalculator.parseCoordinate(trip.getEnd_latitude());
               Double ending_lang = DistanceCalculator.parseCoordinate(trip.getEnd_longitude());
                points.add(new double[]{ending_lat, ending_lang});
            }

            double approxDistance = DistanceCalculator.totalApproximateRoadDistance(points);
            System.out.println("Approximate Road Distance: " + approxDistance + " km");
            holder.tvDistance.setText(
                    String.format(Locale.getDefault(), "Total Distance: %.2f km", approxDistance)
            );
        }
        else {
            holder.tvDistance.setText(
                    String.format(Locale.getDefault(), "Total Distance: %.2f km", "0")
            );
        }



//        List<Double> segments = new ArrayList<>();
//        if (startLat != null && startLng != null) {
//            segments = calculateSegmentDistances(
//                    startLat,
//                    startLng,
//                    trip.getVisit_details(),
//                    endLat,
//                    endLng
//            );
//        }

//        if (segments.isEmpty()) {
//            holder.tvDistance.setText("Total Distance: 0");
//        } else {
//            double totalMeters = 0.0;
//            for (int i = 0; i < segments.size(); i++) {
//                totalMeters += segments.get(i);
//                Log.d(TAG, "Leg " + (i + 1) + ": " + (segments.get(i) / 1000.0) + " km");
//            }
//            double totalKm = totalMeters / 1000.0;
//
//            holder.tvDistance.setText(
//                    String.format(Locale.getDefault(), "Total Distance: %.2f km", totalKm)
//            );
//        }

        if (trip.getVisit_details() != null && !trip.getVisit_details().isEmpty()) {
            holder.rvVisits.setLayoutManager(new LinearLayoutManager(context));
            holder.rvVisits.setAdapter(new VisitDetailAdapter(context, trip.getVisit_details()));
        }

        boolean isExpanded = position == expandedPosition;
        holder.rvVisits.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.tvUsername.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                isExpanded ? R.drawable.arrow_up_round : R.drawable.arrow_down_round,
                0
        );

        View.OnClickListener toggleListener = v -> {
            expandedPosition = (expandedPosition == position) ? -1 : position;
            notifyDataSetChanged();
        };

        holder.tvUsername.setOnClickListener(toggleListener);
        holder.tvStartTime.setOnClickListener(toggleListener);
        holder.tvDistance.setOnClickListener(toggleListener);

        if (position == getItemCount() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvStartTime, tvDistance;
        RecyclerView rvVisits;

        View divider;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            rvVisits = itemView.findViewById(R.id.rv_visits);
            divider = itemView.findViewById(R.id.divider);

        }
    }


    private double parseCoordinate(String coord) {
        if (coord == null) return Double.NaN;
        String s = coord.trim();
        if (s.isEmpty() || "null".equalsIgnoreCase(s)) return Double.NaN;

        try {
            boolean isSouth = s.toUpperCase().contains("S");
            boolean isWest  = s.toUpperCase().contains("W");

            s = s.replace("°", " ").replace("º", " ").replace(",", " ").trim();
            s = s.replaceAll("[^0-9+\\-\\.]", "");

            if (s.isEmpty()) return Double.NaN;

            double value = Double.parseDouble(s);
            if (isSouth || isWest) value = -value;
            return value;
        } catch (Exception e) {
            Log.d(TAG, "parseCoordinate failed for '" + coord + "': " + e.getMessage());
            return Double.NaN;
        }
    }

    private boolean isValidLat(double lat) {
        return !Double.isNaN(lat) && lat >= -90 && lat <= 90;
    }

    private boolean isValidLng(double lng) {
        return !Double.isNaN(lng) && lng >= -180 && lng <= 180;
    }

    private boolean isValidCoordinate(double lat, double lng) {
        return isValidLat(lat) && isValidLng(lng);
    }

    private double[] sanitizeSchoolPoint(double prevLat, double prevLng, double endLng, double schoolLat, double schoolLng) {
        if (!isValidLat(schoolLat)) return new double[]{Double.NaN, Double.NaN};
        if (!isValidLng(schoolLng)) {
            if (isValidLng(prevLng)) return new double[]{schoolLat, prevLng};
            if (isValidLng(endLng)) return new double[]{schoolLat, endLng};
            return new double[]{Double.NaN, Double.NaN};
        }
        if (!AUTO_FIX_SUSPICIOUS_COORDS) {
            return isValidCoordinate(schoolLat, schoolLng) ? new double[]{schoolLat, schoolLng} : new double[]{Double.NaN, Double.NaN};
        }
        boolean latEqualsLng = Math.abs(schoolLat - schoolLng) <= SAME_LAT_LNG_EPS;
        boolean lonJumpPrev = isValidLng(prevLng) && Math.abs(schoolLng - prevLng) > LONGITUDE_JUMP_DEGREES;
        boolean lonJumpEnd  = isValidLng(endLng)  && Math.abs(schoolLng - endLng)  > LONGITUDE_JUMP_DEGREES;

        if (latEqualsLng || (lonJumpPrev && lonJumpEnd)) {
            if (isValidLng(prevLng)) return new double[]{schoolLat, prevLng};
            else if (isValidLng(endLng)) return new double[]{schoolLat, endLng};
            else return new double[]{Double.NaN, Double.NaN};
        }
        return new double[]{schoolLat, schoolLng};
    }

    private double distanceBetweenMeters(double fromLat, double fromLng, double toLat, double toLng) {
        if (!isValidCoordinate(fromLat, fromLng) || !isValidCoordinate(toLat, toLng)) return 0.0;
        float[] r = new float[1];
        Location.distanceBetween(fromLat, fromLng, toLat, toLng, r);
        return (double) r[0];
    }

    private List<Double> calculateSegmentDistances(
            double startLat, double startLng,
            List<VisitDetail> visits,
            Double endLat, Double endLng
    ) {
        List<Double> segmentDistances = new ArrayList<>();
        List<double[]> points = new ArrayList<>();

        if (!isValidCoordinate(startLat, startLng)) {
            return segmentDistances;
        }

        points.add(new double[]{startLat, startLng});

        double prevLat = startLat;
        double prevLng = startLng;
        if (visits != null) {
            for (VisitDetail v : visits) {
                double rawLat = parseCoordinate(v.getSchool_latitude());
                double rawLng = parseCoordinate(v.getSchool_longitude());
                double[] fixed = sanitizeSchoolPoint(prevLat, prevLng,
                        (endLng != null ? endLng : Double.NaN),
                        rawLat, rawLng);
                double lat = fixed[0], lng = fixed[1];
                if (isValidCoordinate(lat, lng)) {
                    points.add(new double[]{lat, lng});
                    prevLat = lat;
                    prevLng = lng;
                }
            }
        }
        if (endLat != null && endLng != null && isValidCoordinate(endLat, endLng)) {
            points.add(new double[]{endLat, endLng});
        }

        for (int i = 0; i < points.size() - 1; i++) {
            double[] a = points.get(i);
            double[] b = points.get(i + 1);
            double segMeters = distanceBetweenMeters(a[0], a[1], b[0], b[1]);
            segmentDistances.add(segMeters);
            Log.d(TAG, String.format(Locale.getDefault(),
                    "Segment %d -> %d distance = %.2f m", i, i + 1, segMeters));
        }

        return segmentDistances;
    }


}
