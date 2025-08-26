package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

public class RequirementModel {
    String Message;
    String Title;
    String DateTime;
    String SendBy;
    String ID;

    public RequirementModel(String title, String message,String date,String sendby,String id ){
        this.Title=title;
        this.Message=message;
        this.DateTime=date;
        this.SendBy=sendby;
        this.ID=id;


    }
    public String getMessage() {
        return Message;

    }

    public void setMessage(String nameValue1) {
        this.Message = nameValue1;

    }
    public String getTitle() {
        return Title;

    }

    public void setTitle(String idValue1) {
        this.Title = idValue1;

    }
    public String getDateTime() {
        return DateTime;

    }

    public void setDateTime(String date) {
        this.DateTime = date;

    }

    public String getSendBy() {
        return SendBy;

    }

    public void setSendBy(String date) {
        this.SendBy = date;

    }

    public String getID() {
        return ID;

    }

    public void setID(String date) {
        this.ID = date;

    }

}
