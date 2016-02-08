package AppLogic;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import HttpNetwork.NetworkHelper;
import model.Place;
import model.Route;
import model.Sight;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class RouteHandler {

    //private  Context context;

    private String url = "http://nodejs-sightsapp.rhcloud.com/route/";

    //holdes all route objects local;
    public static List<Route> routes = new ArrayList<Route>();

    public static HashMap<String, String> routesByName = new HashMap<String, String>();

    public static Route oneRouteByID;


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



   public Route getRoute(String id)   {
       /* String _url = url + "get";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("routeID", id);

        NetworkHelper nh = new NetworkHelper();
        nh.post(url, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("getRouteById exception", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    response
                    return oneRouteByID;

                } else {
                    Log.d("getRouteById resp err: ", response.message());
                }
            }
        });

*/
return null;
    };

    public static void addSightToRouteByRouteName(String RouteName, String SightId, String SightName,double lat,double lng){
        int result = 0;
        for(int i = 0; i < routes.size(); i++){
            if (routes.get(i).getName().equals(RouteName)){
              result = i;
              break;
            }
        }
        routes.get(result).addPlace(SightName,SightId, lat, lng);

        updateRouteToServer(routes.get(result));

    }


   /* public void getAllRoutes(String userid){
        String _url = url + "get";


    };

    */

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
        String _url = url + "add";

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
                   //TODO RouteId rausholen
                   try {
                       String routeId = new JSONObject(res).getJSONObject("routeObject").getString("routeID");
                       String routeName = new JSONObject(res).getJSONObject("routeObject").getString("title");
                       addRouteiD(routeId, routeName);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }else {
                   String res = response.body().string();
                   Log.d("Save Route failed: ", res);
               }
            }
        });
    }

    private void addRouteiD(String routeId, String Name) {
        for(int i = 0; i< routes.size(); i++){
            Route route = routes.get(i);
            if(route.getName().equals(Name)){
                route.setId(routeId);
                routes.add(i, route);
                Log.d("gesetzte RouteID:", routes.get(i).getId());
                break;
            }
        }
    }

    public HashMap<String,String> getAllRouteFromUser(String UserId) throws JSONException {
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
                             //   JSONObject job = new JSONObject(res);
                                JSONArray jsonArray = new JSONArray(res);
                                for(int i=0; i < jsonArray.length(); i++) {
                                    JSONObject jsonRoute = (JSONObject) jsonArray.get(i);
                                    Log.d("jsonRoute Object: ", jsonRoute.toString());
                                    routesByName.put(jsonRoute.getString("title"), jsonRoute.getString("routeID"));
                                }
                                } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

      return routesByName;
    }


    public static void updateRouteToServer(Route route) {
        String _url =  "http://nodejs-sightsapp.rhcloud.com/route/update";

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for(int i= 0; i<route.getSightsList().size();i++){
            Sight sight =route.getSightsList().get(i);
            JSONObject sightJSON = new JSONObject();
            try {
                sightJSON.put("placeID", sight.getPlaceID());
                sightJSON.put("name", sight.getName());
                sightJSON.put("latitude", sight.getLatitude());
                sightJSON.put("longitude", sight.getLongitude());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(sightJSON);
        }

        try {

            jsonObject.put("routeID", route.getId());
            jsonObject.put("title", route.getName());
            jsonObject.put("userID", route.getUserid());
            jsonObject.put("sights", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkHelper nh = new NetworkHelper();
        nh.post(_url, jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("added sight failed:", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful());
                Log.d("added sight:", response.message());

            }
        });


    }



}
