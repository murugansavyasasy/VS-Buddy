package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import com.google.gson.annotations.SerializedName;

public class TripResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
