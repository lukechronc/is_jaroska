package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

import com.appizona.yehiahd.fastsave.FastSave;
import com.example.lukaschroncschool.is_jaroska.Card;
import com.example.lukaschroncschool.is_jaroska.CardAdapter;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.example.lukaschroncschool.is_jaroska.LoginActivity;
public class BulletinActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);
        // toolbar initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // navigation drawer initialization
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // shared preferences library initialization
        Hawk.init(this).setEncryption(new NoEncryption()).build();
        FastSave.init(getApplicationContext());

        if(isInternetAvailable()) new getCookies().execute(new MyTaskParams("s","s"));
        else Snackbar.make(findViewById(R.id.bulletin_container),"offline",Snackbar.LENGTH_LONG).show();




    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bulletin) {

        } else if (id == R.id.nav_supl) {

        } else if (id == R.id.nav_schedule_class) {

        } else if (id == R.id.nav_schedule_all) {

        }else if (id == R.id.nav_grades) {

        }else if (id == R.id.nav_settings) {

        }else if (id == R.id.nav_logout) {
            Hawk.deleteAll();
            startActivity(new Intent(BulletinActivity.this,LoginActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class getCookies extends AsyncTask<MyTaskParams, String, String> {
        private LoginActivity parent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyTaskParams... params) {
            try{
                Connection.Response res = Jsoup
                        .connect("https://is.jaroska.cz/login.php")
                        .data("formUsername", Hawk.get("username").toString())
                        .data("formPassword",Hawk.get("password").toString())
                        .method(Connection.Method.POST)
                        .execute();
                Map<String, String> loginCookies = res.cookies();
                Hawk.put("cookies",loginCookies);
                FastSave.getInstance().saveObject("login_cookie",loginCookies);
                Map<String,String> owo = FastSave.getInstance().getObject("login_cookie",Map.class);
                Document doc = Jsoup.connect("https://is.jaroska.cz")
                        .cookies(owo)
                        .get();
                Log.d("cookieget",doc.toString());

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
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
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
