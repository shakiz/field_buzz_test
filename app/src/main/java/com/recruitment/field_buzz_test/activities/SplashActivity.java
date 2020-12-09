package com.recruitment.field_buzz_test.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.recruitment.field_buzz_test.R;
import com.recruitment.field_buzz_test.utils.PrefManager;

import static com.recruitment.field_buzz_test.utils.Constants.mIsLoggedIn;

public class SplashActivity extends AppCompatActivity {
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //region init components and their interactions
        initUI();
        bindUIWithComponents();
        //endregion
    }

    //region init XML components with backend
    private void initUI() {
        prefManager = new PrefManager(this);
    }
    //endregion

    //region perform UI interactions
    private void bindUIWithComponents() {
        //region splash screen code to call new activity after some time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (prefManager.getBoolean(mIsLoggedIn)) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
    //endregion
}