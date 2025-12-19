package pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SchoolDocumentsClass;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by devi on 4/23/2019.
 */

public class SchoolDocumentsAdapter extends RecyclerView.Adapter<SchoolDocumentsAdapter.ViewHolder> {
    private ArrayList<SchoolDocumentsClass> doclist;
    Context context;
    String usertpye_id;
    public ProgressDialog mProgressDialog;
    public static String pdfFilePath;
    String FolderName = "//SchoolDocs";

    private static final String FILE_URL = ""; // Replace with your file URL
    private static String FILE_EXTENSION = ""; // Change the file extension as needed

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDesc, tvUrl, tvtype;

        LinearLayout crd_view;
        TextView btnview;

        public ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvDesc = (TextView) v.findViewById(R.id.tvDescription);
            tvUrl = (TextView) v.findViewById(R.id.tvUrl);
            tvtype = (TextView) v.findViewById(R.id.tvType);
            btnview = (TextView) v.findViewById(R.id.btnView);
            crd_view = (LinearLayout) v.findViewById(R.id.crd_view);

        }
    }

    public SchoolDocumentsAdapter(Context context, ArrayList<SchoolDocumentsClass> getList) {
        this.doclist = getList;
        this.context = context;
    }

    @Override
    public SchoolDocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_document, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SchoolDocumentsClass details = doclist.get(position);


        if (details.getDocumentType().equals("video")) {
            holder.tvName.setText(details.getDocumentName());
            holder.tvDesc.setText(details.getDocumentDescription());
            holder.btnview.setText("Play Video");
            holder.crd_view.setVisibility(View.GONE);
        } else {
            holder.crd_view.setVisibility(View.VISIBLE);
            holder.tvName.setText(details.getDocumentName());
            holder.tvDesc.setText(details.getDocumentDescription());
            holder.btnview.setText("Download & View Document");
        }
        holder.btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util_common.Doc_Type = details.getDocumentType();
                Util_common.Doc_URL = details.getDocumentURL();
                Log.d("Util_common.Doc_URL", Util_common.Doc_URL);
                Util_common.Doc_ID = details.getId();
                Util_common.Doc_Title = details.getDocumentName();
                new DownloadFileTask(context).execute(details.getDocumentURL());
            }
        });


        usertpye_id = SharedPreference_class.getSh_v_Usertype(context);
        Log.d("usertype", usertpye_id);


    }

    @Override
    public int getItemCount() {
        return doclist.size();

    }

    private class DownloadFileTask extends AsyncTask<String, String, File> {
        private Context context;

        public DownloadFileTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Downloading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected File doInBackground(String... urls) {
            String fileUrl = urls[0];

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                // Extract the filename from the URL
                String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

                // Save file in external storage
                File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "SchoolDocs");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file = new File(directory, fileName);

                // ✅ Delete old file if it exists
                if (file.exists()) {
                    boolean deleted = file.delete();
                    Log.d("DOWNLOAD", "Old file deleted: " + deleted);
                }

                // Download file
                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();

                return file;

            } catch (Exception e) {
                Log.e("DOWNLOAD", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(File file) {
            mProgressDialog.dismiss();

            if (file != null) {
                Log.d("DOWNLOAD", "File downloaded at: " + file.getAbsolutePath());
                openFile(context, file);
            } else {
                Log.e("DOWNLOAD", "File download failed!");
            }
        }
    }

    private void openFile(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context, "pkg.vs.schoolsdemo.voicensapschoolsdemo.provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, getMimeType(file.getAbsolutePath()));
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    private String getMimeType(String filePath) {
        if (filePath.endsWith(".pdf")) return "application/pdf";
        if (filePath.endsWith(".doc") || filePath.endsWith(".docx")) return "application/msword";
        if (filePath.endsWith(".ppt") || filePath.endsWith(".pptx"))
            return "application/vnd.ms-powerpoint";
        if (filePath.endsWith(".xls") || filePath.endsWith(".xlsx"))
            return "application/vnd.ms-excel";
        return "*/*"; // Default
    }
}





