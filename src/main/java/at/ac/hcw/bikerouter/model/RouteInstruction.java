package at.ac.hcw.bikerouter.model;

public class RouteInstruction {
    private String name;
    private double distance;
    private int time;
    private String description;

    public RouteInstruction(String name, double distance, int time, String description) {
        this.name = name;
        this.distance = distance;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
