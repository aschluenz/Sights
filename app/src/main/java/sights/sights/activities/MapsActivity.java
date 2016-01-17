package sights.sights.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import AppLogic.Searchhelper;
import AppLogic.GetPlacesAsyncRunner;
import sights.sights.R;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private EditText edtSearch;
    private boolean isSearchOpened = false;

    private GoogleMap mMap;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FloatingActionButton fab;

    private GoogleApiClient mGoogleApiClient;

        //Berlin
    LatLng MapsPoint = new LatLng(52.5074588,13.2847137);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setToolbar();
        setFab();
        setNavigationView();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(52.5072094, 13.1442686);
        mMap.addMarker(new MarkerOptions().position(MapsPoint).title("Marker in Berlin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(MapsPoint));
    }



    public void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Show Menu Icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFab(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null){
            fab.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    handleSearch();
                }
            });
        }
    }
    protected void handleSearch(){
        ActionBar ab = getSupportActionBar();

        if(isSearchOpened){
            ab.setDisplayShowCustomEnabled(false);
            ab.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);


        }else {//open the search entry
            ab.setDisplayShowCustomEnabled(true);
            ab.setCustomView(R.layout.search_bar);
            ab.setDisplayShowTitleEnabled(false);

            edtSearch= (EditText) ab.getCustomView().findViewById(R.id.edtSearch);

            edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener(){
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                    if(actionId == EditorInfo.IME_ACTION_SEARCH){
                        doSearch(edtSearch.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
            edtSearch.requestFocus();

            //Open Keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);




        }

    }

    private void doSearch(String SearchTerm) {

        Searchhelper sh = new Searchhelper();
        LatLng newLatLng = null;
        newLatLng = sh.determineLatLngFromAdress(this, SearchTerm);

        UpdateCamera(newLatLng);




    }

    @Override
    public void onBackPressed(){
        if(isSearchOpened){
            handleSearch();
            return;
        }
        super.onBackPressed();
    }

    private void UpdateCamera(LatLng newLatLng){
    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLatLng, 15);
        mMap.addMarker(new MarkerOptions().position(newLatLng).title("You are here"));
        mMap.animateCamera(update);
    }

    private String getPlacesfromLatLng(LatLng latLng){
        String key = Resources.getSystem().getString(R.string.google_maps_key);

        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location="+ latLng.latitude + "," + latLng.longitude +
                "&radius=1000&sensor=true" +
                "&rypes=museum|art_gallery"+
                "&key=" + key;

        return placesSearchStr;

        GetPlacesAsyncRunner places = new GetPlacesAsyncRunner();

    }


}

