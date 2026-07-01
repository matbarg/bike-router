package at.ac.hcw.bikerouter.dto;

import at.ac.hcw.bikerouter.preferences.BikeProfile;

import java.util.List;
import java.util.Map;

public class GeoJSONProperties {
    private double distance;
    private double time;
    private double ascend;
    private double descend;
    private BikeProfile profile;
    private List<RouteInstructionDto> instructions;
    private long calcTime;
    private Map<String, Map<String, Double>> routeFeatures;

    public GeoJSONProperties() {}

    public GeoJSONProperties(double distance, double time, double ascend, double descend, BikeProfile profile, long calcTime) {
        this.distance = distance;
        this.time = time;
        this.ascend = ascend;
        this.descend = descend;
        this.profile = profile;
        this.calcTime = calcTime;
    }

    public GeoJSONProperties(double distance, double time, double ascend, double descend, BikeProfile profile, long calcTime, List<RouteInstructionDto> instructions) {
        this.distance = distance;
        this.time = time;
        this.ascend = ascend;
        this.descend = descend;
        this.instructions = instructions;
        this.profile = profile;
        this.calcTime = calcTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getAscend() {
        return ascend;
    }

    public void setAscend(double ascend) {
        this.ascend = ascend;
    }

    public double getDescend() {
        return descend;
    }

    public void setDescend(double descend) {
        this.descend = descend;
    }

    public List<RouteInstructionDto> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<RouteInstructionDto> instructions) {
        this.instructions = instructions;
    }

    public BikeProfile getProfile() {
        return profile;
    }

    public void setProfile(BikeProfile profile) {
        this.profile = profile;
    }

    public long getCalcTime() {
        return calcTime;
    }

    public void setCalcTime(long calcTime) {
        this.calcTime = calcTime;
    }

    public Map<String, Map<String, Double>> getRouteFeatures() {
        return routeFeatures;
    }

    public void setRouteFeatures(Map<String, Map<String, Double>> routeFeatures) {
        this.routeFeatures = routeFeatures;
    }
}
