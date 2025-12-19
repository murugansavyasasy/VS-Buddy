package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.FilePathItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {

    private ArrayList<FilePathItem> fileList;
    private int parentPos;

    public interface OnFilesUpdated {
        void onChanged(int position, ArrayList<FilePathItem> newList);
    }

    private OnFilesUpdated callback;

    public interface OnPreviewListener {
        void onPreview(String url, String type);
    }

    private OnPreviewListener previewListener;

    public void setOnPreviewListener(OnPreviewListener listener) {
        this.previewListener = listener;
    }


    public ImageAdapter(ArrayList<FilePathItem> fileList, int parentPos, OnFilesUpdated callback) {
        this.fileList = fileList;
        this.parentPos = parentPos;
        this.callback = callback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_school_image, parent, false);
        return new Holder(v);
    }

    public void updateList(ArrayList<FilePathItem> newList) {
        Log.d("Checking", "images updated Count: " + newList.size());
        this.fileList.clear();
        this.fileList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        FilePathItem item = fileList.get(position);

        String type = item.type.toUpperCase();
        String url = item.url;

        switch (type) {

            case "IMAGE":
                Glide.with(holder.itemView.getContext())
                        .load(url)
                        .into(holder.imgPreview);
                break;

            case "VIDEO":
                holder.imgPreview.setImageResource(R.drawable.video_dummy_icon);
                break;

            case "AUDIO":
                holder.imgPreview.setImageResource(R.drawable.audio_message);
                break;

            case "PDF":
                holder.imgPreview.setImageResource(R.drawable.pdf_icon);
                break;

            case "EXCEL":
            case "XLS":
            case "XLSX":
                holder.imgPreview.setImageResource(R.drawable.excel_file);
                break;

            case "PPT":
            case "PPTX":
                holder.imgPreview.setImageResource(R.drawable.ppt);
                break;

            case "DOC":
            case "DOCX":
                holder.imgPreview.setImageResource(R.drawable.doc);
                break;

            default:
                holder.imgPreview.setImageResource(R.drawable.doc);
                break;
        }

        holder.btnRemove.setOnClickListener(v -> {
            fileList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, fileList.size());
            Log.d("Remove", "Remaining files: " + fileList.size());
            callback.onChanged(parentPos, fileList);
        });

        holder.imgPreview.setOnClickListener(v -> {
            if (previewListener != null) {
                previewListener.onPreview(item.url, item.type);
            }
        });

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView imgPreview, btnRemove;

        public Holder(View itemView) {
            super(itemView);
            imgPreview = itemView.findViewById(R.id.imgPreview);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}