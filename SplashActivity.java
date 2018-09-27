package com.example.android.amor_em_leite;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;


public class SplashActivity extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    public void run(){
        startActivity(new Intent(this, TelaLogin.class));
        finish();
    }
}
