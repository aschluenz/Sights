package AppLogic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpNetwork.NetworkHelper;
import model.Route;
import model.Sight;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class RouteHandler {

    private String url = "http://nodejs-sightsapp.rhcloud.com/route/";

    //holdes all route objects local;
    public static List<Route> routes = new ArrayList<Route>();

    public static HashMap<String, String> routesByName = new HashMap<String, String>();


    // Struktur = ID : Name
    HashMap<String, String> allSightsfromUser = new HashMap<>();


    public boolean createRoute(String routeName, String UserID) {
        Route route = new Route(routeName, UserID);
        Log.d("Route wurde erstellt: ", route.getName());
        routes.add(route);
        try {
            saveRoute(route);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void addSightToRouteByRouteName(String RouteName, String SightId, String SightName, double lat, double lng) {
        // hole mir dir route id
        String Routeid = routesByName.get(RouteName);
        Route route = new Route(RouteName, Routeid);
        route.addPlace(SightName, SightId, lat, lng);
        updateRouteToServer(route);

    }

    public static String[] getRoutesfromList() {
        int routenum = routesByName.size();
        String[] routesList = new String[routenum];
        int i = 0;
        for (String key : routesByName.keySet()) {
            routesList[i] = key;
            i++;
        }

        return routesList;
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
        object.put("sights", sightArray);

        NetworkHelper nh = new NetworkHelper();
        nh.post(_url, object.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("Save Route exection", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
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
                } else {
                    String res = response.body().string();
                    Log.d("Save Route failed: ", res);
                }
            }
        });
    }

    private void addRouteiD(String routeId, String Name) {
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.getName().equals(Name)) {
                route.setId(routeId);
                routes.add(i, route);
                Log.d("gesetzte RouteID:", routes.get(i).getId());
                break;
            }
        }
    }

    public HashMap<String, String> getAllRouteFromUser(String UserId) throws JSONException {
        String _url = "http://nodejs-sightsapp.rhcloud.com/route/get";
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
                                for (int i = 0; i < jsonArray.length(); i++) {
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
        String _url = "http://nodejs-sightsapp.rhcloud.com/route/update";

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < route.getSightsList().size(); i++) {
            Sight sight = route.getSightsList().get(i);
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
                if (response.isSuccessful()) ;
                Log.d("added sight:", response.message());

            }
        });
    }


    public HashMap<String, String> getAllSightsByUserId(String UserID) {
        String _url = "http://nodejs-sightsapp.rhcloud.com/sight/get";


        JSONObject useridObj = new JSONObject();
        try {
            useridObj.put("userID", UserID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkHelper nh = new NetworkHelper();

        nh.post(_url, useridObj.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("allSightsFromUser:", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("allsightuser", response.body().toString());

                    try {
                        JSONArray sights = new JSONArray(response.body().string());
                        for (int i = 0; i < sights.length(); i++) {
                            JSONObject jsonObject = (JSONObject) sights.get(i);
                            allSightsfromUser.put(jsonObject.getString("title"), jsonObject.getString("placeID"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return allSightsfromUser;
    }
}
