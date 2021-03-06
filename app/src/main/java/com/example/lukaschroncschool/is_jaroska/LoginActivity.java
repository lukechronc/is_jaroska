package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import java.io.IOException;
import java.net.InetAddress;

public class LoginActivity extends AppCompatActivity {
    public EditText username;
    public EditText password;
    public Button login;
    public CheckBox save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);

        Hawk.init(this).setEncryption(new NoEncryption()).build();

        if (Hawk.contains("logged_in")) {
            new PostThatShit().execute(new MyTaskParams(Hawk.get("username").toString(), Hawk.get("password").toString()));

        }

    }
    public void Login(View v){

        new PostThatShit().execute(new MyTaskParams(username.getText().toString(), password.getText().toString()));

    }
    private class PostThatShit extends AsyncTask<MyTaskParams, String, String> {
        private LoginActivity parent;

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

                Elements elements = document.select("form");

                if (elements.isEmpty()) {
                    Hawk.put("username", usr);
                    Hawk.put("password", pass);
                    if (save.isChecked()) {

                        Hawk.put("logged_in", true);
                    }
                    startActivity(new Intent(LoginActivity.this, BulletinActivity.class));
                } else {
                    Element alert = document.select("div.alert.alert-warning strong").first();
                    result = alert.text();
                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "no_internet";
            }
            return null;

        }

        protected void onPostExecute(String result) {
            if (result != null || result == "no_internet") {
                Snackbar.make(findViewById(R.id.loginactivity), "Žádné připojení k internetu", Snackbar.LENGTH_LONG).show();
            } else if (result != null) {
                Snackbar.make(findViewById(R.id.loginactivity), result, Snackbar.LENGTH_LONG).show();
            }
        }

    }


    private class MyTaskParams {
        String username;
        String password;

        private MyTaskParams(String username, String password) {
            this.username = username;
            this.password = password;

        }
    }
}

