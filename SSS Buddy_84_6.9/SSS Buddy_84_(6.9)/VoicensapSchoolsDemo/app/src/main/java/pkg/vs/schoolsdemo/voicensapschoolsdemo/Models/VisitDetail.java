package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;


public class VisitDetail {
    private String school_latitude;
    private String school_longitude;
    private String school_name;
    private String person_name;
    private String reason_of_visit;
    private String remarks;
    private String visit_address;

    public String getSchool_latitude() {
        return school_latitude;
    }

    public String getSchool_longitude() {
        return school_longitude;
    }

    public String getSchool_name() {
        return school_name;
    }

    public String getPerson_name() {
        return person_name;
    }

    public String getReason_of_visit() {
        return reason_of_visit;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getVisit_address() {
        return visit_address;
    }


    public void setSchool_latitude(String school_latitude) {
        this.school_latitude = school_latitude;
    }

    public void setSchool_longitude(String school_longitude) {
        this.school_longitude = school_longitude;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public void setReason_of_visit(String reason_of_visit) {
        this.reason_of_visit = reason_of_visit;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setVisit_address(String address) {
        this.visit_address = address;
    }
}
