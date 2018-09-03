package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appizona.yehiahd.fastsave.FastSave;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

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

        Methods m = new Methods(this);
        m.RunCookies();


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

}
