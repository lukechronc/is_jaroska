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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        FastSave.init(this);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        boolean schedule = intent.getBooleanExtra("schedule",false);
        wb = (WebView) findViewById(R.id.link_webview);
        if(!schedule){
            if(!link.equals("")){
                wb.loadUrl("https://is.jaroska.cz/"+link);
            }
        }else{
            new getSchedule().execute();
        }


    }
    private class getSchedule extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void...voids){
            String result = "";
            Document doc = Connect(FastSave.getInstance().getObject("login_cookie",Map.class));
            Elements elements = doc.select("ul li a");
            for(Element x : elements){
                if(x.text().contains("Rozvrh")){
                    result = x.text();
                }
            }
            return result;
        }
        @Override
        protected void onPostExecute(String link){
            if(!link.equals("")) wb.loadUrl("https://is.jaroska.cz/"+link);
        }
    }
    private Document Connect(Map<String,String> cookie){
        try {
            Document doc = Jsoup.connect("https://is.jaroska.cz/index.php?akce=650")
                    .cookies(cookie)
                    .get();
            return doc;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
