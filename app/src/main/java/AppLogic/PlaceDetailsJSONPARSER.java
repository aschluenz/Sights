package AppLogic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class PlaceDetailsJSONPARSER {

    public HashMap<String,String> parse(JSONObject jsonObject){
        JSONObject jsonObjectPlaceDetails = null;

        try{
            jsonObjectPlaceDetails = jsonObject.getJSONObject("result");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return getPlaceDetails(jsonObjectPlaceDetails);
    }

    private HashMap<String, String> getPlaceDetails(JSONObject jsonObjectPlaceDetails) {

        HashMap<String, String> hashPlaceDetails = new HashMap<String, String>();

        String name = "-NA-";
        String icon = "-NA-";
        String vicinity ="-NA-";
        String latitude ="-NA-";
        String longitude = "-NA-";
        String formatted_address = "-NA-";
        String formatted_phone = "-NA-";
        String website = "-NA-";
        String rating = "-NA-";
        String phone_number = "-NA-";
        String url = "-NA-";

        try {
            if (!jsonObjectPlaceDetails.isNull("name")){
                name = jsonObjectPlaceDetails.getString("name");
            }
            if (!jsonObjectPlaceDetails.isNull("icon")) {
                icon = jsonObjectPlaceDetails.getString("icon");
            }
            if (!jsonObjectPlaceDetails.isNull("vicinity")) {
                vicinity = jsonObjectPlaceDetails.getString("vicinity");
            }
            if (!jsonObjectPlaceDetails.isNull("formatted_address")) {
                formatted_address = jsonObjectPlaceDetails.getString("formatted_adress");
            }
            if (!jsonObjectPlaceDetails.isNull("formatted_phone")) {
                formatted_phone = jsonObjectPlaceDetails.getString("formatted_phone_number");
            }
            if (!jsonObjectPlaceDetails.isNull("website")) {
                website = jsonObjectPlaceDetails.getString("website");
            }
            if (!jsonObjectPlaceDetails.isNull("rating")) {
                rating = jsonObjectPlaceDetails.getString("rating");
            }
            if (!jsonObjectPlaceDetails.isNull("internatinal_phone_number")) {
                phone_number = jsonObjectPlaceDetails.getString("international_phone_number");
            }
            if (!jsonObjectPlaceDetails.isNull("url")) {
                url = jsonObjectPlaceDetails.getString("url");
            }
            latitude = jsonObjectPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude =jsonObjectPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lng");

            hashPlaceDetails.put("name", name);
            hashPlaceDetails.put("icon",icon);
            hashPlaceDetails.put("vicinity", vicinity);
            hashPlaceDetails.put("lat", latitude);
            hashPlaceDetails.put("lng", longitude);
            hashPlaceDetails.put("formatted_address", formatted_address);
            hashPlaceDetails.put("formatted_phone", formatted_phone);
            hashPlaceDetails.put("website", website);
            hashPlaceDetails.put("rating", rating);
            hashPlaceDetails.put("phone_number", phone_number);
            hashPlaceDetails.put("url", url);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return hashPlaceDetails;
    }
}
