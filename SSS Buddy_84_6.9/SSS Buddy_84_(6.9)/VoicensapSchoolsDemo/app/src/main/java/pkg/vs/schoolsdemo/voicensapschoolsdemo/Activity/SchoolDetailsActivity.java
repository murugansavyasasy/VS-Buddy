package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.SchoolImageAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VoicesnapcommInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SchoolModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.CommonUtil;

public class SchoolDetailsActivity extends AppCompatActivity {

    LinearLayout containerSchools;
    Button btnAddSchool, btnSubmit;
    View selectedSchoolView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);

        getSupportActionBar().setTitle("School Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        containerSchools = findViewById(R.id.containerSchools);
        btnAddSchool = findViewById(R.id.btnAddSchool);
        btnSubmit = findViewById(R.id.btnSubmit);
        addSchoolBlock();

        btnAddSchool.setOnClickListener(v -> {
            if (validateCurrentBlocks()) {
                addSchoolBlock();
            }
        });
        btnSubmit.setOnClickListener(v -> submitAllSchools());

    }

    private void addSchoolBlock() {

        View schoolView = getLayoutInflater().inflate(R.layout.item_school_details, containerSchools, false);
        ImageView ivRemove = schoolView.findViewById(R.id.ivRemove);
        Button btnAttach = schoolView.findViewById(R.id.btnAttach);
        RecyclerView rvImages = schoolView.findViewById(R.id.rvImages);
        rvImages.setLayoutManager(new GridLayoutManager(this, 3));

        int spanCount = 3;
        int spacingInDp = 8;
        float scale = getResources().getDisplayMetrics().density;
        int spacingInPixels = (int) (spacingInDp * scale + 0.5f);

        rvImages.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int column = position % spanCount;
                outRect.left = spacingInPixels - column * spacingInPixels / spanCount;
                outRect.right = (column + 1) * spacingInPixels / spanCount;
                outRect.top = spacingInPixels;
                outRect.bottom = spacingInPixels;
            }
        });


        ArrayList<Uri> imageList = new ArrayList<>();
        SchoolImageAdapter imageAdapter = new SchoolImageAdapter(imageList);
        rvImages.setAdapter(imageAdapter);

        schoolView.setTag(R.id.tag_image_list, imageList);
        schoolView.setTag(R.id.tag_image_adapter, imageAdapter);

        ivRemove.setVisibility(containerSchools.getChildCount() > 0 ? View.VISIBLE : View.GONE);
        ivRemove.setOnClickListener(v -> {
            containerSchools.removeView(schoolView);
            updateRemoveIcons();
        });

        btnAttach.setOnClickListener(v -> {
            selectedSchoolView = schoolView;
            openGalleryForMultiple();
        });

        containerSchools.addView(schoolView, containerSchools.getChildCount() - 2);
        updateRemoveIcons();
    }


        private boolean validateCurrentBlocks() {
            boolean isValid = true;

            for (int i = 0; i < containerSchools.getChildCount(); i++) {
                View schoolView = containerSchools.getChildAt(i);

                EditText name = schoolView.findViewById(R.id.etSchoolName);
                EditText address = schoolView.findViewById(R.id.etAddress);
                EditText parentNum = schoolView.findViewById(R.id.etParentNumber);

                if (name == null || address == null || parentNum == null) continue;

                String sName = name.getText().toString().trim();
                String sAddress = address.getText().toString().trim();
                String sPhone = parentNum.getText().toString().trim();

                if (sName.isEmpty()) {
                    name.setError("School name is required");
                    if (isValid) name.requestFocus();
                    isValid = false;
                }

                if (sAddress.isEmpty()) {
                    address.setError("Address is required");
                    if (isValid) address.requestFocus();
                    isValid = false;
                }

                if (sPhone.isEmpty()) {
                    parentNum.setError("Phone number is required");
                    if (isValid) parentNum.requestFocus();
                    isValid = false;
                } else if (sPhone.length() != 10) {
                    parentNum.setError("Enter valid 10-digit number");
                    if (isValid) parentNum.requestFocus();
                    isValid = false;
                }
            }

            return isValid;
        }

    private void submitAllSchools() {

        CommonUtil.schoolList.clear();

        for (int i = 0; i < containerSchools.getChildCount(); i++) {
            View schoolView = containerSchools.getChildAt(i);

            EditText name = schoolView.findViewById(R.id.etSchoolName);
            EditText address = schoolView.findViewById(R.id.etAddress);
            EditText parentNum = schoolView.findViewById(R.id.etParentNumber);

            if (name == null || address == null || parentNum == null) continue;

            String sName = name.getText().toString().trim();
            String sAddress = address.getText().toString().trim();
            String sPhone = parentNum.getText().toString().trim();

            if (sName.isEmpty()) name.setError("School name is required");
            if (sAddress.isEmpty()) address.setError("Address is required");
            if (sPhone.isEmpty()) parentNum.setError("Phone number is required");
            else if (sPhone.length() != 10) parentNum.setError("Enter valid 10-digit number");


            ArrayList<Uri> uriList = (ArrayList<Uri>) schoolView.getTag(R.id.tag_image_list);
            ArrayList<String> pathList = new ArrayList<>();
            if (uriList != null) {
                for (Uri uri : uriList) {
                    String safePath = getRealFilePath(this, uri);//path
                    if (safePath != null) {
                        pathList.add(safePath);//Add path
                    }
                }
            }

            SchoolModel school = new SchoolModel(sName, sAddress, sPhone, pathList);
            CommonUtil.schoolList.add(school);
        }

        JSONArray schoolArray = new JSONArray();
        for (SchoolModel s : CommonUtil.schoolList) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("schoolName", s.schoolName);
                obj.put("schoolAddress", s.schoolAddress);
                obj.put("schoolPhone", s.schoolPhone);
                obj.put("FilePath", new JSONArray(s.filePaths));
                schoolArray.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("SchoolArray", schoolArray.toString());
        try {
            Log.d("SchoolArrayJSON", schoolArray.toString(4));
        } catch (JSONException e) {
            Log.d("SchoolArrayJSON", schoolArray.toString());
        }
    }



    private void updateRemoveIcons() {

        int totalCards = containerSchools.getChildCount() - 2;//(Add, Submit)

        for (int i = 0; i < totalCards; i++) {
            View schoolView = containerSchools.getChildAt(i);
            ImageView ivRemove = schoolView.findViewById(R.id.ivRemove);
            ivRemove.setVisibility(totalCards > 1 ? View.VISIBLE : View.GONE);
        }
    }

    private void openGalleryForMultiple() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Files"), 101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && selectedSchoolView != null) {

            RecyclerView rvImages = selectedSchoolView.findViewById(R.id.rvImages);
            ArrayList<Uri> list = (ArrayList<Uri>) selectedSchoolView.getTag(R.id.tag_image_list);
            SchoolImageAdapter adapter = (SchoolImageAdapter) selectedSchoolView.getTag(R.id.tag_image_adapter);

            if (list == null) {
                list = new ArrayList<>();
                selectedSchoolView.setTag(R.id.tag_image_list, list);
            }

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    list.add(uri);
                }
            } else if (data.getData() != null) {
                list.add(data.getData());
            }
            rvImages.setVisibility(View.VISIBLE);

            adapter.notifyDataSetChanged();
        }
    }



    public static String getRealFilePath(Context context, Uri uri) {
        if (uri == null) return null;
        String filePath = null;

        try {
            if ("content".equals(uri.getScheme())) {

                String displayName = null;
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1)
                        displayName = cursor.getString(nameIndex);
                }
                if (cursor != null) cursor.close();

                if (displayName == null)
                    displayName = "file_" + System.currentTimeMillis();

                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    File cacheDir = new File(context.getCacheDir(), "school_files");
                    if (!cacheDir.exists()) cacheDir.mkdirs();

                    File file = new File(cacheDir, displayName);
                    FileOutputStream out = new FileOutputStream(file);

                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    inputStream.close();

                    filePath = file.getAbsolutePath();
                }
            } else if ("file".equals(uri.getScheme())) {
                filePath = uri.getPath();
            }
        } catch (Exception e) {
            Log.e("FilePath", "Failed: " + e.getMessage());
        }

        return filePath;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
