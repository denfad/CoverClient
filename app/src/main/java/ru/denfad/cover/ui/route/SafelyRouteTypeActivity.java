package ru.denfad.cover.ui.route;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import ru.denfad.cover.R;
import ru.denfad.cover.ui.MainActivity;

public class SafelyRouteTypeActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safely_route_type);

        intent = getIntent();
        RadioGroup group = findViewById(R.id.safely_group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.on_legs:
                        intent.putExtra("type", "walking");
                        intent.setClass(SafelyRouteTypeActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.private_car:
                        intent.putExtra("type", "driving");
                        intent.setClass(SafelyRouteTypeActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.taxi:
                        intent.putExtra("type", "driving");
                        intent.setClass(SafelyRouteTypeActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
