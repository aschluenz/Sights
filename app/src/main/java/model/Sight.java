package model;

/**
 * Created by andyschlunz on 30.01.16.
 */
public class Sight {

    private String name;
    private String placeID;
    private double latitude, longitude;

    public Sight(String name, String placeId) {
        this.name = name;
        this.placeID = placeId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}