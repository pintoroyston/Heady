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
import com.example.myapplication.provider.QueryProvider;

public class SecondLevelCategoryActivity extends Activity {

    ListView list;
    String childCategoryId;
    String[] secondLevelCategories;
    String category;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        category = getIntent().getStringExtra("CATEGORY_NAME");
        title = findViewById(R.id.main_category_textview);
        title.setText(category);

        new AsyncTask<Void, Void, Void>() {

            private boolean isProduct = false;

            @Override
            protected Void doInBackground( Void... voids ) {
                QueryProvider queryProvider = new QueryProvider(getApplicationContext());
                childCategoryId = queryProvider.secondLevelCategoryListIds(category);
                String[] ids = childCategoryId.split(",");
                secondLevelCategories = new String[ids.length];
                for(int i = 0; i < ids.length; i++) {
                    secondLevelCategories[i] = queryProvider.getCategoryForId(ids[i]);
                }

                childCategoryId = queryProvider.secondLevelCategoryListIds(secondLevelCategories[0]);
                if (childCategoryId == null) {
                    isProduct = true;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(SecondLevelCategoryActivity.this, android.R.layout.simple_list_item_1, secondLevelCategories);
                list = findViewById(R.id.main_category_listview);
                list.setAdapter(itemsAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (isProduct) {
                            Intent intent = new Intent(SecondLevelCategoryActivity.this, ProductsPageActivity.class);
                            intent.putExtra("PRODUCT_NAME", (String)adapterView.getItemAtPosition(i));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SecondLevelCategoryActivity.this, ThirdLevelCategoryActivity.class);
                            intent.putExtra("CATEGORY_NAME", (String) adapterView.getItemAtPosition(i));
                            startActivity(intent);
                        }
                    }
                });
            }
        }.execute();
    }
}
