package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;


/**
 * Created by devi on 6/5/2019.
 */

public class imageListAdapter extends RecyclerView.Adapter<imageListAdapter.MyViewHolder> {

    ArrayList<String> Imagelist;
    Context context;
    String File_img;
    String File_pdf;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView text;
        LinearLayout layout_pdf;
        LinearLayout Layout_img;


        public MyViewHolder(View v) {
            super(v);

            Image = (ImageView) v.findViewById(R.id.list_image);
            Layout_img=(LinearLayout)v.findViewById(R.id.image_layout2);

            text=(TextView)v.findViewById(R.id.pdf);
            layout_pdf=(LinearLayout)v.findViewById(R.id.pdf_layout);

        }
    }

    public imageListAdapter(Context context, ArrayList<String> Imagelist) {
//        super(context,Imagelist);
        this.Imagelist = Imagelist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallerylist_image, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final imageListAdapter.MyViewHolder holder, int position) {

        final String image = Imagelist.get(position);

        File_img= SharedPreference_class.getShImage(context);
        Log.d("gallery",File_img);
        if(File_img.equals("Pick")){
            Log.d("checkimage", String.valueOf(File_img));
            Glide.with(context)
                    .load(image)
                    .into(holder.Image);

            holder.layout_pdf.setVisibility(View.GONE);
            holder.Layout_img.setVisibility(View.VISIBLE);

        }else if(File_img.equals("PDF_file")){
            Log.d("checkpdf", String.valueOf(File_pdf));

            holder.text.setText(image);
            holder.layout_pdf.setVisibility(View.VISIBLE);
            holder.Layout_img.setVisibility(View.GONE);


        }

    }
    @Override
    public int getItemCount() {
        return Imagelist.size();

    }

}
