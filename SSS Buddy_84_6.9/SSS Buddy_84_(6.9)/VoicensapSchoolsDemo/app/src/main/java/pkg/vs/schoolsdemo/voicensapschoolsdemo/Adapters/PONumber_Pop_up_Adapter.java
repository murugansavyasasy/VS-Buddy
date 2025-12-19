package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.GetIndividualPO_Corporate;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.GetIndividualPO_Huddle;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities.Get_IndividualPO_School;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.PopUp_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


/**
 * Created by devi on 5/3/2019.
 */

public  class PONumber_Pop_up_Adapter extends RecyclerView.Adapter<PONumber_Pop_up_Adapter.ViewHolder> {

    private ArrayList<PopUp_class> namelist;
    String iduser;
    String customerType;
    String idValue;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button Namevalue;
        TextView idValue;
        Button popclose;





        public ViewHolder(View v) {
            super(v);
            Namevalue=(Button) v.findViewById(R.id.pop_uplist);
            idValue=(TextView)v.findViewById(R.id.idvalue);


//            popclose=(TextView)v.findViewById(R.id.pop_close);

        }
    }
    public PONumber_Pop_up_Adapter(Context context, ArrayList<PopUp_class> namelist) {
        this.namelist = namelist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pop_up, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PopUp_class customer1 = namelist.get(position);
        holder.Namevalue.setText(customer1.getNameValue());
        holder.idValue.setText(customer1.getIdValue());


        customerType= SharedPreference_class.getShcustomertype(context);

        holder.Namevalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customerType.equals("1")){

                    Intent intent = new Intent(v.getContext(), Get_IndividualPO_School.class);
                    intent.putExtra("IDvalue",customer1);
                    context.startActivity(intent);

                }else if(customerType.equals("2")){
                    Intent intent = new Intent(v.getContext(), GetIndividualPO_Corporate.class);
                    intent.putExtra("IDvalue",customer1);
                    context.startActivity(intent);

                }else if(customerType.equals("3")){
                    Intent intent = new Intent(v.getContext(), GetIndividualPO_Huddle.class);
                    intent.putExtra("IDvalue",customer1);
                    context.startActivity(intent);

                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return namelist.size();


    }



}





















