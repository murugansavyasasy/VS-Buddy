package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

/**
 * Created by devi on 4/23/2019.
 */

public class CustomerListClass implements Serializable {
    String tallyCustomerId,idCustomer, customerTypeName,customerName, contactPerson, salesPersonName, billingCity, SchoolServerId;

    public CustomerListClass(String tallyCustomerid, String customerid, String customName, String customtypName, String contactPerson, String salesPersonName, String billingCity, String schoolServerId) {
        this.tallyCustomerId = tallyCustomerid;
        this.idCustomer=customerid;

        this.customerName = customName;
        this.customerTypeName = customtypName;
        this.contactPerson = contactPerson;
        this.salesPersonName = salesPersonName;
        this.billingCity = billingCity;
        this.SchoolServerId = schoolServerId;
//        this.result=result;

    }

    public String getTallyCustomerId() {
        return tallyCustomerId;

    }

    public void setTallyCustomerId(String tallycustomid) {
        this.tallyCustomerId = tallycustomid;

    }
    public String getIdCustomer() {
        return idCustomer;

    }

    public void setIdCustomer(String customerid) {
        this.idCustomer = customerid;

    }

    public String getCustomerName() {
        return customerName;

    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;

    }

    public String getCustomerTypeName() {
        return customerTypeName;

    }

    public void setCustomerTypeName(String customertype) {
        this.customerTypeName = customertype;

    }

    public String getContactPerson() {
        return contactPerson;

    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;

    }

    public String getSalesPersonName() {
        return salesPersonName;

    }

    public void setSalesPersonName(String salespersonname) {
        this.salesPersonName = salespersonname;

    }

    public String getBillingCity() {
        return billingCity;

    }

    public void setBillingCity(String Billing) {
        this.billingCity = Billing;

    }

    public String getSchoolServerId() {
        return SchoolServerId;

    }

    public void setSchoolServerId(String schoolServerId) {
        this.SchoolServerId = schoolServerId;

    }
//    public String getResult() {
//        return result;
//
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//
//    }

}
