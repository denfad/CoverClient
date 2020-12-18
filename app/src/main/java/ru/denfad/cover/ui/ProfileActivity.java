package ru.denfad.cover.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.DAO.PrimitiveDAO;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Person;
import ru.denfad.cover.network.NetworkService;

public class ProfileActivity extends AppCompatActivity {

    private TextView status;
    private Button changeState;
    private Button back;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        status = findViewById(R.id.status);
        changeState = findViewById(R.id.change_status);
        back = findViewById(R.id.back);

        person = PrimitiveDAO.getInstance().person;

        NetworkService.getInstance()
                .getJSONApi()
                .authPerson(person.getLogin(), person.getPassword())
                .enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        fillViews(response.body());
                        person = response.body();
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });

        changeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetStatusDialog dialog = new SetStatusDialog(ProfileActivity.this);
                dialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fillViews(Person person) {
        status.setText(person.getStatus());
    }

    class SetStatusDialog extends Dialog {


        public SetStatusDialog(@NonNull Context context) {
            super(context);

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(params);
            this.setContentView(R.layout.set_status_dialog);

            RadioGroup group  = findViewById(R.id.status_group);
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
                                    fillViews(response.body());
                                    SetStatusDialog.this.cancel();
                                }

                                @Override
                                public void onFailure(Call<Person> call, Throwable t) {
                                    SetStatusDialog.this.cancel();
                                }
                            });
                }
            });
        }

    }

}
