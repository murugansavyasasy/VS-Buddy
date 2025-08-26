package pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass;

import java.io.Serializable;

/**
 * Created by devi on 12/30/2016.
 */

public class MyschoolListclass implements Serializable {

    String strSchoolId;
    String strSchoolNameList;
    String strSchooladdr;
    String strSchoolcity;
    String strUserName;
    String strPassword;
    String strSeniormgt;
    String strDidschool;
    String strFromdate;
    String strTodate;
    String strschoolStatus;
    String strstatus;
    String strstudentcount;
    String strstaffcount;
    String CallsCount;
    String SmsCount;

    String ContactPerson1;
    String ContactNumber1;

    String Salespersionname;

    String WebUsername;

    public String getContactPerson1() {
        return ContactPerson1;
    }

    public void setContactPerson1(String contactPerson1) {
        ContactPerson1 = contactPerson1;
    }

    public String getWebUsername() {
        return WebUsername;
    }

    public void setWebUsername(String Webusername) {
        WebUsername = Webusername;
    }

    public String getSalespersionname() {
        return Salespersionname;
    }

    public void setSalespersionname(String salsePname) {
        Salespersionname = salsePname;
    }

    public String getContactNumber1() {
        return ContactNumber1;
    }

    public void setContactNumber1(String contactNumber1) {
        ContactNumber1 = contactNumber1;
    }

    public String getContactPerson2() {
        return ContactPerson2;
    }

    public void setContactPerson2(String contactPerson2) {
        ContactPerson2 = contactPerson2;
    }

    public String getContactNumber2() {
        return ContactNumber2;
    }

    public void setContactNumber2(String contactNumber2) {
        ContactNumber2 = contactNumber2;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    String ContactPerson2;
    String ContactNumber2;
    String ContactEmail;



    public String getStrstatus() {
        return strstatus;
    }

    public void setStrstatus(String strstatus) {
        this.strstatus = strstatus;
    }

    public String getStrSchooladdr() {
        return strSchooladdr;
    }

    public void setStrSchooladdr(String strSchooladdr) {
        this.strSchooladdr = strSchooladdr;
    }

    public String getStrTodate() {
        return strTodate;
    }

    public void setStrTodate(String strTodate) {
        this.strTodate = strTodate;
    }


    public String getStrFromdate() {
        return strFromdate;
    }

    public void setStrFromdate(String strFromdate) {
        this.strFromdate = strFromdate;
    }

    public MyschoolListclass()
    {

    }

    public String getStrSchoolId() {
        return strSchoolId;
    }

    public String getStrSchoolNameList() {
        return strSchoolNameList;
    }

    public String getStrSchoolcity() {
        return strSchoolcity;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public String getStrDidschool() {
        return  strDidschool;
    }

    public String getStrschoolStatus() {
        return  strschoolStatus;
    }

    public String getStrSeniormgt() {
        return  strSeniormgt;
    }

    public String getStrstudentcount() {
        return  strstudentcount;
    }

    public String getStrstaffcount() {
        return  strstaffcount;
    }

    public String getCallsCount() {
        return  CallsCount;
    }
    public String getSmsCount() {
        return  SmsCount;
    }

    public void setStrSchoolId(String strSchoolId) {
        this.strSchoolId= strSchoolId;
    }

    public void setStrSchoolNameList(String strSchoolNameList) {
        this.strSchoolNameList= strSchoolNameList;
    }
    public void setStrSchoolcity(String strSchoolcity) {
        this.strSchoolcity= strSchoolcity;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    public void setStrDidschool(String strDidschool) {
        this.strDidschool = strDidschool;
    }

    public void setStrschoolStatus(String strschoolStatus) {
        this.strschoolStatus = strschoolStatus;
    }

    public void setStrSeniormgt(String strSeniormgt) {
        this.strSeniormgt = strSeniormgt;
    }

    public void setStrstudentcount(String strstudentcount) {
        this.strstudentcount = strstudentcount;
    }

    public void setStrstaffcount(String strstaffcount) {
        this.strstaffcount = strstaffcount;
    }

    public void setCallsCount(String callscount) {
        this.CallsCount = callscount;
    }

    public void setSmsCount(String smsCount) {
        this.SmsCount = smsCount;
    }
}
