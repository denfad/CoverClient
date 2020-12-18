package ru.denfad.cover.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.denfad.cover.DAO.PrimitiveDAO;
import ru.denfad.cover.R;
import ru.denfad.cover.models.Person;
import ru.denfad.cover.models.Place;
import ru.denfad.cover.network.NetworkService;

public class SearchActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private static final String[] types = new String[]{"Магазин", "Больница", "Общественное место", "Аптека", "Гос. учреждение"};
    private ArrayList<List<Place>> data = new ArrayList<List<Place>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_search_activity);

        listView = findViewById(R.id.expListView);

        ExpListAdapter adapter = new ExpListAdapter(data);
        listView.setAdapter(adapter);

        //fill data
        for (int i = 0; i < types.length; i++) {
            NetworkService.getInstance()
                    .getJSONApi()
                    .getPlacesByType(types[i])
                    .enqueue(new Callback<List<Place>>() {
                        @Override
                        public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                            data.add(response.body());
                            adapter.notifyDataSetChanged();
                            System.out.println(Arrays.asList(response.body()));
                        }

                        @Override
                        public void onFailure(Call<List<Place>> call, Throwable t) {

                        }
                    });
        }


    }

    private class ExpListAdapter extends BaseExpandableListAdapter {

        private ArrayList<List<Place>> mGroups;


        public ExpListAdapter(ArrayList<List<Place>> groups) {
            mGroups = groups;
        }

        @Override
        public int getGroupCount() {
            return mGroups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mGroups.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mGroups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mGroups.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.groupe_view, null);
            }

            if (isExpanded) {
                //Изменяем что-нибудь, если текущая Group раскрыта
            } else {
                //Изменяем что-нибудь, если текущая Group скрыта
            }

            TextView textGroup = (TextView) convertView.findViewById(R.id.place_type);
            textGroup.setText(types[groupPosition]);

            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.child_view, null);
            }

            TextView textChild = (TextView) convertView.findViewById(R.id.place_text);
            textChild.setText(mGroups.get(groupPosition).get(childPosition).getName());

            textChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Person p = PrimitiveDAO.getInstance().person;
                    p.addPlace(mGroups.get(groupPosition).get(childPosition));
                    NetworkService.getInstance()
                            .getJSONApi()
                            .updatePerson(p)
                            .enqueue(new Callback<Person>() {
                                @Override
                                public void onResponse(Call<Person> call, Response<Person> response) {
                                    Toast.makeText(getApplicationContext(), "Вы удачно добавили часто посещаемое место!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Person> call, Throwable t) {

                                }
                            });
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}

