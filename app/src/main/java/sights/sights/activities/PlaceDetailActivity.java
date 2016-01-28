package sights.sights.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import HttpNetwork.PlacesDetailsTask;
import sights.sights.R;

public class PlaceDetailActivity extends AppCompatActivity
{

    private static final String API_KEY = "AIzaSyBV64nPqzGNmMWLBehsj2uxUrr-RlyJeS8";
    private TextView mTitle;
    private ImageView PlaceImage;
    private ImageButton addButton;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        String reference = getIntent().getStringExtra("reference");

        Log.d("Details Reference; ", reference);

        PlacesDetailsTask placesTask = new PlacesDetailsTask();

        placesTask.execute("https://maps.googleapis.com/maps/api/place/details/json?" + "reference=" + reference + "&sensor=true" + "&key=" + API_KEY);

        }




    }



