package sights.sights.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import AppLogic.AddNewSight;
import AppLogic.PreferenceData;
import AppLogic.Searchhelper;
import sights.sights.R;

public class AddSightActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;

    String userID = "abc";  //PreferenceData.getPrefLoggedinUserId(getApplicationContext());

    EditText sTitle;
    EditText sAddress;
    EditText sDiscription;
    EditText sWebsite;
    Button addSigts;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("add new Sight");

        sTitle = (EditText) findViewById(R.id.input_Title);
        sAddress = (EditText) findViewById(R.id.input_adress);
        sDiscription = (EditText) findViewById(R.id.input_description);
        sWebsite = (EditText) findViewById(R.id.input_website);
        addSigts = (Button) findViewById(R.id.btn_add_sight);
        addSigts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String strTitle = sTitle.getText().toString();
                String strAddress = sAddress.getText().toString();
                String strDiscription =sDiscription.getText().toString();
                String strWebsite= sWebsite.getText().toString();

                if(strTitle.matches("")){
                    Toast.makeText(getBaseContext(), "You did not enter a Title", Toast.LENGTH_SHORT).show();
                }
                if(strAddress.matches("")){
                    Toast.makeText(getBaseContext(), "You did not enter a Address", Toast.LENGTH_SHORT).show();
                }
                if(strDiscription.matches("")){
                    Toast.makeText(getBaseContext(), "You did not enter a Discription", Toast.LENGTH_SHORT).show();
                }
                else{
                    Searchhelper sh = new Searchhelper();
                    LatLng address = sh.determineLatLngFromAdress(getBaseContext(), strAddress);


                    new AddNewSight().execute(
                            strTitle,
                            Double.toString(address.latitude),
                            Double.toString(address.longitude),
                            strDiscription,
                            strWebsite,
                            userID);

                    //close activity
                    finish();
                }

            }
        });

        setFab();
        setSupportActionBar(toolbar);
    }

    private void setFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
         //   Bitmap photo = (Bitmap) data.getExtras().get("data");
          //  imageView.setImageBitmap(photo);
        }
    }

}
