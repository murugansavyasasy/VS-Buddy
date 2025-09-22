package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import com.google.gson.annotations.SerializedName;

public class TripRequest {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("type")
    private String type;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public TripRequest(String userId, String type, double latitude, double longitude) {
        this.userId = userId;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}


