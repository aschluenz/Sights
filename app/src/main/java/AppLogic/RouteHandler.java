package AppLogic;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpNetwork.NetworkHelper;
import model.Place;
import model.Route;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class RouteHandler {

    public AsyncResponse delegate = null;


    private  Context context;

    private String url = "http://nodejs-sightsapp.rhcloud.com/route/";

    //holdes all route objects local;
    public static List<Route> routes = new ArrayList<Route>();

    public boolean createRoute(String routeName)  {
        Route route = new Route(routeName);
        Log.d("Route wurde erstellt: ", route.getName());
        routes.add(route);
        try {
            saveRoute(route);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    };



    public void getRoute(String id){

    };

    public static void getRouteByName(String RouteName,String SightId, String SightName){
        int result = 0;
        for(int i = 0; i < routes.size(); i++){
            if (routes.get(i).getName().equals(RouteName)){
              result = i;
              break;
            }
        }
        routes.get(result).addPlace(SightName,SightId);

    }

    public void getAllRoutes(String userid){

    };

    public static String[] getAllRouteName(){
        int routenum = routes.size();
        String[] routenames = new String[routenum];
        for (int i = 0; i < routenum; i++
             ) {
           routenames[i] = routes.get(i).getName();

        }
        return routenames;
    }

    public void saveRoute(Route route) throws JSONException {
        String _url = url + "http://nodejs-sightsapp.rhcloud.com/route/add";

        JSONArray jsonArray = new JSONArray();
        JSONArray sightArray = new JSONArray(route.getSightsList());

        JSONObject object = new JSONObject();
        object.put("title", route.getName());
        object.put("userID", route.getUserid());
        object.put("tags", jsonArray);
        object.put("city", "");
        object.put("sights",sightArray);

        NetworkHelper nh = new NetworkHelper();
        nh.post(_url, object.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Save Route exection", e.getMessage());

            }
            @Override
            public void onResponse(Response response) throws IOException {
               if(response.isSuccessful()){
                   String res = response.body().string();
                   Log.d("Save Route response: ", res);
                   //TODO get RouteID out of the JsonString
                }else {
               }
            }
        });
    }




    public void getAllRouteFromUser(String UserId) throws JSONException {


        String _url =  "http://nodejs-sightsapp.rhcloud.com/route/get";

        JSONObject useridObj = new JSONObject();
        useridObj.put("userID", UserId);

        NetworkHelper nh = new NetworkHelper();
        nh.post(_url, useridObj.toString(), new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.d("All Routes exeception", e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String res = response.body().string();
                            Log.d("all routes response: ", res);
                            try {
                                delegate.prozessFinish(res);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

    }

}
