package sights.sights.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import AppLogic.RouteHandler;
import HttpNetwork.NetworkHelper;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

public class SuggestedActivity extends AppCompatActivity {

    ListView suggestedRoutes;

    HashMap<String, String> routesByName = new HashMap<>();

    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        i = new Intent(this, RouteDetailsActivity.class);


        new suggestedListAsync().execute();

        suggestedRoutes = (ListView) findViewById(R.id.suggestedList);

        suggestedRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = suggestedRoutes.getItemAtPosition(position);


                String routeid = routesByName.get(obj);

                i.putExtra("routeID", routeid);
                i.putExtra("isSavable", true);
                startActivity(i);
            }
        });

    }


    class suggestedListAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            String _url = "http://nodejs-sightsapp.rhcloud.com/route/all";

            //get
            NetworkHelper nh = new NetworkHelper();
            nh.get(_url, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.d("getallRoutes:", e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsono = (JSONObject) jsonArray.get(i);
                                routesByName.put(jsono.getString("title"),
                                        jsono.getString("routeID"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final ArrayList<String> list = new ArrayList<>();

                            routesByName.keySet().toArray();
                            for (Object o : routesByName.keySet().toArray()) {
                                list.add(o.toString());
                            }
                            Log.d("List Array:", list.toString());
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
                            suggestedRoutes.setAdapter(adapter);

                        }
                    });
                }
            });
            return null;
        }


    }
}
