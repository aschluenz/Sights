package sights.sights.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import AppLogic.AsyncResponse;
import HttpNetwork.PlacesDetailsTask;
import sights.sights.R;

public class PlaceDetailActivity extends AppCompatActivity implements AsyncResponse
{

    private static final String API_KEY = "AIzaSyBV64nPqzGNmMWLBehsj2uxUrr-RlyJeS8";
   // private static final String API_KEY = "AIzaSyA8nQ35LYyIYSQUiHXviYnKIu0QqV16Yxs";

    private TextView mTitle;
    private TextView mAddress;
    private TextView mWebsite;

    private ImageView PlaceImage;
    private ImageButton addButton;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private String placeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        String reference = getIntent().getStringExtra("reference");

        Log.d("Details Reference; ", reference);

        PlacesDetailsTask placesTask = new PlacesDetailsTask();
        placesTask.delegate = this;

        placesTask.execute("https://maps.googleapis.com/maps/api/place/details/json?" + "placeid=" + reference + "&sensor=true" + "&key=" + API_KEY);

        mTitle = (TextView) findViewById(R.id.titleTextView);
        mAddress = (TextView) findViewById(R.id.detailsAdressTxt);
        mWebsite = (TextView) findViewById(R.id.detailsWebsiteTxt);

        setAddBtn();
        }

    private void setAddBtn() {
        addButton = (ImageButton) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void prozessFinish(String output) throws JSONException {
        Log.d("Interface output:", output);
        JSONObject job = new JSONObject(output);
        JSONObject result = job.getJSONObject("result");


        mTitle.setText(result.getString("name"));
        mAddress.setText(result.getString("formatted_address"));
        placeid = result.getString("place_id");
        mWebsite.setText(result.getString("website"));
        mWebsite.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());


    }
}



