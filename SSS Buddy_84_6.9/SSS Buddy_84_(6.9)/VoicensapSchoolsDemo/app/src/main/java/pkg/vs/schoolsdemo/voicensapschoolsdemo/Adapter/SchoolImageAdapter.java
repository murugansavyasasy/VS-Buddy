package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter;

import android.content.ContentResolver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class SchoolImageAdapter extends RecyclerView.Adapter<SchoolImageAdapter.Holder> {

    ArrayList<Uri> files;

    public SchoolImageAdapter(ArrayList<Uri> files) {
        this.files = files;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_school_image, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Uri uri = files.get(position);
        String mime = getMimeType(holder.itemView.getContext().getContentResolver(), uri);

        if (mime == null) mime = "";

        if (mime.startsWith("image/")) {
            Glide.with(holder.itemView.getContext())
                    .load(uri)
                    .into(holder.imgPreview);
        }
        else if (mime.startsWith("video/")) {
            holder.imgPreview.setImageResource(R.drawable.video_dummy_icon);
        }
        else if (mime.startsWith("audio/")) {
            holder.imgPreview.setImageResource(R.drawable.audio_message);
        }
        else if (mime.equals("application/pdf")) {
            holder.imgPreview.setImageResource(R.drawable.pdf_icon);
        }
        else if (mime.contains("word")) {
            holder.imgPreview.setImageResource(R.drawable.doc);
        }
        else if (mime.contains("excel")) {
            holder.imgPreview.setImageResource(R.drawable.excel_file);
        }
        else {
            holder.imgPreview.setImageResource(R.drawable.doc);
        }

        holder.btnRemove.setOnClickListener(v -> {
            files.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private String getMimeType(ContentResolver cr, Uri uri) {
        return cr.getType(uri);
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imgPreview, btnRemove;

        Holder(View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.imgPreview);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
