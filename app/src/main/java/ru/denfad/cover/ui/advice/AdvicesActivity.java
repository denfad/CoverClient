package ru.denfad.cover.ui.advice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import ru.denfad.cover.R;

public class AdvicesActivity extends AppCompatActivity {

    private List<String> data = new ArrayList<>();
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advices_activity);

        data.add("Соблюдайте правила гигиены рук. Часто мойте руки водой с мылом или обрабатывайте их  антисептиком для рук.");
        data.add("Держитесь на безопасном расстоянии от чихающих или кашляющих людей.");
        data.add("Носите маску, когда находитесь в окружении других людей.");
        data.add("Не прикасайтесь руками к глазам, рту или носу.");

        ViewPager2 pager = findViewById(R.id.view_pager);
        pager.setAdapter(new ViewPagerAdapter(data));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class ViewPagerAdapter extends RecyclerView.Adapter<PagerVH>{

        private List<String> advices;

        ViewPagerAdapter(List<String> advices){
            this.advices = advices;
        }
        @NonNull
        @Override
        public PagerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.advice_item, parent, false);
            return new PagerVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PagerVH holder, int position) {
            holder.advice.setText(advices.get(position));
        }

        @Override
        public int getItemCount() {
            return advices.size();
        }
    }

    private class PagerVH extends RecyclerView.ViewHolder{

        public TextView advice;
        public PagerVH(@NonNull View itemView) {
            super(itemView);
            advice = itemView.findViewById(R.id.advice_text);
        }
    }
}
