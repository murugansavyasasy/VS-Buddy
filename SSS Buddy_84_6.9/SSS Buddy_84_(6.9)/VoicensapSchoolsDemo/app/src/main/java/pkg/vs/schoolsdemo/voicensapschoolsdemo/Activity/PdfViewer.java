package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.WindowInsetsControllerCompat;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdfViewer extends AppCompatActivity {

    PDFView pdfView;
    static ProgressDialog mProgressDialog;
    public static String pdfFilePath;
    String FolderName = "//SchoolDocs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfView = findViewById(R.id.pdfView);


        final File dir;
        if (Build.VERSION_CODES.R > Build.VERSION.SDK_INT) {
            dir = new File(Environment.getExternalStorageDirectory().getPath()
                    + FolderName);
            Log.d("Getpath", dir.toString());

        } else {

            dir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath()
                    + FolderName);
            Log.d("Getpath", dir.toString());
        }
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Dir: " + dir);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);


        switch (Util_common.Doc_Type) {
            case "pdf": {

//                File futureStudioIconFile = new File(dir, Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".pdf");//"Hai.mp3"
//                if (futureStudioIconFile.exists()) {
//                    pdfView.setVisibility(View.VISIBLE);
//                    pdfView.fromFile(new File(futureStudioIconFile.getPath()))
//                            .load();
//                } else {
                downLoadFeeReceipt(Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".pdf", FolderName);
//                }
//                break;
            }
            case "docx": {
//                File futureStudioIconFile = new File(dir, Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".docx");//"Hai.mp3"
//                if (futureStudioIconFile.exists()) {
//                    openWordFile(futureStudioIconFile.getPath());
//                } else {
                downLoadFeeReceipt(Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".docx", FolderName);
//                }
//                break;
            }
            case "pptx": {
//                File futureStudioIconFile = new File(dir, Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".pptx");//"Hai.mp3"
//                if (futureStudioIconFile.exists()) {
//                    openPowerPointFile(futureStudioIconFile.getPath());
//                } else {
                downLoadFeeReceipt(Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".pptx", FolderName);
//                }
//                break;
            }
            case "xlsx": {
//                File futureStudioIconFile = new File(dir, Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".xlsx");//"Hai.mp3"
//                if (futureStudioIconFile.exists()) {
//                    openElsxFile(futureStudioIconFile.getPath());
//                } else {
                downLoadFeeReceipt(Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".xlsx", FolderName);
//                }
//                break;
            }
            case "xls": {
//                File futureStudioIconFile = new File(dir, Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".xls");//"Hai.mp3"
//                if (futureStudioIconFile.exists()) {
//                    openExcelFile(futureStudioIconFile.getPath());
//                } else {
                downLoadFeeReceipt(Util_common.Doc_ID + Util_common.Doc_Title + "_Circular" + ".xls", FolderName);
//                }
//                break;
            }
        }
    }

    private void openWordFile(String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/msword");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlartDiolog(PdfViewer.this, "Please install document viewer application!");
            e.printStackTrace();
        }
    }

    private void openExcelFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Log.e("EXCEL_OPEN", "File not found: " + filePath);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        intent.setDataAndType(uri, "application/vnd.ms-excel"); // For both xls & xlsx
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app found to open Excel files!", Toast.LENGTH_SHORT).show();
        }
    }


    private void openPowerPointFile(String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlartDiolog(PdfViewer.this, "Please install ppt file viewer application!");
            e.printStackTrace();
        }
    }


    private void AlartDiolog(Activity activity, String Title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(Title);
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void downLoadFeeReceipt(String filename, String folder) {

        ProgressDialog mProgressDialog;
        mProgressDialog = new ProgressDialog(PdfViewer.this);
        mProgressDialog.setIndeterminate(true);

        mProgressDialog.setMessage("Opening...");

        mProgressDialog.setCancelable(false);
        if (!PdfViewer.this.isFinishing()) {

            mProgressDialog.show();
            VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
            Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlAsync(Util_common.Doc_URL);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d("DOWNLOADING...", "server contacted and has file");

                        new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                mProgressDialog.dismiss();
                                boolean writtenToDisk = writeResponseBodyToDisk(response.body(), folder, filename, PdfViewer.this);
                                Log.d("DOWNLOADING...", "file download was a success? " + writtenToDisk);
                                return writtenToDisk;
                            }

                            @Override
                            protected void onPostExecute(Boolean status) {
                                super.onPostExecute(status);
                                if (status) {

                                    File file = new File(getFilesDir(), "SchoolDocs/" + filename);
                                    Log.d("DOWNLOADING...", "Downloaded file path: " + file.getAbsolutePath());
                                    mProgressDialog.dismiss();
                                    switch (Util_common.Doc_Type) {
                                        case "pdf":
                                            pdfView.setVisibility(View.VISIBLE);
                                            Log.d("pdfFilePath", pdfFilePath);
                                            pdfView.fromFile(new File(pdfFilePath))
                                                    .load();
                                            break;
                                        case "pptx":
                                            openPowerPointFile(pdfFilePath);
                                            break;
                                        case "xls":
                                            openExcelFile(pdfFilePath);
                                            break;
                                        case "xlsx":
                                            openExcelFile(pdfFilePath);
                                            break;
                                        case "docx":
                                            openWordFile(pdfFilePath);
                                            break;
                                    }
                                }
                            }
                        }.execute();
                    } else {
                        Log.d("DOWNLOADING...", "server contact failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    Log.e("DOWNLOADING...", "error: " + t.toString());
                }
            });
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String folder, String filename, Context context) {
        try {
            // Define internal storage path
            File dir = new File(context.getFilesDir() + "/SchoolDocs");  // Internal storage path

            if (dir.exists()) {
                boolean deleted = dir.delete();
                Log.d("DOWNLOAD", "Old file deleted: " + deleted);
            }
            if (!dir.exists()) {
                boolean isCreated = dir.mkdirs();  // Create folder if it doesn't exist
                Log.d("DOWNLOADING...", "Directory created: " + isCreated);
            }

            // Define file path inside SchoolDocs
            File file = new File(dir, filename);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                pdfFilePath = file.getPath();

                outputStream.flush();

                // Print the file path
                Log.d("DOWNLOADING...", "File saved at: " + file.getAbsolutePath());

                return true;
            } catch (IOException e) {
                Log.e("DOWNLOADING...", "File write error: " + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            Log.e("DOWNLOADING...", "File creation error: " + e.getMessage());
            return false;
        }
    }
}