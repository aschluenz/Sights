package model;

import java.util.List;

/**
 * Created by AndySchluenz on 26.01.16.
 */
public class Route {

    private String Name;
    private List<Place> places;
    private String Id;

    public Route(String name) {
        Name = name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {

        return Name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public String getId() {
        return Id;
    }
}
