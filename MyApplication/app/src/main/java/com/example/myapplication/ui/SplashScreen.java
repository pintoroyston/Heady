package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.network.FetchJSON;
import com.example.myapplication.provider.PopulateProvider;

/**
 * This class will do the initial sync
 */
public class SplashScreen extends Activity {

    public String url = "https://stark-spire-93433.herokuapp.com/json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (isConnected(getApplicationContext())) {
            new AsyncTask<Void, Void, Void>() {

                String json;

                @Override
                protected Void doInBackground(Void... voids) {
                    FetchJSON fetchJSON = new FetchJSON();
                    json = fetchJSON.fetchJsonForUrl(url);

                    PopulateProvider populateProvider = new PopulateProvider(getApplicationContext());
                    populateProvider.insertContentIntoProvider(json);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent(SplashScreen.this, MasterCategoryActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("Royston", "Operation completed");
                }
            }.execute();
        } else {
            TextView textView = findViewById(R.id.textView);
            textView.setText(R.string.splash_screen_error);
        }
    }

    private NetworkInfo getNetworkInfo(Context context){
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        } catch (SecurityException se) {
            Log.e("Royston", "Permission denied to access network info", se);
        }
        return null;
    }

    private boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

}
