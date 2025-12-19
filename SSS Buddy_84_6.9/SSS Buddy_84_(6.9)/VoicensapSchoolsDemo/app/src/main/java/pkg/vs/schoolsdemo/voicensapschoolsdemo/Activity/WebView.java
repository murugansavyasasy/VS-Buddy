package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.util.Util_common;

public class WebView extends AppCompatActivity {
    private android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

     //   webView = findViewById(R.id.webview);

        webView = findViewById(R.id.webview);
        String docxFilePath = Util_common.Doc_URL;

        // Load the DOCX file using Google Docs Viewer
        loadDocxInWebView(docxFilePath);


    }

    private void loadDocxInWebView(String docxFilePath) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        // Use Google Docs Viewer to view the DOCX file
        String googleDocsViewerUrl = "https://docs.google.com/gview?embedded=true&url=" + docxFilePath;
        webView.loadUrl(googleDocsViewerUrl);
    }
}