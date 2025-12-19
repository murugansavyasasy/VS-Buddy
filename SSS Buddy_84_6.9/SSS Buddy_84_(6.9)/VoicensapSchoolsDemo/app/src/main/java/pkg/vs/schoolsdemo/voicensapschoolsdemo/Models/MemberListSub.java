package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

public class MemberListSub {
    String CustomerID;
    String CustomerName;
    String OtherName;
    String POID;
    String PoNumber;
    String SchoolPoID;
    String GoLiveDate;
    String ValidFrom;
    String ValidTo;
    String RemainingDays;



    public MemberListSub(String customerId, String customername, String othername, String po_id, String po_Number,
                         String schoolPo_Id, String livedate, String validFrom,String validTo,String remainingdays){
        this.CustomerID=customerId;
         this.CustomerName=customername;
         this.OtherName=othername;
         this.POID=po_id;
        this.PoNumber=po_Number;
         this.SchoolPoID=schoolPo_Id;
         this.GoLiveDate=livedate;
         this.ValidFrom=validFrom;
         this.ValidTo=validTo;
         this.RemainingDays=remainingdays;


    }
    public String getCustomerID() {
        return CustomerID;

    }

    public void setCustomerID(String nameValue1) {
        this.CustomerID = nameValue1;

    }
    public String getCustomerName() {
        return CustomerName;

    }

    public void setCustomerName(String idValue1) {
        this.CustomerName = idValue1;

    }
    public String getOtherName() {
        return OtherName;

    }

    public void setOtherName(String date) {
        this.OtherName = date;

    }

    public String getPOID() {
        return POID;

    }

    public void setPOID(String date) {
        this.POID = date;

    }

    public String getPoNumber() {
        return PoNumber;

    }

    public void setPoNumber(String date) {
        this.PoNumber = date;

    }

    public String getSchoolPoID() {
        return SchoolPoID;

    }

    public void setSchoolPoID(String date) {
        this.SchoolPoID = date;

    }

    public String getGoLiveDate() {
        return GoLiveDate;

    }

    public void setGoLiveDate(String date) {
        this.GoLiveDate = date;

    }

    public String getValidFrom() {
        return ValidFrom;

    }

    public void setValidFrom(String date) {
        this.ValidFrom = date;

    }

    public String getValidTo() {
        return ValidTo;

    }

    public void setValidTo(String date) {
        this.ValidTo = date;

    }
    public String getRemainingDays() {
        return RemainingDays;

    }

    public void setRemainingDays(String date) {
        this.RemainingDays = date;

    }


}

