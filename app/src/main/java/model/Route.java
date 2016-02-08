package model;

import java.util.ArrayList;

import HttpNetwork.NetworkHelper;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class Route {

    private String Name;



    private ArrayList<Sight> sightsList;
    private String Id;

    public String getUserid() {
        return userid;
    }

    public String getId() {
        return Id;
    }

    public void setUserid(String userid) {
        this.userid = userid;

    }

    private String userid;

    public Route(String name) {
        this.Name = name;
        this.sightsList = new ArrayList<Sight>();
        this.userid = User.getInstance().getUserId();
    }

    public void setName(String name) {
        Name = name;
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
