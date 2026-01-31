package pkg.vs.schoolsdemo.voicensapschoolsdemo.AWS;

public interface UploadCallback {
        void onUploadSuccess(String response,String isFileUploaded);
        void onUploadError(String error);
}
