package com.example.report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



    public class MainActivity extends AppCompatActivity {

        private String TAG = MainActivity.class.getSimpleName();
        private ListView lv;
        ArrayList<HashMap<String, String>> newsList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            newsList = new ArrayList<>();
            lv = (ListView) findViewById(R.id.list);
            new GetContacts().execute();
        }
        private class GetContacts extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
            }
            @Override
            protected Void doInBackground(Void... arg0) {
                HttpHandler sh = new HttpHandler();
                String url =
                        "https://inshorts.deta.dev/news?category=sports";
                String jsonStr = sh.makeServiceCall(url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONArray data = jsonObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject c = data.getJSONObject(i);
                            String author = c.getString("author");
                            String date = c.getString("date");
                            String title = c.getString("title");
                            String content = c.getString("content");

                            HashMap<String, String> newsR = new HashMap<>();
                            newsR.put("author", author);
                            newsR.put("date",date);
                            newsR.put("title", title);
                            newsR.put("content", content);
                            newsList.add(newsR);
                        } } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            } }); }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run(){
                        Toast.makeText(getApplicationContext(),
"Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG).show();
                    } }); }
                  return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                    newsList,
                    R.layout.list_item, new String[]{ "author","date","title","content"},
                    new int[]{R.id.author,R.id.date,R.id.title,R.id.content});
            lv.setAdapter(adapter);
        }
    }
}
