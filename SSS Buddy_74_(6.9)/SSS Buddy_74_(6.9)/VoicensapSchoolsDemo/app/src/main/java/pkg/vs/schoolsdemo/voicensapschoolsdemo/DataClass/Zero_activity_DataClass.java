package pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass;

import java.io.Serializable;

public class Zero_activity_DataClass implements Serializable {
    String SchoolId;
    String SchoolName;
    String salesname;
    String status;
    String applastuse;
    String weblastuse;

    public Zero_activity_DataClass()
    {

    }


    public String getSchoolId() {
        return SchoolId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public String getSalesname() {
        return salesname;
    }

    public String getStatus() {
        return status;
    }

    public String getApplastuse() {
        return applastuse;
    }

    public String getWeblastuse() {
        return weblastuse;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public void setSalesname(String salesname) {
        this.salesname = salesname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApplastuse(String applastuse) {
        this.applastuse = applastuse;
    }

    public void setWeblastuse(String weblastuse) {
        this.weblastuse = weblastuse;
    }
}
