package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

/**
 * Created by devi on 7/17/2019.
 */

public class Pdf_adapter  extends RecyclerView.Adapter<Pdf_adapter.MyViewHolder>  {
    private ArrayList<String> pdflist;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        LinearLayout layout_pdf;


        public MyViewHolder(View v) {
            super(v);

            text=(TextView)v.findViewById(R.id.pdf);
            layout_pdf=(LinearLayout)v.findViewById(R.id.pdf_layout);

        }
    }

    public Pdf_adapter(Context context, ArrayList<String> Imagelist) {
        this.pdflist = Imagelist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pdf_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final Pdf_adapter.MyViewHolder holder, int position) {
        final String pdf_path = pdflist.get(position);

        holder.text.setText(pdf_path);

    }
    @Override
    public int getItemCount() {
        return pdflist.size();

    }


}
