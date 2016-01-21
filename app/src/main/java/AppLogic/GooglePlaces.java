package AppLogic;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sights.sights.R;

/**
 * Created by andyschlunz on 19.01.16.
 */
public class GooglePlaces {





    // Google Places serach url's
    private static final String API_KEY = Resources.getSystem().getString(R.string.google_maps_key);

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private static final String TAG = GooglePlaces.class.getSimpleName();


    @SuppressWarnings("unused")
    private double _latitude;
    @SuppressWarnings("unused")
    private double _longitude;
    private double _radius;

    public List<Place> search(double latitude,double longitude,double radius, String types ){
        this._latitude = latitude;
        this._longitude= longitude;
        this._radius = radius;

        StringBuilder urlString = new StringBuilder(PLACES_SEARCH_URL);

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=" + _radius);
        urlString.append("&types=" + types);
        urlString.append("&sensor=false&key=" + API_KEY);

        Log.d(TAG, urlString.toString());

        try{
            String json = getJSON(urlString.toString());
            Log.d(TAG, json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");
            List<Place> arrayList = new ArrayList<Place>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    Place place = Place
                    .jsonToPontoReferencia((JSONObject) array.get(i),false);
                    Log.d("Places Services ", "" + place);
                    arrayList.add(place);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
            return arrayList;

        } catch (JSONException ex) {
            Logger.getLogger(GooglePlaces.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }

    protected String getJSON(String url) {
        return getUrlContents(url);
    }

    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}




