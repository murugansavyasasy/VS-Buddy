package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;


import static com.google.android.material.internal.ContextUtils.getActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.GalleryAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
//import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.RealPathFile;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.RealPathFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FileUpload_localConvey extends AppCompatActivity {
    ImageView img_upload;
    ImageView imageview1, imageview2, imageview3, imageview4;
    TextView PDF_1;
    FrameLayout clickImage;
    TextView text_image, txt_clickImage;
    PopupWindow popupWindow;
    ImageView popclose;
    //    String FileImage="Pick";
//    String PDF_File="PDF_fle";
    File outputDir;
    File PDFTempFileWrite;
    Uri imageURI;


    LinearLayout image_layout, pdf_layout;
    LinearLayout layout_pdf2;
    private int PICK_IMAGE_MULTIPLE = 1, CAMERA = 2;

    private static final int PICKFILE_RESULT_CODE = 8778;

    Button btn_submit, btn_change;
    String iduser, idLocalExpense;
    RecyclerView recyclerView;
    RecyclerView pdf_recyle;
    LinearLayout imageupload;
    String path;
    private static final String TAG = FileUpload_localConvey.class.getSimpleName();

    GalleryAdapter imageAdapter;
    ArrayList<String> imagelist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileupload);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.KITKAT) {
            if (requestMultiplePermissions()) {
            }
        }
//        requestMultiplePermissions();

        img_upload = (ImageView) findViewById(R.id.upload_img);
        imageview1 = (ImageView) findViewById(R.id.image1);
        imageview2 = (ImageView) findViewById(R.id.image2);
        imageview3 = (ImageView) findViewById(R.id.image3);
        imageview4 = (ImageView) findViewById(R.id.image4);
        image_layout = (LinearLayout) findViewById(R.id.image_layout);
        clickImage = (FrameLayout) findViewById(R.id.click_img);
        text_image = (TextView) findViewById(R.id.text_number);
        btn_submit = (Button) findViewById(R.id.btn_Upload);
        btn_change = (Button) findViewById(R.id.btn_change1);
        imageupload = (LinearLayout) findViewById(R.id.ImageUpload);
        txt_clickImage = (TextView) findViewById(R.id.txt_clickImage);


        //pdf_layout
        //single
        pdf_layout = (LinearLayout) findViewById(R.id.pdf_layout2);
        PDF_1 = (TextView) findViewById(R.id.pdf1);

        //listview
        layout_pdf2 = (LinearLayout) findViewById(R.id.pdf_layout1);
        pdf_recyle = (RecyclerView) findViewById(R.id.list_pdf);


        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagelist.clear();
                pdf_recyle.setAdapter(null);
                imageview1.setImageBitmap(null);
                imageview2.setImageBitmap(null);
                imageview3.setImageBitmap(null);
                imageview4.setImageBitmap(null);
                text_image.setVisibility(View.GONE);

                showPictureDialog();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((imagelist.size() == 0)) {
                    String title = "Choose the Image or File to Upload";
                    alert1(title);
                } else {
                    UploadImageFile();
                }
