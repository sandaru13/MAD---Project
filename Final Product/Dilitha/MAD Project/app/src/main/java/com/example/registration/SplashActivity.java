package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registration.preference.Consts;
import com.example.registration.preference.StoreDataManager;

public class SplashActivity extends AppCompatActivity {

    private StoreDataManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferenceManager = new StoreDataManager(this);

        CountDownTimer splashTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                redirect();
            }
        };

        splashTimer.start();
    }

    private void redirect() {

        if (preferenceManager.isPreferencesSet(Consts.IS_REGISTERED) && preferenceManager.getPreference(Consts.IS_REGISTERED).equals("1")) {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignUpActivity.class);
            finish();
            startActivity(intent);
        }

    }
}