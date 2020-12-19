package ru.denfad.cover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ru.denfad.cover.R;
import ru.denfad.cover.ui.advice.AdvicesActivity;

public class EducationActivity extends AppCompatActivity {

    private List<Integer> data = new ArrayList<>();
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_activity);
        data.add(R.drawable.group_12);
        data.add(R.drawable.group_13);
        data.add(R.drawable.group_14);

        ViewPager2 pager = findViewById(R.id.view_pager);
        pager.setAdapter(new EducationActivity.ViewPagerAdapter(data));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EducationActivity.this, MainActivity.class));
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabDots);
        new TabLayoutMediator(tabLayout, pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    }
                }).attach();
    }

    private class ViewPagerAdapter extends RecyclerView.Adapter<EducationActivity.PagerVH>{

        private List<Integer> advices;

        ViewPagerAdapter(List<Integer> advices){
            this.advices = advices;
        }
        @NonNull
        @Override
        public EducationActivity.PagerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.education_item, parent, false);
            return new EducationActivity.PagerVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EducationActivity.PagerVH holder, int position) {
            holder.advice.setImageResource(data.get(position));
        }

        @Override
        public int getItemCount() {
            return advices.size();
        }
    }

    private class PagerVH extends RecyclerView.ViewHolder{

        public ImageView advice;
        public PagerVH(@NonNull View itemView) {
            super(itemView);
            advice = itemView.findViewById(R.id.image);
        }
    }
}
