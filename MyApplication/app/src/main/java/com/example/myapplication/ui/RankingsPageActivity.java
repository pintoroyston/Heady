package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.Rankings;
import com.example.myapplication.provider.QueryProvider;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class RankingsPageActivity extends Activity {

    String rankingTitle;
    TextView title;
    Map<Integer, Integer> rankingsMap = new TreeMap<>(Collections.reverseOrder());
    String[] rankingProducts;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        rankingTitle = getIntent().getStringExtra("RANKING_NAME");
        title = findViewById(R.id.main_category_textview);
        title.setText(rankingTitle);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                QueryProvider queryProvider = new QueryProvider(getApplicationContext());
                Rankings[] rankings = queryProvider.getAllRankings();
                for (int i = 0; i < rankings.length; i++) {
                    Rankings object = rankings[i];
                    if (object.getRankingName().equals(rankingTitle)) {
                        rankingsMap.put(object.getRankingCount(), object.getProductId());
                    }
                }

                int i = 0;
                rankingProducts = new String[rankingsMap.size()];
                for(Map.Entry<Integer,Integer> entry : rankingsMap.entrySet()) {
                    Integer value = entry.getValue();
                    rankingProducts[i] = queryProvider.getProductNameForProductId(value);
                    i++;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(RankingsPageActivity.this, android.R.layout.simple_list_item_1, rankingProducts);
                list = findViewById(R.id.main_category_listview);
                list.setAdapter(itemsAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(RankingsPageActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("PRODUCT_TYPE", (String)adapterView.getItemAtPosition(i));
                            startActivity(intent);
                    }
                });
            }
        }.execute();
    }
}
