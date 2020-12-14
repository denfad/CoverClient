package ru.denfad.cover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.denfad.cover.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText login;
    private EditText password;
    private EditText age;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        name = findViewById(R.id.name);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }
}
