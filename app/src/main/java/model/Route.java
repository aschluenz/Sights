package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import HttpNetwork.NetworkHelper;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class Route {

    private String Name;
    private ArrayList<Sight> sightsList;
    private String Id;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public String getId() {
        return Id;
    }

    public void setUserid(String userid) {
        this.userid = userid;

    }



    public Route(String name) {
        this.Name = name;
        this.sightsList = new ArrayList<Sight>();
        this.userid = User.getInstance().getUserId();
    }

    public Route(String Name, String ID){
        this.Name = Name;
        this.sightsList = new ArrayList<Sight>();
        this.userid = User.getInstance().getUserId();
        this.Id = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void addPlace(String name, String placeId, double lat, double lng) {
        Sight sight = new Sight(name,placeId);
        sight.setLatitude(lat);
        sight.setLongitude(lng);
        sightsList.add(sight);
    }

    public void addPlace(String name, String placeId) {
        Sight sight = new Sight(name,placeId);
        sightsList.add(sight);
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<Sight> getSightsList() {
        return sightsList;
    }


}
