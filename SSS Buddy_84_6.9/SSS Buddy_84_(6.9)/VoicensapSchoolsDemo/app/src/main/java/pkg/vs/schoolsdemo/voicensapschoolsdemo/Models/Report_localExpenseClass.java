package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

/**
 * Created by devi on 6/18/2019.
 */

public class Report_localExpenseClass implements Serializable {
    String RefId,Username,monthOfClaim,TotalLocalExpense,Description,idLocalExpense,Status , filepath;
    int IsApproved;

    public Report_localExpenseClass(String refId, String username, String month, String totalLocalExpense, String description, int isApproved, String idLocalExpense, String Status,String filepath){
//        this.idLocalExpense=idlocal;
        this.Username=username;
        this.monthOfClaim=month;
        this.RefId=refId;
        this.TotalLocalExpense=totalLocalExpense;
        this.Description=description;
        this.Status=Status;
        this.IsApproved=isApproved;
//        this.processBy=processBy;
//        this.processType=processType;
//        this.IsApproved=isApproved;
        this.idLocalExpense=idLocalExpense;
//        this.LocalExpenseItems=localExpenseItems;
        this.filepath=filepath;

    }
    public String getIdLocalExpense() {
        return idLocalExpense;

    }

    public void setIdLocalExpense(String idLocalExpense) {
        this.idLocalExpense = idLocalExpense;

    }

    public String getFilepath() {
        return filepath;

    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;

    }




    public String getUsername() {
        return Username;

    }

    public void setUsername(String username) {
        this.Username = username;

    }

    public String getMonthOfClaim() {
        return monthOfClaim;

    }

    public void setMonthOfClaim(String month) {
        this.monthOfClaim = month;

    }

    public String getRefId() {
        return RefId ;

    }

    public void setRefId(String refId) {
        this.RefId = refId;

    }

    public String getTotalLocalExpense() {
        return TotalLocalExpense;

    }

    public void setTotalLocalExpense(String totalLocalExpense) {
        this.TotalLocalExpense = totalLocalExpense;

    }

    public String getDescription() {
        return Description;

    }

    public void setDescription(String description) {
        this.Description = description;

    }



    public int getIsApproved() {
        return IsApproved;

    }

    public void setIsApproved(int approve) {
        this.IsApproved = approve;

    }

    public String getStatus() {
        return Status;

    }

    public void setStatus(String Status) {
        this.Status = Status;

    }
//    public String getProcessType() {
//        return processType;
//
//    }
//
//    public void setProcessType(String result) {
//        this.processType = result;
//
//    }
}
