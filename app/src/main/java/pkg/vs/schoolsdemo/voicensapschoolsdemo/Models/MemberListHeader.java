package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

public class MemberListHeader {
    String SalesPersionID;
    String Name;


    public MemberListHeader(String id, String name) {
        this.SalesPersionID = id;
        this.Name = name;


    }

    public String getSalesPersionID() {
        return SalesPersionID;

    }

    public void setSalesPersionID(String nameValue1) {
        this.SalesPersionID = nameValue1;

    }

    public String getName() {
        return Name;

    }

    public void setName(String idValue1) {
        this.Name = idValue1;

    }


}

