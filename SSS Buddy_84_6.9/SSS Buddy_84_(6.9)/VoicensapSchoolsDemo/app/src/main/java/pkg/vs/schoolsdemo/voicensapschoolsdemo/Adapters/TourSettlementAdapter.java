package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

/**
 * Created by devi on 7/19/2019.
 */

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

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.EditTourSettlement;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.TourSettlement;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.TourSettlement_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourSettlementAdapter extends RecyclerView.Adapter<TourSettlementAdapter.MyViewHolder> {
    private List<TourSettlement_class> tourList;
    TextView tvboarding, tvpetrol, tvmiscelleneous, tvbuisness, tvpostage, tvconveyance, tvprinting, tvfood, tvtravel,
            tvboarding1, tvpetrol1, tvmiscelleneous1, tvbuisness1, tvpostage1, tvconveyance1, tvprinting1, tvfood1, tvtravel1;
    EditText etreason;
    Button btn_pop_submit;
    ImageView img_pop_close;
    String idTourExpenses, isClaimed;
    String cmd = "direct", Strprocesstype = "Cancel", Strreason;
    String boardlodge, buisness, convtravel, food, fuel, postagecourier, printing, travel, misc,
            boardlodge1, buisness1, convtravel1, food1, fuel1, postagecourier1, printing1, travel1, misc1;
    String resultMessage, MailMsg, WebUrl, AccountManagerMailId;
    int idTourExpense, idDirectorExpense, idLocalExpense;
    int result;
    String userid;
    String directorlogin;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvtourid, tvemployee, tvtourdate, tvplace, tvpurpose, tvtotal, tvpaid, tvbalance, tvapprove, tvstatus;
        //        ImageView imgedt, imgdlt;
        Button btnedit, btndelete, btndetails, btnmove;


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
            tvstatus = (TextView) view.findViewById(R.id.txtstatus);
//            tvapprove = (TextView) view.findViewById(R.id.approve);
//            imgedt = (ImageView) view.findViewById(R.id.img_edt);
//            imgdlt = (ImageView) view.findViewById(R.id.img_dlt);
            btnedit = (Button) view.findViewById(R.id.btn_edit);
            btndelete = (Button) view.findViewById(R.id.btn_delete);
            btndetails = (Button) view.findViewById(R.id.btn_details);
            btnmove = (Button) view.findViewById(R.id.btn_move);

        }
    }

    public TourSettlementAdapter(Context context, List<TourSettlement_class> tourList) {
        this.tourList = tourList;
        this.context = context;
    }

    public void updateList(List<TourSettlement_class> temp) {
        tourList = temp;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.toursettle_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final TourSettlement_class tourSettlementClass = tourList.get(position);
        holder.tvtourid.setText(tourSettlementClass.getStrtourid());
        holder.tvemployee.setText(tourSettlementClass.getStremployee());
        holder.tvtourdate.setText(tourSettlementClass.getStrtourdate());
        holder.tvplace.setText(tourSettlementClass.getStrplace());
        holder.tvpurpose.setText(tourSettlementClass.getStrpurpose());
        holder.tvtotal.setText(tourSettlementClass.getStrtotal());
        holder.tvpaid.setText(tourSettlementClass.getStrpaid());
        holder.tvbalance.setText(tourSettlementClass.getStrbalance());

        holder.tvstatus.setText(tourSettlementClass.getStrstatus());

        userid = SharedPreference_class.getSh_v_iduser(context);
        directorlogin = SharedPreference_class.getSh_v_Usertype(context);
        if (tourSettlementClass.getStrapproved().equals("0") && directorlogin.equals("3")) {
            Log.d("isApproved", tourSettlementClass.getStrapproved());
            holder.btnedit.setVisibility(GONE);
            holder.btndelete.setVisibility(GONE);
//            holder.btnapprove.setVisibility(VISIBLE);
//            holder.btndecline.setVisibility(VISIBLE);
        }


//          approved=tourSettlementClass.getStrapproved();
        if (!directorlogin.equals("3") && tourSettlementClass.getStrapproved().equals("0") || tourSettlementClass.getStrapproved().equals("2")) {
            Log.d("isApproved", tourSettlementClass.getStrapproved());
            holder.btnedit.setVisibility(VISIBLE);
            holder.btndelete.setVisibility(VISIBLE);
//            holder.btnapprove.setVisibility(GONE);
//            holder.btndecline.setVisibility(GONE);
        } else {
            Log.d("isApproved", tourSettlementClass.getStrapproved());
            holder.btnedit.setVisibility(GONE);
            holder.btndelete.setVisibility(GONE);
//            holder.btnapprove.setVisibility(GONE);
//            holder.btndecline.setVisibility(GONE);
        }
//        if(advanceTourClass.getStrclaim().equals("1")){
//            Log.d("isClaimed",advanceTourClass.getStrclaim());
//            holder.btnmove.setVisibility(VISIBLE);
//        }
//        else{
//            Log.d("isClaimed",advanceTourClass.getStrclaim());
//            holder.btnmove.setVisibility(GONE);
//
//        }
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTourExpenses = tourSettlementClass.getStridtourexpenses();

                Intent i = new Intent(v.getContext(), EditTourSettlement.class);
                i.putExtra("idTourExpenses", tourSettlementClass.getStridtourexpenses());
                Log.d("idTourExpenses", tourSettlementClass.getStridtourexpenses());
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
                        dlg.setCancelable(false);
                        idTourExpenses = tourSettlementClass.getStridtourexpenses();
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
//                        btn_pop_close = (Button) popupView.findViewById(R.id.popclose);
//                        btn_pop_close.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                popupWindow.dismiss();
//                            }
//                        });
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


                                        }
                                    });
                                    dlg.setCancelable(false);
                                    dlg.create();
                                    dlg.show();
                                } else {


                                    dlg.setCancelable(true);
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
//        holder.btnmove.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                idTourExpenses=advanceTourClass.getStridtourexpenses();
//                Intent i2=new Intent(v.getContext(), MoveTour.class);
//                i2.putExtra("idTourExpenses",advanceTourClass.getStridtourexpenses());
//                v.getContext().startActivity(i2);
//
//            }
//        });
        holder.btndetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idTourExpenses = tourSettlementClass.getStridtourexpenses();
                showPopup(v);
            }

            private void showPopup(View v) {
//                TextView tvboarding,tvpetrol,tvmiscelleneous,tvbuisness,tvpostage,tvconveyance,tvprinting,tvfood,tvtravel;
                View popupView = LayoutInflater.from(v.getContext()).inflate(R.layout.details_tour_settlement, null);

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
                tvboarding1 = (TextView) popupView.findViewById(R.id.boarding1);
                tvpetrol1 = (TextView) popupView.findViewById(R.id.petrol1);
                tvmiscelleneous1 = (TextView) popupView.findViewById(R.id.miscelleneous1);
                tvbuisness1 = (TextView) popupView.findViewById(R.id.buisness1);
                tvpostage1 = (TextView) popupView.findViewById(R.id.postage1);
                tvconveyance1 = (TextView) popupView.findViewById(R.id.conveyance1);
                tvprinting1 = (TextView) popupView.findViewById(R.id.printing1);
                tvfood1 = (TextView) popupView.findViewById(R.id.food1);
                tvtravel1 = (TextView) popupView.findViewById(R.id.travel1);

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

        Call<JsonArray> call = apiService.TourDetails(idTourExpenses, cmd);

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
                        food = temp.getString("Food");
                        fuel = temp.getString("Fuel");
                        postagecourier = temp.getString("PostageCourier");
                        printing = temp.getString("Printing");
                        travel = temp.getString("Travel");
                        misc = temp.getString("Misc");
                        boardlodge1 = temp.getString("BoardLodgeWithoutBill");
                        buisness1 = temp.getString("BusinessPromoWithoutBill");
                        convtravel1 = temp.getString("ConvTravelWithoutBill");
                        food1 = temp.getString("FoodWithoutBill");
                        fuel1 = temp.getString("FuelWithoutBill");
                        postagecourier1 = temp.getString("PostageCourierWithoutBill");
                        printing1 = temp.getString("PrintingWithoutBill");
                        travel1 = temp.getString("TravelWithoutBill");
                        misc1 = temp.getString("MiscWithoutBill");

                        tvboarding.setText(boardlodge);
                        tvpetrol.setText(fuel);
                        tvmiscelleneous.setText(misc);
                        tvbuisness.setText(buisness);
                        tvpostage.setText(postagecourier);
                        tvconveyance.setText(convtravel);
                        tvprinting.setText(printing);
                        tvfood.setText(food);
                        tvtravel.setText(travel);
                        tvboarding1.setText(boardlodge1);
                        tvpetrol1.setText(fuel1);
                        tvmiscelleneous1.setText(misc1);
                        tvbuisness1.setText(buisness1);
                        tvpostage1.setText(postagecourier1);
                        tvconveyance1.setText(convtravel1);
                        tvprinting1.setText(printing1);
                        tvfood1.setText(food1);
                        tvtravel1.setText(travel1);


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
        jsonObject.addProperty("Reason", Strreason);
        Log.d("TourAdapter:req", jsonObject.toString());


        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<JsonArray> call = apiService.Delete(jsonObject);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                try {
                    Log.d("TourAdapter:Res", response.toString());
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
                Intent finish = new Intent(v.getContext(), TourSettlement.class);
                v.getContext().startActivity(finish);
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



