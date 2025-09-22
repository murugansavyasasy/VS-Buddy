package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

public class UnreadChatModel implements Serializable {
    String POID;
    String UnReadCount;
    String CustomerName;
    String SalesPersionID;
    String PoNumber;
    String ValidRemaining;


    public UnreadChatModel(String id, String count,String name,String salesPersionID,String ponumber,String valid) {
        this.POID = id;
        this.UnReadCount = count;
        this.CustomerName = name;
        this.SalesPersionID = salesPersionID;
        this.PoNumber = ponumber;
        this.ValidRemaining = valid;



    }

    public String getSalesPersionID() {
        return SalesPersionID;

    }

    public void setSalesPersionID(String nameValue1) {
        this.SalesPersionID = nameValue1;

    }

    public String getCustomerName() {
        return CustomerName;

    }

    public void setCustomerName(String idValue1) {
        this.CustomerName = idValue1;

    }

    public String getPOID() {
        return POID;

    }

    public void setPOID(String idValue1) {
        this.POID = idValue1;

    }
    public String getUnReadCount() {
        return UnReadCount;

    }

    public void setUnReadCount(String idValue1) {
        this.UnReadCount = idValue1;

    }

    public String getPoNumber() {
        return PoNumber;

    }

    public void setPoNumber(String idValue1) {
        this.PoNumber = idValue1;

    }

    public String getValidRemaining() {
        return ValidRemaining;

    }

    public void setValidRemaining(String idValue1) {
        this.ValidRemaining = idValue1;

    }



}


