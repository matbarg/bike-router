package at.ac.hcw.bikerouter.model;

import com.graphhopper.util.Instruction;

import java.util.ArrayList;
import java.util.List;

public class GeoJSONProperties {
    private double distance;
    private long time;
    private double ascend;
    private double descend;
    private List<RouteInstruction> instructions;

    public GeoJSONProperties() {}

    public GeoJSONProperties(double distance, long time, double ascend, double descend) {
        this.distance = distance;
        this.time = time;
        this.ascend = ascend;
        this.descend = descend;
    }

    public GeoJSONProperties(double distance, long time, double ascend, double descend, List<RouteInstruction> instructions) {
        this.distance = distance;
        this.time = time;
        this.ascend = ascend;
        this.descend = descend;
        this.instructions = instructions;
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
}
