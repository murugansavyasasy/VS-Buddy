package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.OnDemoClickListener;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class ContactNumbersAdapter extends RecyclerView.Adapter<ContactNumbersAdapter.MyViewHolder> {

  //  private List<ContactNumbers> datasList;
    Context context;
    private OnDemoClickListener listener;
    String text="";

    private List<String> datasList;


    public ContactNumbersAdapter(ArrayList<String> datasList, Context context) {
        this.datasList = datasList;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public ContactNumbersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_numbers_list, parent, false);
        return new ContactNumbersAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ContactNumbersAdapter.MyViewHolder holder, int position) {

        final String datas = datasList.get(position);
        final String appPackageName = context.getPackageName();

        if(Pattern.matches("[a-zA-Z]+", datas)){

            String name=datas;
            name = Character.toUpperCase(name.charAt(0)) +
                    name.substring(1).replaceAll("(?<!_)(?=[A-Z])", " ");
            Log.d("name",name);

            holder.txtHeading.setText(name);
            holder.txtHeading.setTypeface(null, Typeface.BOLD);
            holder.txtHeading.setTextColor(context.getResources().getColor(R.color.bpblack));
            holder.txtHeading.setTextSize(25);
        } else {
            Log.d("datas",datas);

            holder.txtHeading.setText(datas);
            holder.txtHeading.setTextColor(context.getResources().getColor(R.color.clr_grey));
            holder.txtHeading.setTextSize(20);
            }

            holder.txtHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datas.contains("@")){
                    String email=holder.txtHeading.getText().toString();
                    checkNumbersOrEmail(email);
                    }



                if(datas.contains("play.google.com")){
                    Log.d("datasplay",datas);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(datas));
                    context.startActivity(intent);
                }else{
                    Log.d("elsedata",datas);


                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(datas));
                    context.startActivity(browserIntent);

                }

//
//                String playstorelink=datas;
//                String playstore= playstorelink.replaceAll("store", "");
//
//                Log.d("text",text);
//                if(playstore.matches("store")){
//                    Log.d("if name",playstore);
//                    Log.d(" dtasd",datas);
//                    Log.d("playstorelink",playstorelink);
//
//
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setData(Uri.parse(playstore + appPackageName));
//                    context.startActivity(intent);
//
//                }

//                if(datas.contains("http")){
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setData(Uri.parse(Playstore + appPackageName));
//                    context.startActivity(intent);
//
//                }
                String str=datas;
                String numberOnly= str.replaceAll("[^0-9]", "");
                if (numberOnly.matches("^[0-9]+$")){
                    Log.d("Numbers",numberOnly);
                    String number=holder.txtHeading.getText().toString();
                    dialingAction(numberOnly);
                }
            }
        });


        }

    private void checkNumbersOrEmail(String a) {

            final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
            /* Fill it with Data */
            emailIntent1.setType("plain/text");
            emailIntent1.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{a});
            emailIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
            emailIntent1.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

            /* Send it off to the Activity-Chooser */
            context.startActivity(Intent.createChooser(emailIntent1, "Send mail..."));


            }

    private void dialingAction(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return datasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeading;
//        public TextView txtNumber1,txtNumber2,txtNumber3,txtNumber4,txtNumber5,txtNumber6,txtNumber7,txtNumber8,
//                txtNumber9,txtNumber10;


        public MyViewHolder(View view) {
            super(view);
            txtHeading = (TextView) view.findViewById(R.id.txtHeading);

//            txtNumber1 = (TextView) view.findViewById(R.id.txtNumber1);
//            txtNumber2 = (TextView) view.findViewById(R.id.txtNumber2);
//            txtNumber3 = (TextView) view.findViewById(R.id.txtNumber3);
//
//            txtNumber4 = (TextView) view.findViewById(R.id.txtNumber4);
//            txtNumber5 = (TextView) view.findViewById(R.id.txtNumber5);
//            txtNumber6 = (TextView) view.findViewById(R.id.txtNumber6);
//            txtNumber7 = (TextView) view.findViewById(R.id.txtNumber7);
//            txtNumber8 = (TextView) view.findViewById(R.id.txtNumber8);
//            txtNumber9 = (TextView) view.findViewById(R.id.txtNumber9);
//            txtNumber10 = (TextView) view.findViewById(R.id.txtNumber10);

//            tvParentNumbercount= (TextView) view.findViewById(R.id.parentnodemo);
//            tvEmailId=(TextView) view.findViewById(R.id.demoprincipalemail);

        }


    }



}
