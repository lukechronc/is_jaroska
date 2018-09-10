package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.appizona.yehiahd.fastsave.FastSave;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        wb = (WebView) findViewById(R.id.link_webview);
        if(!link.equals("")){
            wb.loadUrl("https://is.jaroska.cz/"+link);
        }

    }

}
