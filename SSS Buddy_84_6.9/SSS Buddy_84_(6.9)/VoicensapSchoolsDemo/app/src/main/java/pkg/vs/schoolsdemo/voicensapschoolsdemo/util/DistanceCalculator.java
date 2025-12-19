package pkg.vs.schoolsdemo.voicensapschoolsdemo.util;

import java.util.List;

public class DistanceCalculator {

    // Haversine formula: distance in meters between two points
    public static double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371e3; // Earth radius in meters
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in meters
    }

    // Calculate total approximate road distance using multiplier
    public static double totalApproximateRoadDistance(List<double[]> points) {
        // points: List of double[]{latitude, longitude} in order of visit
        if (points == null || points.size() < 2) return 0.0;

        double totalDistance = 0.0;
        for (int i = 0; i < points.size() - 1; i++) {
            double[] p1 = points.get(i);
            double[] p2 = points.get(i + 1);
            totalDistance += distanceBetween(p1[0], p1[1], p2[0], p2[1]);
        }

        // Convert meters to kilometers
        double distanceKm = totalDistance / 1000.0;

        // Apply multiplier to approximate road distance
        double roadMultiplier = 1.3; // Adjust factor based on road layout
        return distanceKm * roadMultiplier;
    }

    public static double parseCoordinate(String coord) {
        if (coord == null || coord.isEmpty()) {
            throw new IllegalArgumentException("Coordinate is null or empty");
        }

        coord = coord.trim();

        // Check if coordinate contains degree symbol or direction
        if (coord.contains("°") || coord.toUpperCase().contains("N") ||
                coord.toUpperCase().contains("S") || coord.toUpperCase().contains("E") ||
                coord.toUpperCase().contains("W")) {

            // Remove degree symbol
            coord = coord.replace("°", "").trim();
            // Split by space (number + direction)
            String[] parts = coord.split("\\s+");
            double value = Double.parseDouble(parts[0]);

            if (parts.length > 1) {
                String dir = parts[1].toUpperCase();
                if (dir.equals("S") || dir.equals("W")) {
                    value = -value;
                }
            }

            return value;
        } else {
            // Plain number
            return Double.parseDouble(coord);
        }
    }
}
