package ru.denfad.cover.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.denfad.cover.R;

public class StatisticActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_layout);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticActivity.this, ActionsActivity.class));
            }
        });

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient() {
        });


        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView.loadUrl("https://news.google.com/covid19/map?hl=ru&gl=RU&ceid=RU%3Aru");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // Note the Chooser below. If no applications match,
            // Android displays a system message.So here there is no need for try-catch.
            startActivity(Intent.createChooser(intent, "Browse with"));
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


    }

}
