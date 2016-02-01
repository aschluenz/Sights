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
    private float zoomLevel;
    public LatLng newMarkerPos = null;



    private String placeOptions;

    public GetPlacesAsyncForMap(GoogleMap mMap,LatLng position,float zoom, String placeOptions) {
        this.position = position;
        this.placeOptions = placeOptions;
        this.mMap = mMap;
        this.zoomLevel= zoom;

    }

    @Override
    protected List<Place> doInBackground(Void... params) {

        GooglePlacesService service = new GooglePlacesService();
        List<Place> findPlaces = service.findPlaces(position.latitude,position.longitude,calcZoomLeveltoMeter(zoomLevel), placeOptions);

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

