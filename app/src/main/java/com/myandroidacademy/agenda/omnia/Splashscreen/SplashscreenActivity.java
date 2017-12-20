package com.myandroidacademy.agenda.omnia.Splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.myandroidacademy.agenda.omnia.Login.LoginActivity;
import com.myandroidacademy.agenda.omnia.Main.MainActivity;
import com.myandroidacademy.agenda.omnia.R;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .repeat(0)
                .playOn(findViewById(R.id.logo_splash));
        Thread timer= new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    openLogin();
                }
            }
        };
        timer.start();

    }

    protected void openLogin(){
        Intent intentLogin = new Intent(SplashscreenActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }

    protected void openMain(){
        Intent intentMain = new Intent(SplashscreenActivity.this, MainActivity.class);
        startActivity(intentMain);
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
