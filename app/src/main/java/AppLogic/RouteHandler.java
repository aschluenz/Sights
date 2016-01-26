package AppLogic;

import android.util.Log;

import java.util.List;

import model.Place;
import model.Route;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class RouteHandler {

    private List<Route> routes;



    public boolean createRoute(String routeName){
        Route route = new Route(routeName);
        Log.d  ("Route wurde erstellt: ", route.getName());
        routes.add(route);
        return true;
    };

    public void addPlacetoRoute(String routeId, Place newPlace){

    }


    public void getRoute(String id){

    };

    public void getAllRoutes(String userid){

    };
}
