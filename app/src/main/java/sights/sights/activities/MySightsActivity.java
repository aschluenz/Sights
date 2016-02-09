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

import java.util.ArrayList;
import java.util.HashMap;

import AppLogic.PreferenceData;
import AppLogic.RouteHandler;
import sights.sights.R;

public class MySightsActivity extends AppCompatActivity {

    ListView mySights;

    HashMap<String, String> mySightsList = null;

    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        i = new Intent(this, PlaceDetailActivity.class);

        new MySightsAsync().execute();

        mySights = (ListView) findViewById(R.id.mySightsList);

        mySights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = mySights.getItemAtPosition(position);


                String sightID = mySightsList.get(obj);


                //TODO START DETAILS Activity
                // i.putExtra("extra", routeid);
                startActivity(i);
            }
        });


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }



    class MySightsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            RouteHandler rh = new RouteHandler();
            mySightsList = rh.getAllSightsByUserId(PreferenceData.getPrefLoggedinUserId(getBaseContext()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    final ArrayList<String> list = new ArrayList<>();

                    mySightsList.keySet().toArray();
                    for (Object o : mySightsList.keySet().toArray()) {
                        list.add(o.toString());
                    }
                    Log.d("List Array:", list.toString());
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
                    mySights.setAdapter(adapter);

                }
            });
        }

    }

}
