package at.ac.hcw.bikerouter.model;

import at.ac.hcw.bikerouter.util.BikeProfile;

import java.util.ArrayList;
import java.util.List;

public class RouteRequest {
    private List<RoutePoint> points;
    private BikeProfile profile;

    public RouteRequest() {
        points = new ArrayList<>();
    }

    public RouteRequest(RoutePoint start, RoutePoint destination, BikeProfile profile) {
        points = new ArrayList<>();
        points.add(start);
        points.add(destination);
        this.profile = profile;
    }

    public RouteRequest(RoutePoint start, RoutePoint destination, List<RoutePoint> via, BikeProfile profile) {
        points = new ArrayList<>();
        points.add(start);
        points.addAll(via);
        points.add(destination);
        this.profile = profile;
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
}
