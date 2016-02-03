package sights.sights.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import AppLogic.Adapter.InfoWindowAdapterMarker;
import AppLogic.PreferenceData;
import AppLogic.RouteHandler;
import AppLogic.Searchhelper;
import HttpNetwork.GetPlacesAsyncForMap;
import sights.sights.R;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    protected LocationManager locationManager;

    private EditText edtSearch;
    private boolean isSearchOpened = false;

    private int currentSelectedPosition;


    private GoogleMap mMap;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    Context mContext;

    RouteHandler rh = new RouteHandler();
    private GoogleApiClient mGoogleApiClient;


    LatLng accualCameraPos = new LatLng(52.5074588, 13.2847137);
    LatLng MapsPoint = accualCameraPos;

    float zoomlevel = 16;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);

        setFab();
        setUpNavigationDrawer();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        Intent a = new Intent(MapsActivity.this, PlacesListActivity.class);
                        Log.d("accualCamera Lat", String.valueOf(accualCameraPos.latitude));
                        
                        
                        a.putExtra("lat",  String.valueOf(accualCameraPos.latitude));
                        a.putExtra("lng", String.valueOf(accualCameraPos.longitude));
                        startActivity(a);

                        currentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_2:
                        Intent b = new Intent(MapsActivity.this, AddSightActivity.class);
                        startActivity(b);
                        currentSelectedPosition = 1;
                        return true;
                    case R.id.navigation_item_3:
                        Intent c = new Intent(MapsActivity.this, MyRoutesActivity.class);
                        startActivity(c);
                        currentSelectedPosition = 2;
                        return true;
                    case R.id.navigation_item_4:
                        //  Intent d = new Intent(MapsActivity.this,My);
                        currentSelectedPosition = 3;
                        return true;
                    case R.id.navigation_item_5:
                        logout();
                        currentSelectedPosition = 4;
                        return true;
                    default:
                        return true;
                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void logout() {


        LoginManager.getInstance().logOut();
        PreferenceData.clearLoggedInId(this);

        Intent i = new Intent(this, Login.class);
        startActivity(i);

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
        mMap.setInfoWindowAdapter(new InfoWindowAdapterMarker(mContext));

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(52.5072094, 13.1442686);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MapsPoint, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        appMarker(zoomlevel);

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                mMap.addMarker(new MarkerOptions().position(cameraPosition.target).title("Marker in Berlin"));


                //TODO mMap.clear();
                zoomlevel = mMap.getCameraPosition().zoom;
                accualCameraPos = mMap.getCameraPosition().target;
                String bla = new Float(zoomlevel).toString();
                Log.d("Zoomlevel:", bla);
                Log.d("accualCameraPosition: ", accualCameraPos.toString());
                appMarker(zoomlevel);


            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getBaseContext(), PlaceDetailActivity.class);
                String placeId = marker.getSnippet();
                Log.d("Placeid by click:", placeId);
                intent.putExtra("reference", placeId);

                startActivity(intent);


            }


        });

    }

/*
    private GoogleMap.OnCameraChangeListener onCameraChange() {
        zoomlevel = mMap.getCameraPosition().zoom;
        accualCameraPos = mMap.getCameraPosition().target;
        String bla = new Float(zoomlevel).toString();
        Log.d("Zoomlevel:", bla);
        Log.d("accualCameraPosition: ", accualCameraPos.toString());

        return null;
    }

    */

    public void appMarker(float zoom) {

        new GetPlacesAsyncForMap(mMap, accualCameraPos, zoom, "museum").execute();

    }


    public void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void setUpNavigationDrawer() {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                handleSearch();
                return true;

            case R.id.action_find_location:
                showCurrentLocation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openCreateRouteDialog();
                }
            });
        }
    }

    public void openCreateRouteDialog() {
        LayoutInflater inflater = LayoutInflater.from(MapsActivity.this);
        View createRouteDialog = inflater.inflate(R.layout.create_route_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapsActivity.this);
        alertDialogBuilder.setView(createRouteDialog);

        final EditText routeName = (EditText) createRouteDialog.findViewById(R.id.editTextCreateRoute);

        //setup dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean result = rh.createRoute(routeName.getText().toString());

                    }
                })
                .setNegativeButton("Chancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        //Create Dialog
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

    }

    protected void handleSearch() {
        final ActionBar ab = getSupportActionBar();

        if (isSearchOpened) {
            ab.setDisplayShowCustomEnabled(false);
            ab.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);


        } else {//open the search entry
            ab.setDisplayShowCustomEnabled(true);
            ab.setCustomView(R.layout.search_bar);
            ab.setDisplayShowTitleEnabled(false);

            edtSearch = (EditText) ab.getCustomView().findViewById(R.id.edtSearch);

            edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch(edtSearch.getText().toString());
                        ab.setDisplayShowCustomEnabled(false);
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


        edtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

        Searchhelper sh = new Searchhelper();
        LatLng newLatLng = null;
        newLatLng = sh.determineLatLngFromAdress(this, SearchTerm);

        UpdateCamera(newLatLng);

    }

    /* @Override
     public void onBackPressed() {

         }
         super.onBackPressed();
     }
 */
    private void UpdateCamera(LatLng newLatLng) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLatLng, 15);
        mMap.addMarker(new MarkerOptions().position(newLatLng).title("You are here"));
        mMap.animateCamera(update);
    }


    protected void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            MapsPoint = new LatLng(location.getLatitude(),location.getLongitude());
        }

    }



    }

