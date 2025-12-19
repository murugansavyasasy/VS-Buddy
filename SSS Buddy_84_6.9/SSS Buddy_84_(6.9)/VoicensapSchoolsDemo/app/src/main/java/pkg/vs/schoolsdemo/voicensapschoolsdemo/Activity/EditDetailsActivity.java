package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapters.CircularEditableAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.VoicesnapcommInterface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AttachmentItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.AttachmentResponse;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.CircularItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.FilePathItem;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapcommApiClient;
import retrofit2.Call;

public class EditDetailsActivity extends AppCompatActivity {

    private static final String TAG = "EditDetailsActivity";
    RecyclerView rvEditSchools;
    ArrayList<CircularItem> circularList = new ArrayList<>();
    CircularEditableAdapter adapter;
    private int selectedPosition = -1;
    private static final int REQUEST_CODE_ATTACH = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        getSupportActionBar().setTitle("Edit Circulars");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvEditSchools = findViewById(R.id.rvEditSchools);
        rvEditSchools.setLayoutManager(new LinearLayoutManager(this));
        loadSchoolDetails();

        adapter = new CircularEditableAdapter(circularList,
                position -> {
                    circularList.remove(position);
                    adapter.notifyItemRemoved(position);
                },
                position -> {
                    selectedPosition = position;
                    openGallery();
                });

        rvEditSchools.setAdapter(adapter);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        if (btnUpdate != null) {
            btnUpdate.setOnClickListener(v -> {
                printUpdatedData();
            });
        }
    }

    private void loadSchoolDetails() {
        VoicesnapcommInterface api = VoicesnapcommApiClient.getClient().create(VoicesnapcommInterface.class);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdGFmZl9pZCI6IjEwMDc3NjE3Iiwic2Nob29sX2lkIjoiNzA0MyIsImlhdCI6MTc2NTI1NzM5OH0.oYuq8ORNN4z3XbDTJWFhLvbwjzJ4fON2ixEf3aNCXAM";
        Log.d("Authorization", "Token: " + token);
        String fullUrl = VoicesnapcommApiClient.BASE_URL + "attachment_report";
        Log.d("URL", "API URL: " + fullUrl);

        Call<AttachmentResponse> call = api.getAttachmentReport(token);
        call.enqueue(new retrofit2.Callback<AttachmentResponse>() {
            @Override
            public void onResponse(Call<AttachmentResponse> call, retrofit2.Response<AttachmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String APIJson = new GsonBuilder().setPrettyPrinting().create().toJson(response.body());
                    Log.d(TAG, "APIJson:\n" + APIJson);
                    AttachmentResponse res = response.body();
                    Log.d("API Data", "API Data: " + new Gson().toJson(res.data));
                    circularList.clear();

                    for (AttachmentItem item : res.data) {
                        ArrayList<CircularItem.FileItem> files = new ArrayList<>();
                        for (FilePathItem f : item.file_path) {
                            files.add(new CircularItem.FileItem(f.url, f.type));//Convert into your model
                        }
                        circularList.add(new CircularItem(item.title, item.description, files));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "API Failed, code: " + response.code());
                    Toast.makeText(EditDetailsActivity.this, "API Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AttachmentResponse> call, Throwable t) {
                Toast.makeText(EditDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void printUpdatedData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String updatedJson = gson.toJson(circularList);
        Log.d("Updated_Data", updatedJson);
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), REQUEST_CODE_ATTACH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ATTACH && resultCode == RESULT_OK && data != null && selectedPosition != -1) {

            ArrayList<FilePathItem> newFileList = new ArrayList<>();
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    String path = getRealFilePath(this, uri);
                    if (path != null) {
                        String type = getFileType(new File(path).getName());
                        newFileList.add(new FilePathItem(path, type));
                    }
                }
            } else if (data.getData() != null) {
                String path = getRealFilePath(this, data.getData());
                if (path != null) {
                    String type = getFileType(new File(path).getName());
                    newFileList.add(new FilePathItem(path, type));
                }
            }

            if (newFileList.isEmpty()) {
                selectedPosition = -1;
                return;
            }

            CircularItem item = circularList.get(selectedPosition);
            Log.d("Attach"," Current filePaths :" +item.filePaths.size());
            for (FilePathItem f : newFileList) {
                item.filePaths.add(new CircularItem.FileItem(f.url, f.type));
                Log.d("Attach","Added filePaths :" + item.filePaths.size());
            }

            RecyclerView.ViewHolder vh = rvEditSchools.findViewHolderForAdapterPosition(selectedPosition);
            if (vh instanceof CircularEditableAdapter.VH) {
                CircularEditableAdapter.VH holder = (CircularEditableAdapter.VH) vh;
                if (holder.imageAdapter != null) {
                    ArrayList<FilePathItem> updatedList = new ArrayList<>();
                    for (CircularItem.FileItem fi : item.filePaths) {
                        updatedList.add(new FilePathItem(fi.url, fi.type));
                    }
                    Log.d("Attach", "Calling imageAdapter :" + updatedList.size());
                    holder.imageAdapter.updateList(updatedList);
                }else {
                    Log.d("Attach", "ViewHolder NOT found for position " + selectedPosition);
                }

            }

            selectedPosition = -1;
        }
    }


    public static String getRealFilePath(Context context, Uri uri) {
        if (uri == null) return null;
        String filePath = null;
        try {
            if ("content".equals(uri.getScheme())) {
                String displayName = null;
                try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (nameIndex != -1) displayName = cursor.getString(nameIndex);
                    }
                }
                if (displayName == null) displayName = "img_" + System.currentTimeMillis() + ".jpg";

                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    File cacheDir = new File(context.getCacheDir(), "circular_images");
                    if (!cacheDir.exists()) cacheDir.mkdirs();
                    File file = new File(cacheDir, displayName);
                    FileOutputStream outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.close();
                    inputStream.close();
                    filePath = file.getAbsolutePath();
                }
            } else if ("file".equals(uri.getScheme())) {
                filePath = uri.getPath();
            }
        } catch (Exception e) {
            Log.e(TAG, "Image save failed: " + e.getMessage());
        }
        return filePath;
    }

    private String getFileType(String fileName) {
        fileName = fileName.toLowerCase();

        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))
            return "IMAGE";
        if (fileName.endsWith(".mp4") || fileName.endsWith(".mov") || fileName.endsWith(".mkv"))
            return "VIDEO";
        if (fileName.endsWith(".pdf"))
            return "PDF";
        if (fileName.endsWith(".mp3") || fileName.endsWith(".wav") ||
                fileName.endsWith(".aac") || fileName.endsWith(".m4a") ||
                fileName.endsWith(".ogg"))
            return "AUDIO";
        if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))
            return "EXCEL";
        if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx"))
            return "PPT";
        if (fileName.endsWith(".doc") || fileName.endsWith(".docx"))
            return "DOC";
        return "OTHER";
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}