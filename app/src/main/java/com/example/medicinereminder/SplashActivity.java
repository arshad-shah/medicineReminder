package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * A Custom Splash Screen for the application.
 * @author Arshad Shah
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load Main activity
        Intent loadMainActivity = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(loadMainActivity);
        finish();
    }
}