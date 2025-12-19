package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

/**
 * Created by devi on 5/21/2019.
 */

public class Discountinfo_huddle {


    String AboveValue;
    String DiscountPercentage;



    public Discountinfo_huddle(String aboveValue, String DiscountPercentage ){
        this.AboveValue=aboveValue;
        this.DiscountPercentage=DiscountPercentage;


    }



    public String getAboveValue() {
        return AboveValue;

    }

    public void setAboveValue(String aboveValue) {
        this.AboveValue = aboveValue;

    }
    public String getDiscountPercentage() {
        return DiscountPercentage;

    }

    public void setDiscountPercentage(String discountPercentage) {
        this.DiscountPercentage = discountPercentage;

    }

}
