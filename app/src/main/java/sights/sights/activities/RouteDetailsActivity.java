package sights.sights.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import HttpNetwork.NetworkHelper;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

public class RouteDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap routeMap;

    private String routeID;
    private ListView sightslist;
    private  PolylineOptions sightsLine;

    private ArrayList<String> namesSights;

    LatLng point_1 = new LatLng(40.712784, -74.005941);
    LatLng point_2 = new LatLng(52.520007,13.404954);


    //Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        routeID = getIntent().getStringExtra("routeID");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.routeMap);
        mapFragment.getMapAsync(this);
        sightslist = (ListView) findViewById(R.id.sightsList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new SightsfromRoute().execute();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        routeMap = googleMap;
        Log.d("aufruf:", "onMapReady");
    }


    public void makeList(){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, namesSights);
        sightslist.setAdapter(adapter);
    }

    public void drawPolyline(PolylineOptions polyLine){
        Polyline poly = routeMap.addPolyline(polyLine);
    }


    public class SightsfromRoute extends AsyncTask<Void, Void, PolylineOptions> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(PolylineOptions poly) {
            super.onPostExecute(poly);
//            drawPolyline(poly);
          //  makeList();

        }

        @Override
        protected PolylineOptions doInBackground(Void...params) {
           String Url = "http://nodejs-sightsapp.rhcloud.com/route/get";

            JSONObject json = new JSONObject();
            try {
                json.put("routeID" , routeID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            NetworkHelper nh = new NetworkHelper();
            nh.post(Url, json.toString(), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.d("response get Sights:", response.message());
                    if(response.isSuccessful()){
                        try {

                            JSONObject json = new JSONObject(response.body().string());
                            JSONArray sightsarray = json.getJSONArray("sights");
                            Log.d("aufruf:","drawPolinie" );

                            // ArrayList<LatLng> sightspoint = new ArrayList<>();
                            namesSights = new ArrayList<>();

                             sightsLine = new PolylineOptions();


                            for(int i=0; i< sightsarray.length(); i++){
                                JSONObject sight = sightsarray.getJSONObject(i);
                               // Log.d("latitude:", String.valueOf(sight.getDouble("latitude")));

                                sightsLine.add(new LatLng(sight.getDouble("latitude"), sight.getDouble("longitude")));
                                namesSights.add(sight.getString("name"));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Polyline poly = routeMap.addPolyline(sightsLine);
                                    makeList();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
            return sightsLine;
        }
    }

}
