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

public class ProductsPageActivity extends Activity {

    ListView list;
    String[] productList;
    String category;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        category = getIntent().getStringExtra("PRODUCT_NAME");
        title = findViewById(R.id.main_category_textview);
        title.setText(category);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                QueryProvider queryProvider = new QueryProvider(getApplicationContext());
                productList = queryProvider.getProductsForCategoryName(category);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(ProductsPageActivity.this, android.R.layout.simple_list_item_1, productList);
                list = findViewById(R.id.main_category_listview);
                list.setAdapter(itemsAdapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ProductsPageActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("PRODUCT_TYPE", (String)adapterView.getItemAtPosition(i));
                        startActivity(intent);
                    }
                });
            }
        }.execute();
    }
}
