package pkg.vs.schoolsdemo.voicensapschoolsdemo.AWS;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import okhttp3.MediaType;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Interface.Voicesnapdemointerface;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VimsClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.rest.VoicesnapdemoapiClient;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;
import retrofit2.Call;
import retrofit2.Callback;

public class AwsUploadingPreSigned {
    String isBucket = "";
    public void getPreSignedUrl(String isFilePathUrl, Activity activity, UploadCallback uploadCallback) {

        String bucketPath = "";
        String isFileExtension = ".m4a";
        String currentDate = CurrentDatePicking.getCurrentDateTime();

        isBucket = AWSKeys.SCHOOL_CHIMES_COMMUNICATION;
        bucketPath = currentDate + "/" + "1";

        Log.d("isBucket", isBucket);
        Log.d("isFileExtension", isFileExtension);
        File isFilePth = new File(isFilePathUrl);

        String fileExtension = getFileExtension(isFilePth.getName());
        MediaType mediaType = null;

        try {
            mediaType = getMediaType(fileExtension);
            System.out.println("MediaType: " + mediaType);
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
        String baseURL = "https://api.schoolchimes.com/nodejs/api/MergedApi/";
        Log.d("baseURL", baseURL);
        VoicesnapdemoapiClient.changeApiBaseUrl(baseURL);

        String isFileName = getFileNameFromPath(isFilePathUrl);

        Voicesnapdemointerface apiService = VoicesnapdemoapiClient.getClient().create(Voicesnapdemointerface.class);
        Call<PreSignedUrl> call = apiService.getPreSignedUrl(isBucket, isFileName, bucketPath, String.valueOf(mediaType));

        call.enqueue(new Callback<PreSignedUrl>() {
            @Override
            public void onResponse(Call<PreSignedUrl> call, retrofit2.Response<PreSignedUrl> response) {
                Log.d("attendance:code-res", response.code() + " - " + response);

                if (response.isSuccessful() && response.body() != null) {
                    PreSignedUrl preSignedUrlResponse = response.body();
                    Log.d("PreSignedData", new Gson().toJson(preSignedUrlResponse));

                    if (preSignedUrlResponse.getStatus() == 1) {
                        Log.d("isSuccessFullUpload", "isSuccessFullUpload");
                        String presignedUrl = preSignedUrlResponse.getData().getPresignedUrl();
                        String isFileUrl = preSignedUrlResponse.getData().getFileUrl();
                        Log.d("presignedUrl", presignedUrl);

                        // Upload the file and get the upload response
                        isAwsUpload(presignedUrl, isFilePathUrl, isFileUrl, uploadCallback);

                    } else {
                        Log.d("isSuccessFullUpload", "isErrorUpload: " + preSignedUrlResponse.getMessage());
                        uploadCallback.onUploadError(preSignedUrlResponse.getMessage());
                        VimsClient.changeApiBaseUrl(Util_common.isVimesUrl);
                        VoicesnapdemoapiClient.changeApiBaseUrl(Util_common.isSchoolUrl);
                    }
                } else {
                    Toast.makeText(activity, "Check internet connection", Toast.LENGTH_SHORT).show();
                    String errorMessage = response.message(); // Get the error message from the response
                    Log.e("Response Error", errorMessage != null ? errorMessage : "Unknown error occurred");
                    uploadCallback.onUploadError(errorMessage);
                    VimsClient.changeApiBaseUrl(Util_common.isVimesUrl);
                    VoicesnapdemoapiClient.changeApiBaseUrl(Util_common.isSchoolUrl);
                }
            }

            @Override
            public void onFailure(Call<PreSignedUrl> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                Toast.makeText(activity, "Check internet connection", Toast.LENGTH_SHORT).show();
                uploadCallback.onUploadError(t.getMessage());
                VimsClient.changeApiBaseUrl(Util_common.isVimesUrl);
                VoicesnapdemoapiClient.changeApiBaseUrl(Util_common.isSchoolUrl);
            }
        });
    }

    public String getFileNameFromPath(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    private void isAwsUpload(String presignedUrl, String filePath, String isFileUploadUrl, UploadCallback uploadCallback) {

        byte[] imageData = getImageData(filePath); // Replace with the actual byte array of your image

        File isFilePth = new File(filePath);

        String fileExtension = getFileExtension(isFilePth.getName());

        MediaType mediaType = null;
        try {
            mediaType = getMediaType(fileExtension);
            System.out.println("MediaType: " + mediaType);
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }

        S3Uploader uploader = new S3Uploader();
        uploader.uploadImageToS3(presignedUrl, imageData, String.valueOf(mediaType), new S3Uploader.UploadCallback() {
            @Override
            public void onSuccess(String message) {
                Log.d("S3Upload", message);
                uploadCallback.onUploadSuccess(message, isFileUploadUrl);
                VimsClient.changeApiBaseUrl(Util_common.isVimesUrl);
                VoicesnapdemoapiClient.changeApiBaseUrl(Util_common.isSchoolUrl);
            }

            @Override
            public void onError(Exception error) {
                Log.e("S3Upload", "Error: " + error.getMessage(), error);
                VimsClient.changeApiBaseUrl(Util_common.isVimesUrl);
                VoicesnapdemoapiClient.changeApiBaseUrl(Util_common.isSchoolUrl);
            }
        });
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < fileName.length() - 1) {
            return fileName.substring(lastIndexOfDot + 1).toLowerCase();
        }
        return ""; // Return empty string if no extension found
    }

    private byte[] getImageData(String filePath) {
        File imageFile = new File(filePath);
        byte[] imageData = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imageData = Files.readAllBytes(imageFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public MediaType getMediaType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.parse("image/jpeg");
            case "png":
                return MediaType.parse("image/png");
            case "pdf":
                return MediaType.parse("application/pdf");
            case "mp3":
                return MediaType.parse("audio/mpeg");
            case "wav":
                return MediaType.parse("audio/wav");
            case "m4a":
                return MediaType.parse("audio/mp4");

            default:
                throw new UnsupportedOperationException(
                        "Unsupported file type: " + fileExtension
                );
        }
    }
}
