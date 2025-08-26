package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import static android.view.Gravity.CENTER;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.AdvanceTour;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.EditAdvTour;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.MoveTour;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AdvanceTourClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by devi on 7/6/2019.
 */

public class AdvanceTourAdapter extends RecyclerView.Adapter<AdvanceTourAdapter.MyViewHolder>{
    private List<AdvanceTourClass> tourList;
    Context context;
    TextView tvboarding,tvpetrol,tvmiscelleneous,tvbuisness,tvpostage,tvconveyance,tvprinting,tvfood,tvtravel;
    EditText etreason;
    Button btn_pop_submit;
    ImageView img_pop_close;
    String idTourExpenses;
    String cmd = "Advance",Strprocesstype="Cancel",Strreason;
    String boardlodge,buisness,convtravel,food,fuel,postagecourier,printing,travel,misc;
    String resultMessage, MailMsg;
    int idTourExpense, idDirectorExpense, idLocalExpense;
    int result;
    String directorlogin;

    String userid;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtourid, tvemployee, tvtourdate, tvplace, tvpurpose, tvtotal, tvpaid, tvbalance, tvapprove,tvstatus;
        //        ImageView imgedt, imgdlt;
        Button btnedit, btndelete,btndetails,btnmove;


        public MyViewHolder(View view) {
            super(view);


            tvtourid = (TextView) view.findViewById(R.id.txttourid);
            tvemployee = (TextView) view.findViewById(R.id.txtemployee);
            tvtourdate = (TextView) view.findViewById(R.id.txttourdate);
            tvplace = (TextView) view.findViewById(R.id.txtplace);
            tvpurpose = (TextView) view.findViewById(R.id.txtpurpose);
            tvtotal = (TextView) view.findViewById(R.id.txttotal);
            tvpaid = (TextView) view.findViewById(R.id.txtpaid);
            tvbalance = (TextView) view.findViewById(R.id.txtbalance);
            tvstatus=(TextView)view.findViewById(R.id.txtstatus);
            tvapprove = (TextView) view.findViewById(R.id.Approved);
//            imgedt = (ImageView) view.findViewById(R.id.img_edt);
//            imgdlt = (ImageView) view.findViewById(R.id.img_dlt);
            btnedit = (Button) view.findViewById(R.id.btn_edit);
            btndelete = (Button) view.findViewById(R.id.btn_delete);
            btndetails=(Button)view.findViewById(R.id.btn_details);
            btnmove=(Button)view.findViewById(R.id.btn_move);

        }
    }

    public AdvanceTourAdapter(Context context,List<AdvanceTourClass> tourList) {
        this.tourList = tourList;
        this.context=context;
    }
    public void updateList(List<AdvanceTourClass> temp) {
        tourList = temp;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advance_tour_expense, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final AdvanceTourClass advanceTourClass = tourList.get(position);
        holder.tvtourid.setText(advanceTourClass.getStrtourid());
        holder.tvemployee.setText(advanceTourClass.getStremployee());
        holder.tvtourdate.setText(advanceTourClass.getStrtourdate());
        holder.tvplace.setText(advanceTourClass.getStrplace());
        holder.tvpurpose.setText(advanceTourClass.getStrpurpose());
        holder.tvtotal.setText(advanceTourClass.getStrtotal());
        holder.tvpaid.setText(advanceTourClass.getStrpaid());
        holder.tvbalance.setText(advanceTourClass.getStrbalance());
        holder.tvapprove.setText(advanceTourClass.getStrapproved());
        holder.tvstatus.setText(advanceTourClass.getStrstatus());

        userid = SharedPreference_class.getSh_v_iduser(context);
        Log.d("ADVuserid",userid);

        directorlogin = SharedPreference_class.getSh_v_Usertype(context);
        Log.d("director",directorlogin);


        if (advanceTourClass.getStrapproved().equals("0")&& directorlogin.equals("3")) {
            Log.d("isApproved", advanceTourClass.getStrapproved());
            holder.btnedit.setVisibility(GONE);
            holder.btndelete.setVisibility(GONE);
//            holder.btnapprove.setVisibility(VISIBLE);
//            holder.btndecline.setVisibility(VISIBLE);
        }

        if ( !directorlogin.equals("3")&& advanceTourClass.getStrapproved().equals("0") || advanceTourClass.getStrapproved().equals("2")) {
            Log.d("isApproved", advanceTourClass.getStrapproved());
            holder.btnedit.setVisibility(VISIBLE);
            holder.btndelete.setVisibility(VISIBLE);
//            holder.btnapprove.setVisibility(GONE);
//            holder.btndecline.setVisibility(GONE);
        } else {
            Log.d("isApproved", advanceTourClass.getStrapproved());
            holder.btnedit.setVisibility(GONE);
            holder.btndelete.setVisibility(GONE);
//            holder.btnapprove.setVisibility(GONE);
//            holder.btndecline.setVisibility(GONE);
        }
        if(advanceTourClass.getStrclaim().equals("1")){
            Log.d("isClaimed",advanceTourClass.getStrclaim());
            holder.btnmove.setVisibility(VISIBLE);
        }
        else{
            Log.d("isClaimed",advanceTourClass.getStrclaim());
            holder.btnmove.setVisibility(GONE);

        }
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTourExpenses=advanceTourClass.getStridtourexpenses();

                Intent i=new Intent(v.getContext(), EditAdvTour.class);
                i.putExtra("idTourExpenses",advanceTourClass.getStridtourexpenses());
                v.getContext().startActivity(i);

            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                dlg.setTitle("warning");
                dlg.setMessage("Are You Sure,You Want To Delete The Expense!");
                dlg.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        idTourExpenses = advanceTourClass.getStridtourexpenses();
                        showpopup1(v);
                    }

                    private void showpopup1(final View v) {

                        View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_delete, null);

                        final PopupWindow popupWindow = new PopupWindow(popupView, MATCH_PARENT,
                                MATCH_PARENT, true);
                        popupWindow.showAtLocation(v, CENTER, 0, 0);
                        etreason = (EditText) popupView.findViewById(R.id.reason);

                        Strreason = etreason.getText().toString();
                        etreason.setText(Strreason);



                        img_pop_close = (ImageView) popupView.findViewById(R.id.img_pop_close);
                        img_pop_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });

                        btn_pop_submit = (Button) popupView.findViewById(R.id.btn_SUBMIT);
                        btn_pop_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (etreason.getText().toString().isEmpty()) {
                                    popupWindow.isShowing();
                                    final AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                    dlg.setTitle("Alert");
                                    dlg.setMessage("Please Enter Reason");
                                    dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dlg.setCancelable(true);
                                        }
                                    });
                                    dlg.setCancelable(false);
                                    dlg.create();
                                    dlg.show();
                                } else {
                                    popupWindow.dismiss();
                                    deleteRetrofit(v);

                                }

                            }


                        });
                    }
                });

                dlg.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dlg.setCancelable(true);
                    }
                });
                dlg.setCancelable(false);
                dlg.create();
                dlg.show();
            }

        });
        holder.btnmove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                idTourExpenses=advanceTourClass.getStridtourexpenses();
                Intent i2=new Intent(v.getContext(), MoveTour.class);
                i2.putExtra("idTourExpenses",advanceTourClass.getStridtourexpenses());
                v.getContext().startActivity(i2);

            }
        });
        holder.btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTourExpenses=advanceTourClass.getStridtourexpenses();
                showPopup(v);
            }
            private void showPopup(View v) {
//                TextView tvboarding,tvpetrol,tvmiscelleneous,tvbuisness,tvpostage,tvconveyance,tvprinting,tvfood,tvtravel;
                View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_detail, null);

                final PopupWindow popupWindow = new PopupWindow(popupView, MATCH_PARENT,
                        MATCH_PARENT, true);
                popupWindow.showAtLocation(v, CENTER, 0, 0);

                tvboarding = (TextView) popupView.findViewById(R.id.boarding);
                tvpetrol = (TextView) popupView.findViewById(R.id.petrol);
                tvmiscelleneous = (TextView) popupView.findViewById(R.id.miscelleneous);
                tvbuisness = (TextView) popupView.findViewById(R.id.buisness);
                tvpostage = (TextView) popupView.findViewById(R.id.postage);
                tvconveyance = (TextView) popupView.findViewById(R.id.conveyance);
                tvprinting = (TextView) popupView.findViewById(R.id.printing);
                tvfood = (TextView) popupView.findViewById(R.id.food);
                tvtravel = (TextView) popupView.findViewById(R.id.travel);

                detailsretrofit(v);

                ImageView imgDismiss = (ImageView) popupView.findViewById(R.id.ib_close);
                imgDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
