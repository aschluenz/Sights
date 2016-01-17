package AppLogic;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.URL;

import HttpNetwork.NetworkHelper;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;


/**
 * Created by AndySchluenz on 17.01.16.
 */
public class GetPlacesAsyncRunner extends AsyncTask<String, Void, String> {


/*
    String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
            "json?location="+lat+","+lng+
            "&radius=1000&sensor=true" +
            "&types=food|bar|store|museum|art_gallery"+
            "&key=your_key_here";

*/


    @Override
    public String doInBackground(String...placesURL){
        StringBuilder placesBuilder = new StringBuilder();
        //process search parameter string(s)
        for (String placeSearchURL : placesURL) {
            NetworkHelper nth = new NetworkHelper();
            nth.get(placeSearchURL, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    
                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            });

        }
    }


}
