package ru.denfad.cover.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.denfad.cover.DAO.PrimitiveDAO;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Person;

public class SplashActivity extends AppCompatActivity {

    // Время в милесекундах, в течение которого будет отображаться Splash Screen
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private SharedPreferences mSharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
// По истечении времени, запускаем главный активити, а Splash Screen закрываем
                if(mSharedPreferences.getString("person","").equals("")){
                    Intent mainIntent = new Intent(SplashActivity.this, RegistrationActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                }
                else {
                    Gson gson = new Gson();
                    PrimitiveDAO.getInstance().person = gson.fromJson(mSharedPreferences.getString("person",""), Person.class);
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
