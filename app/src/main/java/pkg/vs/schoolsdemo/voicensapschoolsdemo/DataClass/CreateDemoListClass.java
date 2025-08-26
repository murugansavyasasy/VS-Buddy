package pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass;

import java.io.Serializable;

/**
 * Created by devi on 12/8/2016.
 */

public class CreateDemoListClass implements Serializable {
    String strDemoId;
    String strDemoSchoolName;
    String strDemoPrincipalNumber;
    String strDemoEmailId;
    String strDemoParentNumber;
    String strDemoParentNumbercount;

    public CreateDemoListClass()
    {

    }

    public String getStrDemoId() {
        return strDemoId;
    }

    public String getStrDemoSchoolName() {
        return strDemoSchoolName;
    }

    public String getStrDemoPrincipalNumber() {
        return strDemoPrincipalNumber;
    }

    public String getStrDemoEmailId() {
        return strDemoEmailId;
    }

    public String getStrDemoParentNumber() {
        return  strDemoParentNumber;
    }

    public String getStrDemoParentNumbercount() {
        return  strDemoParentNumbercount;
    }

    public void setStrDemoId(String strDemoId) {
        this.strDemoId= strDemoId;
    }

    public void setStrDemoSchoolName(String strDemoSchoolName) {
        this.strDemoSchoolName= strDemoSchoolName;
    }

    public void setStrDemoPrincipalNumber(String strDemoPrincipalNumber) {
        this.strDemoPrincipalNumber = strDemoPrincipalNumber;
    }

    public void setStrDemoEmailId(String strDemoEmailId) {
        this.strDemoEmailId = strDemoEmailId;
    }

    public void setStrDemoParentNumber(String strParentNumber) {
        this.strDemoParentNumber = strParentNumber;
    }

    public void setStrDemoParentNumbercount(String strParentNumbercount) {
        this.strDemoParentNumbercount = strParentNumbercount;
    }
}
