package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.util.List;

public class TripDetails {
    private int status;
    private String message;
    private String username;
    private String start_time;
    private String start_latitude;
    private String start_longitude;
    private String end_time;
    private String end_latitude;
    private String end_longitude;
    private int is_closed;
    private List<VisitDetail> visit_details;


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public int getIs_closed() {
        return is_closed;
    }

    public List<VisitDetail> getVisit_details() {
        return visit_details;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public void setIs_closed(int is_closed) {
        this.is_closed = is_closed;
    }

    public void setVisit_details(List<VisitDetail> visit_details) {
        this.visit_details = visit_details;
    }
}
