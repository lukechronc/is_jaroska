package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.lukaschroncschool.is_jaroska.SimpleCrypto;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import java.io.IOException;
import java.util.Random;

public class LandingActivity extends AppCompatActivity {
    public EditText username;
    public EditText password;
    public Button login;
    public CheckBox save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);
        Hawk.init(this).setEncryption(new NoEncryption()).build();

        if( Hawk.contains("username") || Hawk.contains("password")){
            new PostThatShit().execute(new MyTaskParams(Hawk.get("username").toString(),Hawk.get("password").toString()));
        }

    }

    public void Login(View v){
        new PostThatShit().execute(new MyTaskParams(username.getText().toString(),password.getText().toString()));

    }

    private class PostThatShit extends AsyncTask<MyTaskParams, String, String> {
        private LandingActivity parent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyTaskParams... params) {
            String result;
            try {
                String url = "https://is.jaroska.cz/login.php";
                String usr = params[0].username;
                String pass = params[0].password;

                //Connect to the website
                Document document = Jsoup.connect(url)
                        .data("formUsername", usr)
                        .data("formPassword", pass)
                        .post();

                Log.d("res", document.toString());
                Elements elements = document.select("form");

                if (elements.isEmpty()) {
                    if(save.isChecked()){
                        Hawk.put("username",usr);
                        Hawk.put("password",pass);
                    }
                    startActivity(new Intent(LandingActivity.this, BulletinActivity.class));
                } else {
                    Element alert = document.select("div.alert.alert-warning strong").first();
                    result = alert.text();
                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        protected void onPostExecute(String result){
            if (result != null) {
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
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
}
