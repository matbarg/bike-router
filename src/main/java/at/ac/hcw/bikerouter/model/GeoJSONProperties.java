package at.ac.hcw.bikerouter.model;

import at.ac.hcw.bikerouter.preferences.BikeProfile;

import java.util.List;

public class GeoJSONProperties {
    private double distance;
    private long time;
    private double ascend;
    private double descend;
    private BikeProfile profile;
    private List<RouteInstruction> instructions;
    private long calcTime;

    public GeoJSONProperties() {}

    public GeoJSONProperties(double distance, long time, double ascend, double descend, BikeProfile profile, long calcTime) {
        this.distance = distance;
        this.time = time;
        this.ascend = ascend;
        this.descend = descend;
        this.profile = profile;
        this.calcTime = calcTime;
    }

    public GeoJSONProperties(double distance, long time, double ascend, double descend, BikeProfile profile, long calcTime, List<RouteInstruction> instructions) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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

    public List<RouteInstruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<RouteInstruction> instructions) {
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

    public void setCalcTime(int calcTime) {
        this.calcTime = calcTime;
    }
}
