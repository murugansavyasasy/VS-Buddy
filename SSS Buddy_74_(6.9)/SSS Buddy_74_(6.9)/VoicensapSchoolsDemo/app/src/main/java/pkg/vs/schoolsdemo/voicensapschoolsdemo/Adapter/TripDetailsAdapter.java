package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TripDetails;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.VisitDetail;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class TripDetailsAdapter extends BaseAdapter {

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

    @Override public int getCount() { return trips.size(); }
    @Override public Object getItem(int position) { return trips.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trip_detail, parent, false);
        }

        TripDetails trip = trips.get(position);

        TextView tvUsername = convertView.findViewById(R.id.tv_username);
        TextView tvStartTime = convertView.findViewById(R.id.tv_start_time);
        TextView tvDistance = convertView.findViewById(R.id.tv_distance);
        RecyclerView rvVisits = convertView.findViewById(R.id.rv_visits);

        tvUsername.setText(trip.getUsername());
        tvStartTime.setText((trip.getStart_time() != null ? trip.getStart_time() : "--"));

        // Distance calc remains same...
        double totalMeters = calculateTotalTripDistanceMeters(
                parseCoordinate(trip.getStart_latitude()),
                parseCoordinate(trip.getStart_longitude()),
                trip.getVisit_details(),
                parseCoordinate(trip.getEnd_latitude()),
                parseCoordinate(trip.getEnd_longitude())
        );
        double totalKm = totalMeters / 1000.0;

        if (totalMeters <= 0) {
            tvDistance.setText("Total Distance: N/A");
        } else {
            tvDistance.setText(String.format(Locale.getDefault(), "Total Distance: %.2f km", totalKm));
        }

        if (trip.getVisit_details() != null && !trip.getVisit_details().isEmpty()) {
            rvVisits.setLayoutManager(new LinearLayoutManager(context));
            rvVisits.setAdapter(new VisitDetailAdapter(context, trip.getVisit_details()));
        }

        boolean isExpanded = position == expandedPosition;
        rvVisits.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // 🔹 Toggle arrow based on expand state
        tvUsername.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                isExpanded ? R.drawable.arrow_up_round : R.drawable.arrow_down_round,
                0
        );

        // 🔹 One common click listener for expand/collapse
        View.OnClickListener toggleListener = v -> {
            expandedPosition = (expandedPosition == position) ? -1 : position;
            notifyDataSetChanged();
        };

        tvUsername.setOnClickListener(toggleListener);
        tvStartTime.setOnClickListener(toggleListener);
        tvDistance.setOnClickListener(toggleListener);

        return convertView;
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

            if (isSouth || isWest) {
                value = -value;
            }

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
            if (isValidLng(prevLng)) {
                return new double[]{schoolLat, prevLng};
            } else if (isValidLng(endLng)) {
                return new double[]{schoolLat, endLng};
            } else {
                return new double[]{Double.NaN, Double.NaN};
            }
        }

        return new double[]{schoolLat, schoolLng};
    }

    private double distanceBetweenMeters(double fromLat, double fromLng, double toLat, double toLng) {
        if (!isValidCoordinate(fromLat, fromLng) || !isValidCoordinate(toLat, toLng)) return 0.0;
        float[] r = new float[1];
        Location.distanceBetween(fromLat, fromLng, toLat, toLng, r);
        return (double) r[0];
    }
    private double calculateTotalTripDistanceMeters(
            double startLat, double startLng,
            List<VisitDetail> visits,
            double endLat, double endLng
    ) {
        if (!isValidCoordinate(startLat, startLng) || !isValidCoordinate(endLat, endLng)) {
            Log.d(TAG, "Invalid start or end coordinates. start: " + startLat + "," + startLng + " end: " + endLat + "," + endLng);
            return -1.0;
        }

        List<double[]> points = new ArrayList<>();
        points.add(new double[]{startLat, startLng});

        double prevLat = startLat;
        double prevLng = startLng;

        if (visits != null) {
            for (VisitDetail v : visits) {
                double rawLat = parseCoordinate(v.getSchool_latitude());
                double rawLng = parseCoordinate(v.getSchool_longitude());

                double[] fixed = sanitizeSchoolPoint(prevLat, prevLng, endLng, rawLat, rawLng);
                double lat = fixed[0], lng = fixed[1];

                if (isValidCoordinate(lat, lng)) {
                    points.add(new double[]{lat, lng});
                    prevLat = lat;
                    prevLng = lng;
                } else {
                    Log.d(TAG, "Skipping invalid school coordinate: raw=(" + rawLat + "," + rawLng + ")");
                }
            }
        }

        points.add(new double[]{endLat, endLng});

        double totalMeters = 0.0;
        for (int i = 0; i < points.size() - 1; i++) {
            double[] a = points.get(i);
            double[] b = points.get(i + 1);
            double segMeters = distanceBetweenMeters(a[0], a[1], b[0], b[1]);
            Log.d(TAG, String.format(Locale.getDefault(),
                    "Segment %d -> %d: from(%.6f,%.6f) to(%.6f,%.6f) = %.2f m",
                    i, i+1, a[0], a[1], b[0], b[1], segMeters));
            totalMeters += segMeters;
        }

        Log.d(TAG, String.format(Locale.getDefault(), "Trip total meters = %.2f m (%.3f km)", totalMeters, totalMeters / 1000.0));
        return totalMeters;
    }
}
