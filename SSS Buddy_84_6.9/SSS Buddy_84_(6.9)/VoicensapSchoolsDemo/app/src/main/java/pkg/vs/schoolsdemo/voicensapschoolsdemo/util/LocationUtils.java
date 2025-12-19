package pkg.vs.schoolsdemo.voicensapschoolsdemo.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

public class LocationUtils {

    public static String getAddressFromLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressLine = address.getAddressLine(0); // Complete address
                return addressLine;
            } else {
                return "Address not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting address";
        }
    }
}
