package HttpNetwork;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import AppLogic.PlaceDetailsJSONPARSER;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class PlacesDetailsTask extends AsyncTask<String, Integer,String> {

    String data = null;


    @Override
    protected String doInBackground(String... url) {
        try{
            data = downloadURl(url[0]);

        }catch(Exception e){
            Log.d("Background Task", e.toString());

        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        ParserTask parserTask = new ParserTask();

        parserTask.execute(result);
    }

    private String downloadURl(String strURL) throws IOException{

        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try{
            URL url = new URL(strURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer stringBuffer = new StringBuffer();

            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
            bufferedReader.close();

        }catch (Exception e){
            Log.d("Exception while downloading: ", e.toString());
        }finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return null;
    };



    private class ParserTask extends AsyncTask<String, Integer,HashMap<String,String>>{

        JSONObject jsonObject;

        @Override
        protected HashMap<String, String> doInBackground(String... jsonData) {
            HashMap<String, String> hashPlaceDetails = null;
            PlaceDetailsJSONPARSER placeDetailsJSONPARSER = new PlaceDetailsJSONPARSER();

            try{
                jsonObject = new JSONObject(jsonData[0]);

                hashPlaceDetails = placeDetailsJSONPARSER.parse(jsonObject);


            }catch (Exception e){
                Log.d("Execution",e.toString());
            }
            return hashPlaceDetails;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> hashPlaceDetails) {
            String name = hashPlaceDetails.get("name");
            String icon = hashPlaceDetails.get("icon");
            String vicinity = hashPlaceDetails.get("vicinity");
            String latitude =hashPlaceDetails.get("lat");
            String longitude = hashPlaceDetails.get("lng");
            String formatted_address = hashPlaceDetails.get("formatted_adress");
            String formatted_phone = hashPlaceDetails.get("formatted_phone");
            String website = hashPlaceDetails.get("website");
            String rating = hashPlaceDetails.get("rating");
            String phone_number = hashPlaceDetails.get("international_phone_number");
            String url = hashPlaceDetails.get("url");
        }
    }

}


