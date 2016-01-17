package AppLogic;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

/**
 * Created by AndySchluenz on 17.01.16.
 */
public class Searchhelper {


    public LatLng determineLatLngFromAdress(Context AppContext,  String strAdress){
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(AppContext, Locale.getDefault());
        List<Address> geoResults = null;

        try{
            geoResults = geocoder.getFromLocationName(strAdress, 1);
            while (geoResults.size() == 0){
                geoResults = geocoder.getFromLocationName(strAdress, 1);
            }
            if (geoResults.size()>0) {
                Address addr = geoResults.get(0);
                latLng = new LatLng(addr.getLatitude(), addr.getLongitude());
            }
    } catch (Exception e) {
        System.out.print(e.getMessage());
    }
        return latLng;
    }
}
