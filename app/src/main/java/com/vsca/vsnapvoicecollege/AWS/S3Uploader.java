package com.vsca.vsnapvoicecollege.AWS;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public
class S3Uploader {

    private static final String TAG = "S3Uploader";

    private Context context;
    private TransferUtility transferUtility;
    public S3UploadInterface s3UploadInterface;

    public S3Uploader(Context context) {
        this.context = context;
        transferUtility = AmazonUtil.getTransferUtility(context);

    }

    public void initUpload(String filePath, String contenttype,String collegeid,String fileNameDateTime) {
        File file = new File(filePath);
        Log.d("fileinitupload", String.valueOf(file));

        Log.d("contenttype", contenttype);
        ObjectMetadata myObjectMetadata = new ObjectMetadata();
        myObjectMetadata.setContentType(contenttype);
        String mediaUrl = file.getName();
        Log.d("mediaUrl",mediaUrl);
        File collegefile=new File(collegeid);
        Log.d("datefile", String.valueOf(collegefile));
        if (collegefile.exists()) {
            Log.d("newcreation", String.valueOf(collegefile.exists()));

            String  filedate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
            File Datefolder=new File(filedate);
            Log.d("datefilenew", String.valueOf(Datefolder));
            TransferObserver observer = transferUtility.upload(AWSKeys.BUCKET_NAME, collegefile+"/"+Datefolder+"/"+fileNameDateTime+"_"+mediaUrl, file, CannedAccessControlList.PublicRead);
            observer.setTransferListener(new UploadListener());
        }
        else {
            File newid=new File(collegeid);
            Log.d("newid",collegeid);
            if(!newid.exists()){
                Log.d("test",collegeid);
                String filedate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                File Datefolder=new File(filedate);
                Log.d("datefileexist", String.valueOf(Datefolder));
                TransferObserver observer = transferUtility.upload(AWSKeys.BUCKET_NAME, newid+"/"+Datefolder+"/"+fileNameDateTime+"_"+mediaUrl, file, CannedAccessControlList.PublicRead);
                observer.setTransferListener(new UploadListener());
            }
        }
    }

    private class UploadListener implements TransferListener {

        @Override
        public void onError(int id, Exception e) {
            Log.e(TAG, "Error during upload: " + id, e);
            s3UploadInterface.onUploadError(e.toString());
            s3UploadInterface.onUploadError("Error");
        }

        @Override

        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                    id, bytesTotal, bytesCurrent));
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            Log.d(TAG, "onStateChanged: " + id + ", " + newState);
            if (newState == TransferState.COMPLETED) {
                Log.d("Success", "Success");
                s3UploadInterface.onUploadSuccess("Success");
            }
        }
    }

    public void setOns3UploadDone(S3UploadInterface s3UploadInterface) {
        this.s3UploadInterface = s3UploadInterface;
    }

    public interface S3UploadInterface {
        void onUploadSuccess(String response);

        void onUploadError(String response);

    }
}
