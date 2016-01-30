package sights.sights.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AppLogic.AsyncResponse;
import AppLogic.RouteHandler;
import HttpNetwork.PlacesDetailsTask;
import sights.sights.R;

public class PlaceDetailActivity extends AppCompatActivity implements AsyncResponse {

    private static final String API_KEY = "AIzaSyBV64nPqzGNmMWLBehsj2uxUrr-RlyJeS8";
    // private static final String API_KEY = "AIzaSyA8nQ35LYyIYSQUiHXviYnKIu0QqV16Yxs";

    private TextView mTitle;
    private TextView mAddress;
    private TextView mWebsite;

    private ImageView PlaceImage;
    private ImageButton addButton;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private String reference;
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

        PlacesDetailsTask placesTask = new PlacesDetailsTask();
        placesTask.delegate = this;

        placesTask.execute("https://maps.googleapis.com/maps/api/place/details/json?" + "placeid=" + reference + "&sensor=true" + "&key=" + API_KEY);

        mTitle = (TextView) findViewById(R.id.titleTextView);
        mAddress = (TextView) findViewById(R.id.detailsAdressTxt);
        mWebsite = (TextView) findViewById(R.id.detailsWebsiteTxt);

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
                int routeSize = RouteHandler.routes.size();
                if (routeSize == 0) {
                    Toast.makeText(PlaceDetailActivity.this,"Create a Route first!!!",Toast.LENGTH_LONG).show();
                }
                if (routeSize == 1) {
                    RouteHandler.routes.get(0).addPlace(reference, (String) mTitle.getText());
                    Toast.makeText(PlaceDetailActivity.this, "Place added!", Toast.LENGTH_LONG).show();
                } else {
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


        mTitle.setText(result.getString("name"));
        mAddress.setText(result.getString("formatted_address"));
        mWebsite.setText(result.getString("website"));
        mWebsite.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void chooseRouteDialog(){
        LayoutInflater inflater = LayoutInflater.from(PlaceDetailActivity.this);
        View chooseRouteDialog = inflater.inflate(R.layout.choose_route_dialog,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlaceDetailActivity.this);
        alertDialogBuilder.setView(chooseRouteDialog);

        final Spinner DialogSpinner = (Spinner) chooseRouteDialog.findViewById(R.id.chooseRouteSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this ,R.layout.support_simple_spinner_dropdown_item, RouteHandler.getAllRouteName());
        DialogSpinner.setAdapter(adapter);


        //setup dialog window
        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       RouteHandler.getRouteByName(DialogSpinner.getSelectedItem().toString(),reference, (String) mTitle.getText());
                        Toast.makeText(PlaceDetailActivity.this, "Place added!", Toast.LENGTH_LONG).show();
                        startActivity(getParentActivityIntent());
                    }
                });


        //Create Dialog
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();


    }


}



