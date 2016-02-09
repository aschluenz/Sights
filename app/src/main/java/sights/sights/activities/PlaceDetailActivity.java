package sights.sights.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import AppLogic.AsyncResponse;
import AppLogic.RouteHandler;
import HttpNetwork.NetworkHelper;
import HttpNetwork.PlacesDetailsTask;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

public class PlaceDetailActivity extends AppCompatActivity implements AsyncResponse {

    private static final String API_KEY = "AIzaSyBV64nPqzGNmMWLBehsj2uxUrr-RlyJeS8";

    private TextView mTitle;
    private TextView mAddress;
    private TextView mWebsite;

    private double lat;
    private double lng;


    private ImageView PlaceImage;
    private ImageButton addButton;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private String reference;
    private boolean isSavable;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);


        reference = getIntent().getStringExtra("reference");

        Log.d("Details Reference; ", reference);


        if(reference.contains("/")){
            new SightsfromServer().execute(reference);

        }
        else {

            PlacesDetailsTask placesTask = new PlacesDetailsTask();
            placesTask.delegate = this;

            placesTask.execute("https://maps.googleapis.com/maps/api/place/details/json?" + "placeid=" + reference + "&sensor=true" + "&key=" + API_KEY);

        }
        mTitle = (TextView) findViewById(R.id.titleTextView);
        mAddress = (TextView) findViewById(R.id.detailsAdressTxt);
        mWebsite = (TextView) findViewById(R.id.detailsWebsiteTxt);

        mWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebsite.getText().toString()));
                startActivity(browserIntent);
            }
        });

        setAddBtn();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    private void setAddBtn() {
        addButton = (ImageButton) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int routeSize = RouteHandler.routesByName.size();
                if (routeSize == 0) {
                    Toast.makeText(PlaceDetailActivity.this,"Create a Route first!!!",Toast.LENGTH_LONG).show();
                }
                else {
                    chooseRouteDialog();
                }


            }
        });
    }

    @Override
    public void prozessFinish(String output) throws JSONException {
        Log.d("Interface output:", output);
        JSONObject job = new JSONObject(output);
        JSONObject result = job.getJSONObject("result");
        lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        lng = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        mTitle.setText(result.getString("name"));
        mAddress.setText(result.getString("formatted_address"));
        mWebsite.setText(result.getString("website"));
        mWebsite.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void prozessFinish2(String output) throws JSONException {
        Log.d("own output:", output);
        JSONObject result = new JSONObject(output);
       // JSONObject result = job.getJSONObject("");
        lat = Double.valueOf(result.getString("latitude"));
        lng = Double.valueOf(result.getString("longitude"));

        mTitle.setText(result.getString("title"));
        //mAddress.setText(result.getString(""));
        mWebsite.setText(result.getString("website"));
        mWebsite.setMovementMethod(LinkMovementMethod.getInstance());

    }


    public void chooseRouteDialog(){
        LayoutInflater inflater = LayoutInflater.from(PlaceDetailActivity.this);
        View chooseRouteDialog = inflater.inflate(R.layout.choose_route_dialog,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceDetailActivity.this);
        alertDialogBuilder.setView(chooseRouteDialog);

        final Spinner DialogSpinner = (Spinner) chooseRouteDialog.findViewById(R.id.chooseRouteSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this ,R.layout.support_simple_spinner_dropdown_item, RouteHandler.getRoutesfromList());
        DialogSpinner.setAdapter(adapter);

        //setup dialog window
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       RouteHandler.addSightToRouteByRouteName(DialogSpinner.getSelectedItem().toString(), reference, (String) mTitle.getText(),lat,lng);
                        Toast.makeText(PlaceDetailActivity.this, "Place added!", Toast.LENGTH_LONG).show();
                    }
                });

        //Create Dialog
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }


    public class SightsfromServer extends AsyncTask<String,Void,Void>{
            String Url = "http://nodejs-sightsapp.rhcloud.com/sight/get";

        @Override
        protected Void doInBackground(String... params) {

            JSONObject id = new JSONObject();
            try {
                id.put("placeID", params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            NetworkHelper nh = new NetworkHelper();
            nh.post(Url, id.toString(), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    if(response.isSuccessful()){

                       final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    prozessFinish2(res);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }


                }
            });


            return null;
        }
    }


}



