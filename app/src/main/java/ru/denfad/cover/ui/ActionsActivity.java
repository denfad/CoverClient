package ru.denfad.cover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.denfad.cover.R;
import ru.denfad.cover.ui.advice.AdvicesActivity;
import ru.denfad.cover.ui.route.RoutePlaceActivity;

public class ActionsActivity extends AppCompatActivity {

    private Button backButton;
    private Button placeButton;
    private Button adviceButton;
    private Button callButton;
    private Button statisticButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions_layout);

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionsActivity.this, MainActivity.class));
            }
        });

        callButton = findViewById(R.id.ambulance);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionsActivity.this, CallAmbulanceActivity.class));
            }
        });

        statisticButton = findViewById(R.id.statistic);
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionsActivity.this, StatisticActivity.class));
            }
        });


        placeButton = findViewById(R.id.get_place);
        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionsActivity.this, RoutePlaceActivity.class));
            }
        });

        adviceButton = findViewById(R.id.get_advice);
        adviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionsActivity.this, AdvicesActivity.class));
            }
        });
    }
}
