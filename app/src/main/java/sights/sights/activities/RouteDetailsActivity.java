package sights.sights.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONException;

import AppLogic.RouteHandler;
import sights.sights.R;

public class RouteDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap routeMap;

    private String routeID;

    //Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
       // mContext = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.routeMap);
        mapFragment.getMapAsync(this);


        routeID = getIntent().getStringExtra("routeID");

        new Thread(new Runnable() {
            @Override
            public void run() {
                RouteHandler rh = new RouteHandler();
                try {
                    rh.getRoute(routeID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //todo was mit UI passiert....liste von von sights + karte anzeigen
                    }
                });
            }

        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        routeMap = googleMap;

    }
}
