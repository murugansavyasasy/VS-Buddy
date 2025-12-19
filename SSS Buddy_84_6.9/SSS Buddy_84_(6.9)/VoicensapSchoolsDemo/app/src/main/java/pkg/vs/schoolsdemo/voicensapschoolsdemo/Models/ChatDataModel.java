package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

public class ChatDataModel {


    String idPurchaseOrderConversation;
    String PurchaseOrderID;
    String UserID;
    String Message;
    String DateTime;
    String Name;
    String IconText;
    String ChatSide;
    String isSeen;


    public ChatDataModel(String conversation,String pursId,String userid,String msg,String time,
                         String name, String icontext,String side,String isseen ){

        this.Name=name;
        this.Message=msg;
        this.DateTime=time;
        this.idPurchaseOrderConversation=conversation;
        this.PurchaseOrderID=pursId;
        this.UserID=userid;
        this.IconText=icontext;
        this.ChatSide=side;
        this.isSeen=isseen;

    }
    public String getMessage() {
        return Message;

    }

    public void setMessage(String nameValue1) {
        this.Message = nameValue1;

    }
    public String getName() {
        return Name;

    }

    public void setName(String idValue1) {
        this.Name = idValue1;

    }
    public String getDateTime() {
        return DateTime;

    }

    public void setDateTime(String date) {
        this.DateTime = date;

    }

    public String getIdPurchaseOrderConversation() {
        return idPurchaseOrderConversation;

    }

    public void setIdPurchaseOrderConversation(String date) {
        this.idPurchaseOrderConversation = date;

    }

    public String getPurchaseOrderID() {
        return PurchaseOrderID;

    }

    public void setPurchaseOrderID(String date) {
        this.PurchaseOrderID = date;

    }

    public String getUserID() {
        return UserID;

    }

    public void setUserID(String date) {
        this.UserID = date;

    }

    public String getIconText() {
        return IconText;

    }

    public void setIconText(String date) {
        this.IconText = date;

    }

    public String getChatSide() {
        return ChatSide;

    }

    public void setChatSide(String date) {
        this.ChatSide = date;

    }

    public String getIsSeen() {
        return isSeen;

    }

    public void setIsSeen(String date) {
        this.isSeen = date;

    }
}

