package ru.denfad.cover.ui.route;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.denfad.cover.R;

public class RoutePlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_place);

        Button toChemistry = findViewById(R.id.to_chemist);
        toChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoutePlaceActivity.this, RouteTypeActivity.class);
                intent.putExtra("place", "Аптека");
                startActivity(intent);
            }
        });

        Button toHospital = findViewById(R.id.to_hospital);
        toHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoutePlaceActivity.this, RouteTypeActivity.class);
                intent.putExtra("place", "Больница");
                startActivity(intent);
            }
        });
    }
}


