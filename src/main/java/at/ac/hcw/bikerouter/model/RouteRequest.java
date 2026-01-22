package at.ac.hcw.bikerouter.model;

import at.ac.hcw.bikerouter.preferences.BikeProfile;
import at.ac.hcw.bikerouter.preferences.Preferences;
import at.ac.hcw.bikerouter.preferences.RoutingMode;

import java.util.ArrayList;
import java.util.List;

public class RouteRequest {
    private List<RoutePoint> points;
    private BikeProfile profile;
    private int speed;
    private RoutingMode mode;
    private Preferences preferences;

    public RouteRequest() {
        points = new ArrayList<>();
    }

    public RouteRequest(RoutePoint start, RoutePoint destination, BikeProfile profile, int speed, RoutingMode mode, Preferences preferences) {
        points = new ArrayList<>();
        points.add(start);
        points.add(destination);
        this.profile = profile;
        this.speed = speed;
        this.mode = mode;
        this.preferences = preferences;
    }

    public RouteRequest(RoutePoint start, RoutePoint destination, List<RoutePoint> via, BikeProfile profile, int speed, RoutingMode mode, Preferences preferences) {
        points = new ArrayList<>();
        points.add(start);
        points.addAll(via);
        points.add(destination);
        this.profile = profile;
        this.speed = speed;
        this.mode = mode;
        this.preferences = preferences;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public void setPoints(List<RoutePoint> points) {
        this.points = points;
    }

    public BikeProfile getProfile() {
        return profile;
    }

    public void setProfile(BikeProfile profile) {
        this.profile = profile;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public RoutingMode getMode() {
        return mode;
    }

    public void setMode(RoutingMode mode) {
        this.mode = mode;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}
