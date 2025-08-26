package pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass;

/**
 * Created by devi on 5/2/2018.
 */

public class Seniormgtnumberclass {

    public String MemberName,AppPassword,MemberType,Designation,IVRPassword,mobileno;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getAppPassword() {
        return AppPassword;
    }

    public void setAppPassword(String appPassword) {
        AppPassword = appPassword;
    }

    public String getMemberType() {
        return MemberType;
    }

    public void setMemberType(String memberType) {
        MemberType = memberType;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getIVRPassword() {
        return IVRPassword;
    }

    public void setIVRPassword(String IVRPassword) {
        this.IVRPassword = IVRPassword;
    }
}
