package ru.denfad.cover.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.DAO.PrimitiveDAO;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Person;
import ru.denfad.cover.network.NetworkService;

public class ProfileActivity extends AppCompatActivity {

    private Button back;
    private Person person;
    private static Map<String, Integer> statusMap = new HashMap<>();

    static {
        statusMap.put("Есть положительный тест", R.id.positive_test);
        statusMap.put("Есть отрицательный тест", R.id.negative_test);
        statusMap.put("Ожидание результатов", R.id.wait);
        statusMap.put("Потенциально контактировал", R.id.potential_contact);
        statusMap.put("Контактировал с больным", R.id.contact);
        statusMap.put("Есть симптомы", R.id.symptoms);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        back = findViewById(R.id.back);

        person = PrimitiveDAO.getInstance().person;

        RadioGroup group = findViewById(R.id.status_group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton button = findViewById(checkedId);
                person.setStatus(button.getText().toString());
                NetworkService.getInstance()
                        .getJSONApi()
                        .updatePerson(person)
                        .enqueue(new Callback<Person>() {
                            @Override
                            public void onResponse(Call<Person> call, Response<Person> response) {
                                Toast.makeText(getApplicationContext(),"Статус обновлен", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Person> call, Throwable t) {

                            }
                        });

                if(checkedId == R.id.positive_test || checkedId == R.id.contact || checkedId == R.id.symptoms){
                    startActivity(new Intent(ProfileActivity.this, ActionsActivity.class));
                }
            }
        });

        NetworkService.getInstance()
                .getJSONApi()
                .authPerson(person.getLogin(), person.getPassword())
                .enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        try {
                            group.check(statusMap.get(response.body().getStatus()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
    }


}
