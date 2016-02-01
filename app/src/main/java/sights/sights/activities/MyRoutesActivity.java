package sights.sights.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class MyRoutesActivity extends AppCompatActivity{


    ListView myRoutes;

    public Route myroute = new Route("Basti wird irre");
    public Route myroute1 = new Route("Andy ist irre");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routes);


        myRoutes = (ListView) findViewById(R.id.myRoutesLists);



        final ArrayList<String> list = new ArrayList<>();
        list.add(myroute.getName());
        list.add(myroute1.getName());



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        myRoutes.setAdapter(adapter);

      /*  RouteHandler rh = new RouteHandler();
        rh.delegate = this;
        try {
            rh.getAllRouteFromUser(PreferenceData.getPrefLoggedinUserId(getBaseContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        */







    }
/*



    @Override
    public void prozessFinish(String output) throws JSONException {
        Log.d("ist es endlich da?", output);
        generateList(output);
    }

    private void generateList(String res)  {
        HashMap<String,String> routes = new HashMap<>();

        try {
            // job = new JSONObject(res);
            JSONArray jsonArray = new JSONArray(res);

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonRoute = (JSONObject) jsonArray.get(i);
                Log.d("jsonRoute Object: ", jsonRoute.toString());

                routes.put(jsonRoute.getString("title"),jsonRoute.getString("routeID"));
            }

            Object[] nameObjects =  routes.keySet().toArray();
            String[] names = new String[nameObjects.length];
            for (int i = 0; i < nameObjects.length; i++) {
                names[i] = (String) nameObjects[i];
            }

            getListView().setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,names));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/

}
