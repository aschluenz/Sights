package HttpNetwork;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import AppLogic.GooglePlacesService;
import model.Place;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

/**
 * Created by AndySchluenz on 24.01.16.
 */
public class GetPlacesAsyncForMap extends AsyncTask<Void,Void,List<Place>> {

    private List<Marker> mymarker;
    private GoogleMap mMap;
    private LatLng position;
    private float zoomLevel;
    public LatLng newMarkerPos = null;


    private List<Place> allPlaces = new ArrayList<>();
    List<Place> ourPlaces = new ArrayList<>();

    private String placeOptions;

    public GetPlacesAsyncForMap(GoogleMap mMap,LatLng position,float zoom, String placeOptions) {
        this.position = position;
        this.placeOptions = placeOptions;
        this.mMap = mMap;
        this.zoomLevel= zoom;

    }

    @Override
    protected List<Place> doInBackground(Void... params) {



        String url = "http://nodejs-sightsapp.rhcloud.com/sight/all";
        /*
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude", position.latitude);
            jsonObject.put("longitude", position.longitude);
            jsonObject.put("r", calcZoomLeveltoMeter(zoomLevel));

        } catch (JSONException e) {
            e.printStackTrace();
        }

       */
        NetworkHelper nh = new NetworkHelper();

        nh.get(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("getOwnPlaces erro", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();

                Log.d("response hilfe:",res);
                if(response.isSuccessful()){
                    try {
                        JSONArray places = new JSONArray(res);
                        for(int i =0; i< places.length(); i++){
                            JSONObject jsonObject1 = (JSONObject) places.get(i);

                            Place place = new Place();
                            place.setLatitude(Double.valueOf(jsonObject1.getString("latitude")));
                            place.setLongitude(Double.valueOf(jsonObject1.getString("longitude")));
                            place.setName(jsonObject1.getString("title"));
                            place.setId(jsonObject1.getString("placeID"));
                            allPlaces.add(place);
                            Log.d("ower places:" ,allPlaces.get(i).toString());
                        }
                        //addMaker(ourPlaces);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else
                    Log.d("reponse error:", response.message());


            }

        });



        GooglePlacesService service = new GooglePlacesService();
        List<Place> findPlaces = service.findPlaces(position.latitude,position.longitude,calcZoomLeveltoMeter(zoomLevel), placeOptions);

        allPlaces.addAll(findPlaces);


        return allPlaces;
    }

    private void addMaker(List<Place> allPlaces) {
        for (Place place : allPlaces) {
            newMarkerPos = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(newMarkerPos).title(place.getName()).snippet(place.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapspin)));
        }
    }

    private int calcZoomLeveltoMeter(float zoomLevel) {
        int meter=1000;
        switch ((int)zoomLevel){
            case 10:
                 meter =  1155581;
                break;
            case 11: meter = 577790;break;
            case 12: meter =288895 ;break;
            case 13: meter =144447 ;break;
            case 14: meter =72223 ;break;
            case 15: meter = 36111;break;
            case 16: meter =18055;break;
            case 17: meter = 9027;break;
            case 18: meter = 4513;break;
            case 19: meter = 2256;break;
            case 20: meter = 1128;break;

        }
        return meter / 10;
    }


    @Override
    protected void onPostExecute(List<Place> places) {
        super.onPostExecute(places);
        for (Place place:places){
           newMarkerPos = new LatLng(place.getLatitude(),place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(newMarkerPos).title(place.getName()).snippet(place.getId()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapspin)));
    }
    }
}

