package com.example.lukaschroncschool.is_jaroska;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(this).setEncryption(new NoEncryption()).build();

        if(Hawk.contains("logged_in")){
            startActivity(new Intent(MainActivity.this, BulletinActivity.class));
        }
        else{
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    
}
