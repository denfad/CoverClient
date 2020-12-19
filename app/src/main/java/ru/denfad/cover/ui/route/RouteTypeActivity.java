package ru.denfad.cover.ui.route;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.denfad.cover.R;
import ru.denfad.cover.ui.MainActivity;

public class RouteTypeActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_type);

        intent = getIntent();
        Button safely = findViewById(R.id.safely);
        safely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "walking");
                intent.setClass(RouteTypeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button onBus = findViewById(R.id.on_bus);
        onBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "transit");
                intent.setClass(RouteTypeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
