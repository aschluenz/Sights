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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import AppLogic.RouteHandler;
import sights.sights.R;

public class SuggestedActivity extends AppCompatActivity {

    ListView suggestedRoutes;

    HashMap<String, String> routesByName = null;

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
                startActivity(i);
            }
        });

    }


    class suggestedListAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            RouteHandler rh = new RouteHandler();
            routesByName = rh.getAllRoutes();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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

    }
}
