package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.PreviewActivity;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.CircularItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.FilePathItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class CircularEditableAdapter extends RecyclerView.Adapter<CircularEditableAdapter.VH> {

    private final ArrayList<CircularItem> list;
    private final OnItemDeleteListener deleteListener;
    private final OnAttachClickListener attachListener;

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }

    public interface OnAttachClickListener {
        void onAttachClick(int position);
    }

    public CircularEditableAdapter(ArrayList<CircularItem> list, OnItemDeleteListener deleteListener, OnAttachClickListener attachListener) {
        this.list = list;
        this.deleteListener = deleteListener;
        this.attachListener = attachListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_school, parent, false);
        return new VH(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, @SuppressLint("RecyclerView") int position) {
        CircularItem item = list.get(position);
        Log.d("onBindViewHolder", "onBindViewHolder called for position: " + position);


        holder.etTitle.setText(item.title);
        holder.etAddress.setText(item.description);

        if (holder.etTitle.getTag() == null) {
            TextWatcher titleWatcher = new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.title = s.toString();
                }
            };
            holder.etTitle.addTextChangedListener(titleWatcher);
            holder.etTitle.setTag(titleWatcher);
        }

        if (holder.etAddress.getTag() == null) {
            TextWatcher descWatcher = new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.description = s.toString();
                }
            };
            holder.etAddress.addTextChangedListener(descWatcher);
            holder.etAddress.setTag(descWatcher);
        }

        holder.tvPhoneHeader.setVisibility(View.GONE);
        holder.etPhone.setVisibility(View.GONE);

        holder.btnAttach.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && attachListener != null) {
                attachListener.onAttachClick(pos);
            }
        });

        holder.deleteLayout.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && deleteListener != null) {
                deleteListener.onDelete(pos);
            }
        });

        if (holder.rvImages.getAdapter() == null) {
            holder.rvImages.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 3));

            ArrayList<FilePathItem> fileList = new ArrayList<>();
            for (CircularItem.FileItem f : item.filePaths) {
                fileList.add(new FilePathItem(f.url, f.type));
            }

            holder.imageAdapter = new ImageAdapter(fileList, position, (updatedPos, updatedList) -> {
                if (updatedPos >= 0 && updatedPos < list.size()) {
                    CircularItem curr = list.get(updatedPos);
                    curr.filePaths.clear();
                    for (FilePathItem fp : updatedList) {
                        curr.filePaths.add(new CircularItem.FileItem(fp.url, fp.type));
                    }
                }
            });

            holder.imageAdapter.setOnPreviewListener((url, type) -> {
                Context ctx = holder.itemView.getContext();
                Intent intent = new Intent(ctx, PreviewActivity.class);
                intent.putExtra("file_url", url);
                Log.d("Sending URL","file_url:" +url);
                intent.putExtra("file_type", type);
                Log.d("Sending Type","file_type:"+type);
                ctx.startActivity(intent);
            });

            holder.rvImages.setAdapter(holder.imageAdapter);
        } else {
            ArrayList<FilePathItem> updatedFiles = new ArrayList<>();
            for (CircularItem.FileItem f : item.filePaths) {
                updatedFiles.add(new FilePathItem(f.url, f.type));
            }
            holder.imageAdapter.updateList(updatedFiles);
        }
    }

        @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tvTitleHeader, tvAddressHeader, tvPhoneHeader;
        EditText etTitle, etAddress, etPhone;
        Button btnAttach;
        ImageButton btnDelete;
        RecyclerView rvImages;
        LinearLayout deleteLayout;

        public ImageAdapter imageAdapter;

        VH(View itemView) {
            super(itemView);
            tvTitleHeader = itemView.findViewById(R.id.tvSchoolNameHeader);
            etTitle = itemView.findViewById(R.id.etEditName);
            tvAddressHeader = itemView.findViewById(R.id.tvAddressHeader);
            etAddress = itemView.findViewById(R.id.etEditAddress);
            tvPhoneHeader = itemView.findViewById(R.id.tvPhoneHeader);
            etPhone = itemView.findViewById(R.id.etEditPhone);
            btnAttach = itemView.findViewById(R.id.btnAttachImages);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            deleteLayout = itemView.findViewById(R.id.DeleteLayout);
            rvImages = itemView.findViewById(R.id.rvEditImages);
        }
    }
}