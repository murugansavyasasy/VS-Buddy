package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

public class Today_Visit_NewSchool {

    String Schoolname;
    String Area;
    String Personname;
    String Contactnumber;
    String Remark;
    String DateofVisit;
    String District;
    String ReasonOflateEntry;
    String ReasonOfvisit;
    String Personmet;

    String Latitude;
    String Longitude;

    public String getSchoolname() {
        return Schoolname;
    }

    public void setSchoolname(String Schoolname) {
        this.Schoolname = Schoolname;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    public String getPersonname() {
        return Personname;
    }

    public void setPersonname(String Presonname) {
        this.Personname = Presonname;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getContactnumber() {
        return Contactnumber;
    }

    public void setContactnumber(String Contactnumber) {
        this.Contactnumber = Contactnumber;
    }

    public String getDateofVisit() {
        return DateofVisit;
    }

    public void setDateofVisit(String DateofVisit) {
        this.DateofVisit = DateofVisit;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public String getReasonOflateEntry() {
        return ReasonOflateEntry;
    }

    public void setReasonOflateEntry(String ReasonOflateEntry) {
        this.ReasonOflateEntry = ReasonOflateEntry;
    }


    public String getReasonOfvisit() {
        return ReasonOfvisit;
    }

    public void setReasonOfvisit(String ReasonOfvisit) {
        this.ReasonOfvisit = ReasonOfvisit;
    }

    public String getPersonmet() {
        return Personmet;
    }

    public void setPersonmet(String Personmet) {
        this.Personmet = Personmet;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        this.Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        this.Longitude = longitude;
    }

    public Today_Visit_NewSchool(String Schoolname, String Area, String Personname, String Contactnumber,
                                 String Remark, String date_ofVisit, String District, String ReasonOFdELAY_Entry,
                                 String Reasonof_Visit, String Persomet, String Latitude, String Longitude) {
        this.Schoolname = Schoolname;
        this.Area = Area;
        this.Personname = Personname;
        this.Contactnumber = Contactnumber;
        this.Remark = Remark;
        this.DateofVisit = date_ofVisit;
        this.District = District;
        this.ReasonOflateEntry = ReasonOFdELAY_Entry;
        this.ReasonOfvisit = Reasonof_Visit;
        this.Personmet = Persomet;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
}
