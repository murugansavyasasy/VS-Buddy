package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by devi on 4/29/2019.
 */

public class SharedPreference_class {

    public static final String SH_PREF = "Mypref";
    public  static final String SH_UPDATE="DemoApp";
    public static final String SH_UPDATE_PREF = "MY_UPDATE";

    public static final String SH_v_EMPLOYEEID = "EmployeeId";
    public static final String SH_v_PASSWORD = "Password";
    public static final String SH_v_USERID = "VimsIdUser";
    public static final String SH_v_USERTYPEID = "VimsUserTypeId";
    public static final String SH_v_NUMBER = "VimsNumber";
    public static final String SH_v_USERNAME = "Priya Naganathan";
    public static final String SH_filter = "filter";



    public static final String SH_SCHL_USERTYPE = "Admin";
    public static final String SH_SCHL_LOGINID = "SchoolLoginId";
    public static final String SH_LOCATION = "Location";
    public static final String SH_REGION = "Region";
    public static final String SH_CUSTOMERNAME="customer";

    public static final String SH_IDVALUE="idValue";
    public static final String SH_CUSTOMERTYPE="customerType";
    public static final String SH_IDLOCALEXPENSE="idLocalExpense";
    public static final String SH_LOCALEXPENSE="idlocal";
    public static final String SH_IMAGE="IMAGE";
    public static final String SH_PDF="PDF";





    public static  void putLogin_detail(Activity activity, String v_emp_id, String v_password, String v_iduser, String v_Username,String v_user_typeid,String S_user_type,String S_loginid,String V_number,String location,String region) {
        android.content.SharedPreferences sharepref = activity.getSharedPreferences(SH_PREF, 0);
        SharedPreferences.Editor ed = sharepref.edit();
        ed.putString(SH_v_EMPLOYEEID, v_emp_id);
        ed.putString(SH_v_PASSWORD, v_password);
        ed.putString(SH_v_USERID, v_iduser);
        ed.putString(SH_v_USERNAME, v_Username);
        ed.putString(SH_v_USERTYPEID, v_user_typeid);
        ed.putString(SH_SCHL_USERTYPE, S_user_type);
        ed.putString(SH_SCHL_LOGINID, S_loginid);
        ed.putString(SH_v_NUMBER, V_number);
        ed.putString(SH_LOCATION,location );
        ed.putString(SH_REGION, region);
//        ed.putString(SH_UPDATE, update);
        ed.apply();
        ed.commit();

        return;

    }

    public  static void putUpdation(Context activity, String update){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_UPDATE_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_UPDATE,update);
        ed.apply();
        ed.commit();
        return;


    }
    public  static void putfilter(Context activity, String update){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_UPDATE_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_filter,update);
        ed.apply();
        ed.commit();
        return;


    }

    public  static void putSh_Image(Activity activity, String image){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_UPDATE_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_IMAGE,image);
        ed.apply();
        ed.commit();
        return;

    }
    public  static void putSh_Pdf(Activity activity, String pdf){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_UPDATE_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_PDF,pdf);
        ed.apply();
        ed.commit();
        return;

    }


    public  static void putCustomertype(Activity activity, String customertype){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_CUSTOMERTYPE,customertype);
        ed.commit();
        return;


    }
    public  static void putCustomerName(Activity activity, String customername){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_CUSTOMERNAME,customername);
        ed.commit();
        return;


    }
    public  static void putidlocalExpense(Activity activity, String idlocal){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_IDLOCALEXPENSE,idlocal);
        ed.commit();
        return;
    }
    public  static void putReport_Expenseidlocal(Activity activity, String localid){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(SH_PREF,0);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SH_LOCALEXPENSE,localid);
//        Log.d("localid",SH_LOCALEXPENSE);
        ed.commit();
        return;
    }


    public  static  String getShSchlUsertype(Activity activity){
        String schl_usertype = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_SCHL_USERTYPE, "");
        return schl_usertype;
    }
    public  static  String getShSchlLoginid(Activity activity){
        String schl_loginid = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_SCHL_LOGINID, "");
        Log.d("Schl_loginid",schl_loginid);
        return schl_loginid;
    }
    public static String getShIdvalue(Activity activity) {
        String idvalue = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_IDVALUE, "");
        return idvalue;
    }


    public static String getUserid(Activity activity) {
        String userid = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_USERID, "");
        return userid;
    }
    public static String getShcustomertype(Context activity) {
        String cus_type = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_CUSTOMERTYPE, "");
        return cus_type;
    }


    public  static  String getSchoolType(Context activity){
        String schl_usertype = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_SCHL_USERTYPE, "");
        return schl_usertype;
    }


    public static String getShPassword(Activity activity) {
        String password = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_PASSWORD, "");
        return password;
    }
    public static String getShEmployeeid(Activity activity) {
        String Empid = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_EMPLOYEEID, "");
        return Empid;
    }
    public static String getShUpdate(Activity activity) {
        String update = activity.getSharedPreferences(SH_UPDATE_PREF, MODE_PRIVATE).getString(SH_UPDATE, "");
        return update;
    }
    public static String getShV_Username(Activity activity) {
        String username = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_NUMBER, "");
        return username;
    }

    public static String getCustomername(Activity activity) {
        String customername= activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_CUSTOMERNAME, "");
        return customername;
    }
        public static String getShIdlocalexpense(Activity activity) {
        String idlocalexpense = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_IDLOCALEXPENSE, "");

        return idlocalexpense;
    }
    public static String getSh_v_Usertype(Context activity) {
        String usertype = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_USERTYPEID, "");
        return usertype;
    }

    public static String getSh_v_iduser(Context activity) {
        String user = activity.getSharedPreferences(SH_PREF, MODE_PRIVATE).getString(SH_v_USERID, "");
        return user;
    }
    public static String getShImage(Context activity) {
        String img = activity.getSharedPreferences(SH_UPDATE_PREF, MODE_PRIVATE).getString(SH_IMAGE, "");
        return  img;
    }
    public static String getShpdf(Context activity) {
        String pdf = activity.getSharedPreferences(SH_UPDATE_PREF, MODE_PRIVATE).getString(SH_PDF, "");
        return pdf;
    }
}
