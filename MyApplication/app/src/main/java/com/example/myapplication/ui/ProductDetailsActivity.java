package com.example.myapplication.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.Variants;
import com.example.myapplication.provider.QueryProvider;

public class ProductDetailsActivity extends Activity {

    ListView list;
    String[] productList;
    String productType;
    TextView title;
    int productId;
    String[] description;
    String taxName;
    double taxValue;
    Variants[] variants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        productType = getIntent().getStringExtra("PRODUCT_TYPE");
        title = findViewById(R.id.main_category_textview);
        title.setText(productType);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                QueryProvider queryProvider = new QueryProvider(getApplicationContext());
                productId = queryProvider.getProductIdForProductName(productType);
                taxName = queryProvider.getTaxNameForProductId(productId);
                taxValue = queryProvider.getTaxValueForProductId(productId);
                variants = queryProvider.getVariantsForProductId(productId);
                description = new String[variants.length];
                for (int i = 0; i < variants.length; i++) {
                    description[i] = "Color : " + variants[i].getColor() + "\n";
                    if(variants[i].getSize() != 0)
                        description[i] += "Size : " + variants[i].getSize() + "\n";
                    description[i] += "Price : " + variants[i].getPrice();
                    description[i] += " ( additional " + taxName + " " + taxValue + "% )";
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(ProductDetailsActivity.this, android.R.layout.simple_list_item_1, description);
                list = findViewById(R.id.main_category_listview);
                list.setAdapter(itemsAdapter);
            }
        }.execute();
    }
}
