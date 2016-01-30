package HttpNetwork;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import AppLogic.GooglePlacesService;
import model.Place;
import sights.sights.R;

/**
 * Created by AndySchluenz on 24.01.16.
 */
public class GetPlacesAsyncForMap extends AsyncTask<Void,Void,List<Place>> {

    private List<Marker> mymarker;
    private GoogleMap mMap;
    private LatLng position;
    public LatLng newMarkerPos = null;


    private String placeOptions;

    public GetPlacesAsyncForMap(GoogleMap mMap,LatLng position, String placeOptions) {
        this.position = position;
        this.placeOptions = placeOptions;
        this.mMap = mMap;

    }

    @Override
    protected List<Place> doInBackground(Void... params) {

        GooglePlacesService service = new GooglePlacesService();
        List<Place> findPlaces = service.findPlaces(position.latitude,position.longitude,1600, placeOptions);

       /* placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

        for(int i = 0; i < findPlaces.size(); i++){
            Place placeDetail = findPlaces.get(i);
            placeDetail.getIcon();

            System.out.println( placeDetail.getName());
            placeName[i] = placeDetail.getName();

            imageUrl[i] = placeDetail.getIcon();
        } */
        return findPlaces;
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

