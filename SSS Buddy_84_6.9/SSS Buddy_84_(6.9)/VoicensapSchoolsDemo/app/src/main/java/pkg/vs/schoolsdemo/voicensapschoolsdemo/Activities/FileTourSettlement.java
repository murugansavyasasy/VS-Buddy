package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.imageListAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VimsInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.RealPathFile;
import retrofit2.Call;
import retrofit2.Callback;

public class FileTourSettlement extends AppCompatActivity {
    ImageView img_upload, card_upload, card_upload1, card_upload2, card_upload3, img_pdf;
    Button btn_choose, btn_submit;
    TextView displayname, txtcount;
    RelativeLayout rl_images;
    String idTourExpenses;
    String nameValue = "Tour";
    String resultMessage;
    String result;
    GridView grid;
    private static final int REQUEST = 1;
    private static final int SELECT_IMAGE = 2;
    private static final int SELECT_FILE = 3;
    String path;
    String pdfFilePath;
    String iduser;
    LinearLayout layout_pdf2;
    RecyclerView pdf_recyle;
    imageListAdapter mAdapter;
    LinearLayout pdf_layout;
    TextView PDF_1;

    String imageEncoded;
    List<String> imagesEncodedList;
    private ArrayList<String> imagePathList = new ArrayList<>();
    private PopupWindow pwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_tour_settlement);
        Intent i = getIntent();
        idTourExpenses = i.getStringExtra("idTourExpenses");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        img_upload = (ImageView) findViewById(R.id.img_upload);
        card_upload = (ImageView) findViewById(R.id.card_upload);
        card_upload1 = (ImageView) findViewById(R.id.card_upload1);
        card_upload2 = (ImageView) findViewById(R.id.card_upload2);
        card_upload3 = (ImageView) findViewById(R.id.card_upload3);
        btn_choose = (Button) findViewById(R.id.btn_change);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        displayname = (TextView) findViewById(R.id.display);
        img_pdf = (ImageView) findViewById(R.id.img_pdf);
        rl_images = (RelativeLayout) findViewById(R.id.rl_images);
        txtcount = (TextView) findViewById(R.id.txtcount);
        card_upload.setVisibility(View.VISIBLE);

        pdf_layout = (LinearLayout) findViewById(R.id.pdf_layout2);
        PDF_1 = (TextView) findViewById(R.id.pdf1);

        layout_pdf2 = (LinearLayout) findViewById(R.id.pdf_layout1);
        pdf_recyle = (RecyclerView) findViewById(R.id.list_pdf);

        iduser = SharedPreference_class.getUserid(FileTourSettlement.this);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.KITKAT) {
            if (checkIfAlreadyhavePermission()) {
            }
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((imagePathList.size() == 0)) {
                    String title = "Choose the Image or File to Upload";
                    alertDialog(title);
                } else {
                    fileUpload();
                }
            }
        });
        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbox();
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                imagePathList.clear();
                pdf_recyle.setAdapter(null);
                card_upload.setImageBitmap(null);
                card_upload1.setImageBitmap(null);
                card_upload2.setImageBitmap(null);
                card_upload3.setImageBitmap(null);
                txtcount.setVisibility(View.GONE);
                dialogbox();
            }
        });
        card_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageListPopup(v);
            }
        });
        card_upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageListPopup(v);
            }
        });
        card_upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageListPopup(v);
            }
        });
        card_upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageListPopup(v);
            }
        });
        txtcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageListPopup(v);
            }
        });

    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST);
            return false;
        }
        if (result1 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_IMAGE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_FILE);
            return false;
        }
        if (result2 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_IMAGE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("requestcode", String.valueOf(requestCode));

        if (requestCode == REQUEST && resultCode == RESULT_OK && null != data) {
            img_upload.setEnabled(false);
            btn_choose.setVisibility(View.VISIBLE);

            card_upload.setVisibility(View.VISIBLE);
            card_upload.setEnabled(true);
            card_upload1.setVisibility(View.GONE);
            card_upload2.setVisibility(View.GONE);
            card_upload3.setVisibility(View.GONE);
            rl_images.setVisibility(View.VISIBLE);
            txtcount.setVisibility(View.GONE);
            displayname.setVisibility(View.GONE);
            img_pdf.setVisibility(View.GONE);
//            Bundle extras = data.getExtras();
//            Bitmap photo = (Bitmap) extras.get("data");
//            card_upload.setImageBitmap(photo);


            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri mImageUri = getImageUri(getApplicationContext(), photo);

            Log.d("Uri", String.valueOf(mImageUri));
            //    path = RealPathFile.getPath(FileTourSettlement.this, mImageUri);
            path = getRealPathFromURIRealfilepath(mImageUri);
            // String path=getRealPathFromURI1(mImageUri);
            Log.d("File_path", path);
            imagePathList.add(path);
            pdfFilePath = path;
            //  img1.setImageBitmap(BitmapFactory.decodeFile(path));

            File file = new File(path);
            Uri imageUri = Uri.fromFile(file);
            Glide.with(this).load(imageUri).into(this.card_upload);


        } else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
            img_upload.setEnabled(false);
            btn_choose.setVisibility(View.VISIBLE);

            card_upload.setVisibility(View.VISIBLE);
            card_upload1.setVisibility(View.VISIBLE);
            card_upload2.setVisibility(View.VISIBLE);
            card_upload3.setVisibility(View.VISIBLE);
            rl_images.setVisibility(View.VISIBLE);

            displayname.setVisibility(View.GONE);
            img_pdf.setVisibility(View.GONE);


            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imagesEncodedList = new ArrayList<>();
            if (data.getClipData() != null) {
                btn_choose.setVisibility(View.VISIBLE);

                card_upload.setVisibility(View.VISIBLE);
                card_upload1.setVisibility(View.VISIBLE);
                card_upload2.setVisibility(View.VISIBLE);
                card_upload3.setVisibility(View.VISIBLE);
                rl_images.setVisibility(View.VISIBLE);
                txtcount.setVisibility(View.VISIBLE);

                displayname.setVisibility(View.GONE);
                img_pdf.setVisibility(View.GONE);


                int count = 0;
                ClipData mClipData = data.getClipData();
                ArrayList<Uri> mArrayUri = new ArrayList<>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    //    path = RealPathFile.getPath(FileTourSettlement.this, uri);
                    path = getRealPathFromURIRealfilepath(uri);
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    imagePathList.add(path);
                    pdfFilePath = path;
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    imagesEncodedList.add(imageEncoded);
                    cursor.close();
                    count = mClipData.getItemCount() - 4;
                    if (i == 0) {
                        card_upload.setEnabled(true);
                        card_upload.setVisibility(View.VISIBLE);
                        File file = new File(path);
                        Uri imageUri = Uri.fromFile(file);
                        Glide.with(this).load(imageUri).into(this.card_upload);
                    } else if (i == 1) {
                        card_upload1.setEnabled(true);
                        card_upload1.setVisibility(View.VISIBLE);
                        File file = new File(path);
                        Uri imageUri = Uri.fromFile(file);
                        Glide.with(this).load(imageUri).into(this.card_upload1);
                    } else if (i == 2) {
                        card_upload2.setEnabled(true);
                        card_upload2.setVisibility(View.VISIBLE);
                        File file = new File(path);
                        Uri imageUri = Uri.fromFile(file);
                        Glide.with(this).load(imageUri).into(this.card_upload2);
                    } else if (i == 3) {
                        card_upload3.setEnabled(true);
                        card_upload3.setVisibility(View.VISIBLE);
                        File file = new File(path);
                        Uri imageUri = Uri.fromFile(file);
                        Glide.with(this).load(imageUri).into(this.card_upload3);
                    } else {
                        if (count == 0) {
                            card_upload3.setEnabled(true);
                            card_upload3.setVisibility(View.VISIBLE);
                            File file = new File(path);
                            Uri imageUri = Uri.fromFile(file);
                            Glide.with(this).load(imageUri).into(this.card_upload3);
                        } else if (count > 0) {
                            String count1 = "+" + count;
                            txtcount.setText(String.valueOf(count1));

                        }

                    }

                }


                Log.v("LOG_TAG", "Selected Images" + mClipData.getItemCount());
            } else if (data.getData() != null) {
                img_upload.setEnabled(false);
                btn_choose.setVisibility(View.VISIBLE);

                Uri imageURI = data.getData();
                Log.d("URI", String.valueOf(imageURI));
                // path = RealPathFile.getPath(this, imageURI);
                path = getRealPathFromURIRealfilepath(imageURI);
                imagePathList.add(path);
                Log.d("ImagePath", path);
                Glide.with(getApplicationContext()).load(path).into(card_upload);

            }
        } else if (resultCode == RESULT_OK && requestCode == SELECT_FILE && null != data) {
            img_upload.setEnabled(false);
//            img_pdf.setVisibility(View.VISIBLE);
            displayname.setVisibility(View.VISIBLE);
            card_upload.setVisibility(View.GONE);
            card_upload1.setVisibility(View.GONE);
            card_upload2.setVisibility(View.GONE);
            card_upload3.setVisibility(View.GONE);
            rl_images.setVisibility(View.GONE);
            txtcount.setVisibility(View.GONE);
            btn_choose.setVisibility(View.VISIBLE);


            if (data.getClipData() != null) {


                ClipData mClipData = data.getClipData();

                final ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

                imagePathList.clear();

                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    path = RealPathFile.getPath(this, uri);
                    imagePathList.add(path);
                    pdf_layout.setVisibility(View.GONE);
                    mAdapter = new imageListAdapter(FileTourSettlement.this, imagePathList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    pdf_recyle.setLayoutManager(mLayoutManager);
                    pdf_recyle.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();


                }
            } else if (data.getData() != null) {
                img_upload.setEnabled(false);
                btn_choose.setVisibility(View.VISIBLE);

                Uri pdfuri = data.getData();
                //    path = RealPathFile.getPath(this, pdfuri);
                path = getPDFPath(pdfuri);
                Log.d("PDF_URI", path);
                imagePathList.add(path);
                rl_images.setVisibility(View.GONE);
                layout_pdf2.setVisibility(View.GONE);
                pdf_layout.setVisibility(View.VISIBLE);
                PDF_1.setText(path);
            }
        }
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


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Uri getImageUri(Context applicationContext, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    private void imageListPopup(View v) {


        final LayoutInflater inflater = (LayoutInflater) FileTourSettlement.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_gallery, null);

        pwindow = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        pwindow.setContentView(layout);
        pwindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);

        RecyclerView imageRecyclerview = (RecyclerView) layout.findViewById(R.id.recycler_view);

        mAdapter = new imageListAdapter(FileTourSettlement.this, imagePathList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        imageRecyclerview.setLayoutManager(mLayoutManager);
        imageRecyclerview.setItemAnimator(new DefaultItemAnimator());
        imageRecyclerview.setAdapter(mAdapter);
        imageRecyclerview.getRecycledViewPool().setMaxRecycledViews(0, 80);


    }

    private void dialogbox() {
        final CharSequence[] items = {"Take Photo", "Choose Image From Gallery", "Choose PDF File"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FileTourSettlement.this);
        builder.setTitle("Add Photo/file!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    layout_pdf2.setVisibility(View.GONE);
                    rl_images.setVisibility(View.VISIBLE);


                    Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    imagePathList.clear();
                    startActivityForResult(camera, REQUEST);
                } else if (items[item].equals("Choose Image From Gallery")) {
                    layout_pdf2.setVisibility(View.GONE);
                    rl_images.setVisibility(View.VISIBLE);
                    String FileImage = "Pick";
                    SharedPreference_class.putSh_Image(FileTourSettlement.this, FileImage);


                    Intent intent;
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(intent, SELECT_IMAGE);
//                    intent.setType("image/*");
                    imagePathList.clear();
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

                } else if (items[item].equals("Choose PDF File")) {
                    layout_pdf2.setVisibility(View.VISIBLE);
                    rl_images.setVisibility(View.GONE);

                    String PDF_File = "PDF_file";
                    SharedPreference_class.putSh_Image(FileTourSettlement.this, PDF_File);

                    Intent intent = new Intent();
                    imagePathList.clear();
                    intent.setType("application/pdf");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, SELECT_FILE);

                }


            }

        });
        builder.show();
    }

    private void fileUpload() {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Intent i = getIntent();
        idTourExpenses = i.getStringExtra("idTourExpenses");

        VimsInterface apiService = VimsClient.getClient().create(VimsInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idValue", idTourExpenses);
        jsonObject.addProperty("nameValue", nameValue);
        jsonObject.addProperty("processby", iduser);


        Log.d("fileupload:req", jsonObject.toString());

//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        Log.d("multipart", String.valueOf(file));
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[imagePathList.size()];
        for (int index = 0; index < imagePathList.size(); index++) {
            File file1 = new File(imagePathList.get(index));
            Log.d("index", String.valueOf(file1));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("Info", file1.getName(), surveyBody);
        }
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, jsonObject.toString());


        Call<JsonArray> call = apiService.Uploadfile(requestBody, surveyImagesParts);


        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, retrofit2.Response<JsonArray> response) {

                Log.d("Response", response.toString());
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
                try {
                    Log.d("FileResponse:Res", response.toString());
                    int statusCode = response.code();
                    Log.d("Status Code - Response", statusCode + " - " + response.body());
                    String strResponse = response.body().toString();

                    JSONArray jsonArrayorgList = new JSONArray(strResponse);

                    if (jsonArrayorgList.length() > 0) {
                        JSONObject temp;
                        temp = jsonArrayorgList.getJSONObject(0);
                        result = temp.getString("result");
                        resultMessage = temp.getString("resultMessage");
                        if (result.equals("1")) {
                            alertDialog1(resultMessage);
                        } else {
                            alertDialog1(resultMessage);
                        }
                    }
                    Log.d("Server Response", strResponse);

                } catch (Exception e) {
                    Log.e("Response Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
                Log.e("Response Failure", t.getMessage());
                showToast("Server Connection Failed");
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void alertDialog1(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(FileTourSettlement.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (result.equals("1")) {
                    Intent i = new Intent(FileTourSettlement.this, TourSettlement.class);

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(i);
                    finish();
                }
            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }

    private void alertDialog(String msg) {
        final AlertDialog.Builder dlg = new AlertDialog.Builder(FileTourSettlement.this);
        dlg.setTitle("Alert");
        dlg.setMessage(msg);
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setCancelable(false);
        dlg.create();
        dlg.show();
    }
}







