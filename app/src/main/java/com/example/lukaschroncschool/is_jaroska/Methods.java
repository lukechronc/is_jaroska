package com.example.lukaschroncschool.is_jaroska;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.TextView;

import com.appizona.yehiahd.fastsave.FastSave;
import com.orhanobut.hawk.Hawk;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Methods {
    public Activity activity;
    public Methods(Activity _activity){
        this.activity = _activity;
        FastSave.init(activity);
    }

    public void RunCookies(){
        try{new getCookies().execute(new MyTaskParams("","")).get();}
        catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
    }

    private class getCookies extends AsyncTask<MyTaskParams, String, String> {
        private BulletinActivity parent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyTaskParams... params) {
            String result;
            try{
                Connection.Response res = Jsoup
                        .connect("https://is.jaroska.cz/login.php")
                        .data("formUsername", Hawk.get("username").toString())
                        .data("formPassword",Hawk.get("password").toString()).method(Connection.Method.POST)
                        .execute();
                Map<String, String> loginCookies = res.cookies();
                FastSave.getInstance().saveObject("login_cookie",loginCookies);
                Map<String,String> owo = FastSave.getInstance().getObject("login_cookie",Map.class);
                Document doc = Jsoup.connect("https://is.jaroska.cz")
                        .cookies(owo)
                        .get();
                result = Jsoup.parse(doc.selectFirst("div.col-12.alert.alert-info").text()).text();
                result = result.substring(result.indexOf(":")+1);
                result.trim();
                Log.d("cookieget",owo.toString());
                return result;

            }catch (IOException e){
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(String result){
            if (result == null){
                Snackbar.make(activity.findViewById(R.id.bulletin_container),"Žádné připojení k internetu",Snackbar.LENGTH_LONG).show();
            }
            /*else{
                TextView tv = (TextView) activity.findViewById(R.id.user_textview);
                tv.setText(result);
            }*/
        }

    }

    private class MyTaskParams {
        String username;
        String password;

        private MyTaskParams(String username, String password){
            this.username = username;
            this.password = password;

        }

    }
    private Document Connect(Map<String,String> cookie){
        try {
            Document doc = Jsoup.connect("https://is.jaroska.cz")
                    .cookies(cookie)
                    .get();
            return doc;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
