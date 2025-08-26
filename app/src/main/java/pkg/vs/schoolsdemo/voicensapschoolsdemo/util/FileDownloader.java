package pkg.vs.schoolsdemo.voicensapschoolsdemo.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileDownloader {

    private Context context;
    private ProgressDialog mProgressDialog;

    public FileDownloader(Context context) {
        this.context = context;
    }

    public void downloadFeeReceipt(String filename) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlAsync(Util_common.Doc_URL);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("DOWNLOAD", "Server contacted, starting download...");
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        String filePath = writeResponseBodyToDisk(response.body(), filename);
                        Log.d("DOWNLOAD", "File download success? " + (filePath != null));

                        new Handler(Looper.getMainLooper()).post(() -> {
                            mProgressDialog.dismiss();
                            if (filePath != null) {
                                openFile(filePath);
                            } else {
                                Log.e("DOWNLOAD", "File download failed");
                            }
                        });
                    });
                    executor.shutdown();
                } else {
                    Log.e("DOWNLOAD", "Server contact failed");
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("DOWNLOAD", "Error: " + t.toString());
            }
        });
    }

    private String writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            // Save file in external app storage (Scoped Storage Compatible)
            File dir = context.getExternalFilesDir(null);
            if (dir == null) return null;

            File file = new File(dir, fileName);
            if (file.exists()) {
                boolean deleted = file.delete();
                Log.d("DOWNLOAD", "Old file deleted: " + deleted);
            }

            try (InputStream inputStream = body.byteStream();
                 OutputStream outputStream = new FileOutputStream(file)) {

                byte[] fileReader = new byte[4096];
                int read;
                while ((read = inputStream.read(fileReader)) != -1) {
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();

                Log.d("DOWNLOAD", "File saved at: " + file.getAbsolutePath());
                return file.getAbsolutePath(); // Returning the file path
            }
        } catch (IOException e) {
            Log.e("DOWNLOAD", "File write error: " + e.getMessage());
            return null;
        }
    }

    private void openFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Log.e("DOWNLOAD", "File not found: " + filePath);
            return;
        }

        Uri fileUri = FileProvider.getUriForFile(context, "pkg.vs.schoolsdemo.voicensapschoolsdemo.provider", file);
        String mimeType = getMimeType(filePath);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, mimeType);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("DOWNLOAD", "No app found to open file: " + e.getMessage());
        }
    }

    private String getMimeType(String filePath) {
        if (filePath.endsWith(".pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (filePath.endsWith(".xls")) return "application/vnd.ms-excel";
        if (filePath.endsWith(".xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (filePath.endsWith(".docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        return "*/*";
    }
}