//                UploadImageFile();
            }
        });

        iduser = SharedPreference_class.getUserid(FileUpload_localConvey.this);
        Log.d("iduser", iduser);

        idLocalExpense = SharedPreference_class.getShIdlocalexpense(FileUpload_localConvey.this);


    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(FileUpload_localConvey.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select Image from Gallery",
                // "Capture Image from Camera",
                "Choose PDF file"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        layout_pdf2.setVisibility(View.GONE);
                        image_layout.setVisibility(View.VISIBLE);

                        String FileImage = "Pick";
                        SharedPreference_class.putSh_Image(FileUpload_localConvey.this, FileImage);
                        choosePhotoFromGallary();
                        break;

                    case 1:
//                        layout_pdf2.setVisibility(View.GONE);
//                        image_layout.setVisibility(View.VISIBLE);
//
//                        takePhotoFromCamera();
//                        break;

                        image_layout.setVisibility(View.GONE);
                        layout_pdf2.setVisibility(View.VISIBLE);
                        String PDF_File = "PDF_file";
                        SharedPreference_class.putSh_Image(FileUpload_localConvey.this, PDF_File);
                        choosefilePDF();
                        break;

//                    case 2:
//                        image_layout.setVisibility(View.GONE);
//                        layout_pdf2.setVisibility(View.VISIBLE);
//                        String PDF_File = "PDF_file";
//                        SharedPreference_class.putSh_Image(FileUpload_localConvey.this, PDF_File);
//
//                        choosefilePDF();
//                        break;

                }
            }
        });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        imagelist.clear();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void takePhotoFromCamera() {
        imagelist.clear();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosefilePDF() {
        imagelist.clear();
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a PDF "), PICKFILE_RESULT_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == Activity.RESULT_OK && null != data) {
            img_upload.setEnabled(false);
            btn_change.setVisibility(View.VISIBLE);


            if (data.getData() != null) {

                img_upload.setEnabled(false);
                btn_change.setVisibility(View.VISIBLE);
                Uri imageURI = data.getData();
                path = getRealPathFromURIRealfilepath(imageURI);
                imagelist.add(path);
                Glide.with(getApplicationContext()).load(path).into(imageview1);

            }
        } else if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK && data != null) {
            img_upload.setEnabled(false);
            btn_change.setVisibility(View.VISIBLE);
            if (data.getData() != null) {
                img_upload.setEnabled(false);
                btn_change.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                path = getPDFPath(uri);
                imagelist.add(path);
                image_layout.setVisibility(View.GONE);
                pdf_layout.setVisibility(View.VISIBLE);
                PDF_1.setText(path);
            }
        }
    }


    public String getRealPathFromURIRealfilepath(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));

        File file = new File(getFilesDir(), name);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();


            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getAbsolutePath());

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getAbsolutePath();
    }

    public String getPDFPath(Uri uri) {
        String absolutePath = "";
        try {
            InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
            byte[] pdfInBytes = new byte[inputStream.available()];
            inputStream.read(pdfInBytes);
            int offset = 0;
            int numRead = 0;
            while (offset < pdfInBytes.length && (numRead = inputStream.read(pdfInBytes, offset, pdfInBytes.length - offset)) >= 0) {
                offset += numRead;
            }

            String mPath = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mPath = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + ".pdf";
            } else {
                mPath = Environment.getExternalStorageDirectory().toString() + ".pdf";
            }

            File pdfFile = new File(mPath);
            OutputStream op = new FileOutputStream(pdfFile);
            op.write(pdfInBytes);
            absolutePath = pdfFile.getPath();

        } catch (Exception ae) {
            ae.printStackTrace();
        }
        return absolutePath;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
                imagelist.add(path);
            }
        }
        return path;
    }


    private boolean requestMultiplePermissions() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {
                    // show alert dialog navigating to Settings
                    //openSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void UploadImageFile() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(" Loading");
        mProgressDialog.setMessage(" Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        if (isNetworkConnected()) {


            VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);

            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("idValue", idLocalExpense);
            jsonObj.addProperty("nameValue", "Local");
            jsonObj.addProperty("processby", iduser);

            Log.d("fileupload:req", jsonObj.toString());


            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, jsonObj.toString());

            MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[imagelist.size()];
            for (int index = 0; index < imagelist.size(); index++) {
                Log.d("File_path", String.valueOf((imagelist.size())));

                File file = new File(imagelist.get(index));
                RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                surveyImagesParts[index] = MultipartBody.Part.createFormData("pdf", file.getName(), surveyBody);

            }

            Call<JsonArray> call = apiService.Upload_Image(requestBody, surveyImagesParts);

            call.enqueue(new Callback<JsonArray>() {

                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    try {
                        if (mProgressDialog.isShowing()) mProgressDialog.dismiss();


                        Log.d("URL", String.valueOf(response.code()));
                        Log.d("customer:code-res", response.code() + " - " + response);

                        if (response.code() == 200 || response.code() == 201) {
                            Log.d("response", response.body().toString());

                            JSONArray Array = new JSONArray(response.body().toString());
                            if (Array.length() > 0) {
                                for (int i = 0; i < Array.length(); i++) {
                                    JSONObject object1 = Array.getJSONObject(i);

                                    String Msg = object1.getString("resultMessage");
                                    Log.d("Message", Msg);
                                    String result = object1.getString("result");

                                    if (Integer.parseInt(object1.getString("result")) == 1) {
                                        alert(Msg);
                                    } else {
                                        alert1(Msg);
                                    }
                                }
                            } else {
                                alert1("No Records has found");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
//                mProgressDialog.dismiss();
                    alert("Server Connection Failed");
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            alert1("Check Internet Connection");
        }
    }

    public void alert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUpload_localConvey.this);
        builder.setTitle(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(FileUpload_localConvey.this, Report.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);
            }
        });

        builder.create().show();
    }

    public void alert1(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FileUpload_localConvey.this);

        builder.setTitle(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
//        finish();
        return true;
    }

}