//                popupWindow.showAsDropDown(popupView, 0, 200);
            }
        });

    }

    private void detailsretrofit(final View v) {


        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("idTourExpense", idTourExpenses);
        jsonObject.addProperty("cmd", cmd);
        Log.d("AdvanceTourAdapter:req", jsonObject.toString());
        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

        Call<JsonArray> call = apiService.Details(idTourExpenses, cmd);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                try {
                    Log.d("AdvanceTourAdapter:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {

                        JSONObject temp;

                        temp = jsonArrayorgList.getJSONObject(0);

                        boardlodge = temp.getString("BoardLodge");
                        buisness = temp.getString("BusinessPromo");
                        convtravel = temp.getString("ConvTravel");
                        food =  temp.getString("Food");
                        fuel = temp.getString("Fuel");
                        postagecourier = temp.getString("PostageCourier");
                        printing = temp.getString("Printing");
                        travel = temp.getString("Travel");
                        misc = temp.getString("Misc");

                        tvboarding.setText(boardlodge);
                        tvpetrol.setText(fuel);
                        tvmiscelleneous.setText(misc);
                        tvbuisness.setText(buisness);
                        tvpostage.setText(postagecourier);
                        tvconveyance.setText(convtravel);
                        tvprinting.setText(printing);
                        tvfood.setText(food);
                        tvtravel.setText(travel);


                    }
                    Log.d("Server Response", strResponse);


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Log.e("Response Failure", t.getMessage());
                showToast("Server Connection Failed");
            }

            private void showToast(String msg) {
                Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteRetrofit(final View v) {

        Strreason = etreason.getText().toString();
        etreason.setText(Strreason);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("DeclineId", idTourExpenses);
        jsonObject.addProperty("cmd", cmd);
        jsonObject.addProperty("processType", Strprocesstype);
        jsonObject.addProperty("processby", userid);
        Log.d("advanceuserid",userid);

        jsonObject.addProperty("Reason", Strreason);
        Log.d("AdvanceTourAdapter:req", jsonObject.toString());


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.Delete(jsonObject);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                try {
                    Log.d("AdvanceTourAdapter:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);
                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        temp = jsonArrayorgList.getJSONObject(0);
                        result = Integer.parseInt(temp.getString("result"));
                        resultMessage = temp.getString("resultMessage");
                        MailMsg = temp.getString("MailMsg");
                        //  WebUrl = temp.getString("WebUrl");
                        // AccountManagerMailId = temp.getString("AccountManagerMailId");
                        idTourExpense = temp.getInt("idTourExpense");
                        idDirectorExpense = temp.getInt("idDirectorExpense");
                        idLocalExpense = temp.getInt("idLocalExpense");

                        if (result == 1) {
                            alertDialog1(resultMessage, v);
                        } else {
                            alertDialog1(resultMessage, v);
                        }
                    }
                    Log.d("Server Response", strResponse);


                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }


            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Log.e("Response Failure", t.getMessage());
                showToast("Server Connection Failed");
            }
            private void showToast(String msg) {
                Toast.makeText(v.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void alertDialog1(String msg, final View v) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(result==1){
                    dialog.dismiss();
                    Intent finish=new Intent(v.getContext(), AdvanceTour.class);
                    v.getContext().startActivity(finish);

                }
                dlg.setCancelable(true);
            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }
    public int getItemCount() {
        return tourList.size();
    }
}
