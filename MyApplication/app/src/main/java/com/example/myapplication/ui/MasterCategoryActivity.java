package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.provider.QueryProvider;

public class MasterCategoryActivity extends Activity {

    ListView masterList;
    ListView socialList;
    String[] masterListItems;
    String[] socialListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                QueryProvider queryProvider = new QueryProvider(getApplicationContext());
                masterListItems = queryProvider.mainCategoryList();
                socialListItems = queryProvider.getRankingNames();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> masterListAdapter =
                        new ArrayAdapter<>(MasterCategoryActivity.this, android.R.layout.simple_list_item_1, masterListItems);
                masterList = findViewById(R.id.main_activity_listview);
                masterList.setAdapter(masterListAdapter);

                masterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MasterCategoryActivity.this, SecondLevelCategoryActivity.class);
                        intent.putExtra("CATEGORY_NAME", (String)adapterView.getItemAtPosition(i));
                        startActivity(intent);
                    }
                });

                ArrayAdapter<String> socialListAdapter =
                        new ArrayAdapter<>(MasterCategoryActivity.this, android.R.layout.simple_list_item_1, socialListItems);
                socialList = findViewById(R.id.main_activity_listview_social);
                socialList.setAdapter(socialListAdapter);

                socialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MasterCategoryActivity.this, RankingsPageActivity.class);
                        intent.putExtra("RANKING_NAME", (String)adapterView.getItemAtPosition(i));
                        startActivity(intent);
                    }
                });
            }
        }.execute();
    }
}
