package sights.sights.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import AppLogic.AsyncResponse;
import AppLogic.PreferenceData;
import AppLogic.RouteHandler;
import HttpNetwork.NetworkHelper;
import model.Route;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

public class MyRoutesActivity extends AppCompatActivity {


    ListView myRoutes;

    HashMap<String, String> routesByName = null;

    public Route myroute = new Route("Basti wird irre");
    public Route myroute1 = new Route("Andy ist irre");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routes);


        new ListAsynctask().execute();

        myRoutes = (ListView) findViewById(R.id.myRoutesLists);

        //final ArrayList<String> list = new ArrayList<>();
       // list.add(myroute.getName());
       // list.add(myroute1.getName());




      /*  RouteHandler rh = new RouteHandler();
        rh.delegate = this;
        try {
            rh.getAllRouteFromUser(PreferenceData.getPrefLoggedinUserId(getBaseContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        */


    }

    class ListAsynctask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            RouteHandler rh = new RouteHandler();
            try {
                routesByName = rh.getAllRouteFromUser(PreferenceData.getPrefLoggedinUserId(getBaseContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                Thread.sleep(2000); //TODO muss noch gelöst werden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final ArrayList<String> list = new ArrayList<>();

                 routesByName.keySet().toArray();
            for(Object o:routesByName.keySet().toArray()){
                list.add(o.toString());
            }
            Log.d("List Array:", list.toString());
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, list);
                myRoutes.setAdapter(adapter);


        }
    }
}