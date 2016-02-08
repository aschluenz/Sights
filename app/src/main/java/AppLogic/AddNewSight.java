package AppLogic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import HttpNetwork.NetworkHelper;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andyschlunz on 04.02.16.
 */
public class AddNewSight extends AsyncTask<String,Void,Void> {




    String url = "http://nodejs-sightsapp.rhcloud.com/sight/add";

    @Override
    protected Void doInBackground(String... params) {
        String strTitle = params[0];
        String strLat = params[1];
        String strLng  = params[2];
        String strDiscription = params[3];
        String strWebsite = params[4];
        String userID = params[5];

        //TODO check json creating for backend

        //create json
        JSONObject jo = new JSONObject();
        try {
            jo.put("title", strTitle);
            jo.put("describtion",strDiscription);
            jo.put("website", strWebsite);
            jo.put("userID", userID);
            jo.put("latitude", strLat);
            jo.put("longitude", strLng);
        }catch (JSONException j){
            Log.d("Jsonerror: ", j.getMessage());
        }
        String json = jo.toString();


        NetworkHelper nh = new NetworkHelper();
        nh.post(url, json, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("something went wrong:", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.d("New Sight saved...", response.message());
                }
            }
        });
        return null;
    }
}
