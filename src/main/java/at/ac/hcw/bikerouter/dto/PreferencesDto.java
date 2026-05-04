package at.ac.hcw.bikerouter.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class PreferencesDto {
    private final static String DECIMAL_MIN = "0.3";
    private final static String DECIMAL_MAX = "1.8";

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double bikeInfra = 1.0;

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double surfaces = 1.0;

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double hills = 1.0;

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double carFree = 1.0;

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double mainRoads = 1.0;

    @DecimalMin(DECIMAL_MIN)
    @DecimalMax(DECIMAL_MAX)
    private double residential = 1.0;

    public double getBikeInfra() {
        return bikeInfra;
    }

    public void setBikeInfra(double bikeInfra) {
        this.bikeInfra = bikeInfra;
    }

    public double getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(double surfaces) {
        this.surfaces = surfaces;
    }

    public double getHills() {
        return hills;
    }

    public void setHills(double hills) {
        this.hills = hills;
    }

    public double getCarFree() {
        return carFree;
    }

    public void setCarFree(double carFree) {
        this.carFree = carFree;
    }

    public double getMainRoads() {
        return mainRoads;
    }

    public void setMainRoads(double mainRoads) {
        this.mainRoads = mainRoads;
    }

    public double getResidential() {
        return residential;
    }

    public void setResidential(double residential) {
        this.residential = residential;
    }

    @Override
    public String toString() {
        return "Preferences weights{" +
                "surfaces=" + surfaces +
                ", hills=" + hills +
                ", bike infra=" + bikeInfra +
                ", car free=" + bikeInfra +
                ", residential=" + residential +
                '}';
    }
}
