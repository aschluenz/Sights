package model;

import com.google.android.gms.location.places.Place;

import java.util.List;

import okhttp3.Route;

/**
 * Created by AndySchluenz on 11.01.16.
 */
public class User {
    public String id = "";
    public String email = "";
    public String firstname = "";
    public String lastname = "";
    public String password = "";
    public List<Route> routes;
    public List<Place> places;



}
