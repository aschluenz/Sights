package AppLogic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import HttpNetwork.NetworkHelper;
import model.Place;
import model.Route;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class RouteHandler {

    private String url = "http://nodejs-sightsapp.rhcloud.com/route/add";

    //holdes all route objects;
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
        JSONArray jsonArray = new JSONArray();
        JSONArray sightArray = new JSONArray(route.getSightsList());

        JSONObject object = new JSONObject();
        object.put("title", route.getName());
        object.put("userID", route.getUserid());
        object.put("tags", jsonArray);
        object.put("city", "");
        object.put("sights",sightArray);

        NetworkHelper nh = new NetworkHelper();
        nh.post(url, object.toString(), new Callback() {
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


}
