package ru.denfad.cover.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.DAO.PrimitiveDAO;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Person;
import ru.denfad.cover.network.NetworkService;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText login;
    private EditText password;
    private EditText age;
    private Button add;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        name = findViewById(R.id.name);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        add = findViewById(R.id.add);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkService.getInstance()
                        .getJSONApi()
                        .registPerson(new Person(login.getText().toString(), password.getText().toString(), name.getText().toString(), Integer.valueOf(age.getText().toString())))
                        .enqueue(new Callback<Person>() {
                            @Override
                            public void onResponse(Call<Person> call, Response<Person> response) {
                                PrimitiveDAO.getInstance().person = response.body();
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                GsonBuilder gson = new GsonBuilder();
                                editor.putString("person",gson.create().toJson(response.body())).apply();
                                Toast.makeText(getApplicationContext(), "Добро пожаловать в Caver " + response.body().getName(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, EducationActivity.class));
                            }

                            @Override
                            public void onFailure(Call<Person> call, Throwable t) {
                                startActivity(new Intent(RegistrationActivity.this, EducationActivity.class));
                            }
                        });

            }
        });
    }
}
