package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

/**
 * Created by devi on 5/2/2019.
 */

public class PopUp_class implements Serializable {
    String nameValue;
    String idValue;



    public PopUp_class(String nameValue, String idValue ){
        this.nameValue=nameValue;
        this.idValue=idValue;

    }

    public String getNameValue() {
        return nameValue;

    }

    public void setNameValue(String nameValue1) {
        this.nameValue = nameValue1;

    }
    public String getIdValue() {
        return idValue;

    }

    public void setIdValue(String idValue1) {
        this.idValue = idValue1;

    }

}
