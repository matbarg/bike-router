package at.ac.hcw.bikerouter.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public class PreferencesDto {
    private final static String MIN = "0.2";
    private final static String NEUTRAL = "1.0";
    private final static String MAX = "2.0";

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double cyclewayLane = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double cyclewayTrack = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double roadClassCycleway = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double roadClassPrimarySecondaryTrunk = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double roadClassResidential = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double roadClassPath = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double roadClassFootway = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double surfaceCobblestoneGravelUnpaved = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double inclineAvgAboveFourPercent = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double declineAvgAboveFourPercent = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double noCarAccess = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double bikeRoadAccessDesignated = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double bikeRoadAccessDismountOrGetOffBike = 1.0;

    @DecimalMin(MIN)
    @DecimalMax(MAX)
    private double maxSpeedAboveThirty = 1.0;

    public double getCyclewayLane() {
        return cyclewayLane;
    }

    public void setCyclewayLane(double cyclewayLane) {
        this.cyclewayLane = cyclewayLane;
    }

    public double getCyclewayTrack() {
        return cyclewayTrack;
    }

    public void setCyclewayTrack(double cyclewayTrack) {
        this.cyclewayTrack = cyclewayTrack;
    }

    public double getRoadClassCycleway() {
        return roadClassCycleway;
    }

    public void setRoadClassCycleway(double roadClassCycleway) {
        this.roadClassCycleway = roadClassCycleway;
    }

    public double getRoadClassPrimarySecondaryTrunk() {
        return roadClassPrimarySecondaryTrunk;
    }

    public void setRoadClassPrimarySecondaryTrunk(double roadClassPrimarySecondaryTrunk) {
        this.roadClassPrimarySecondaryTrunk = roadClassPrimarySecondaryTrunk;
    }

    public double getRoadClassResidential() {
        return roadClassResidential;
    }

    public void setRoadClassResidential(double roadClassResidential) {
        this.roadClassResidential = roadClassResidential;
    }

    public double getRoadClassPath() {
        return roadClassPath;
    }

    public void setRoadClassPath(double roadClassPath) {
        this.roadClassPath = roadClassPath;
    }

    public double getRoadClassFootway() {
        return roadClassFootway;
    }

    public void setRoadClassFootway(double roadClassFootway) {
        this.roadClassFootway = roadClassFootway;
    }

    public double getSurfaceCobblestoneGravelUnpaved() {
        return surfaceCobblestoneGravelUnpaved;
    }

    public void setSurfaceCobblestoneGravelUnpaved(double surfaceCobblestoneGravelUnpaved) {
        this.surfaceCobblestoneGravelUnpaved = surfaceCobblestoneGravelUnpaved;
    }

    public double getInclineAvgAboveFourPercent() {
        return inclineAvgAboveFourPercent;
    }

    public void setInclineAvgAboveFourPercent(double inclineAvgAboveFourPercent) {
        this.inclineAvgAboveFourPercent = inclineAvgAboveFourPercent;
    }

    public double getDeclineAvgAboveFourPercent() {
        return declineAvgAboveFourPercent;
    }

    public void setDeclineAvgAboveFourPercent(double declineAvgAboveFourPercent) {
        this.declineAvgAboveFourPercent = declineAvgAboveFourPercent;
    }

    public double getNoCarAccess() {
        return noCarAccess;
    }

    public void setNoCarAccess(double noCarAccess) {
        this.noCarAccess = noCarAccess;
    }

    public double getBikeRoadAccessDesignated() {
        return bikeRoadAccessDesignated;
    }

    public void setBikeRoadAccessDesignated(double bikeRoadAccessDesignated) {
        this.bikeRoadAccessDesignated = bikeRoadAccessDesignated;
    }

    public double getBikeRoadAccessDismountOrGetOffBike() {
        return bikeRoadAccessDismountOrGetOffBike;
    }

    public void setBikeRoadAccessDismountOrGetOffBike(double bikeRoadAccessDismountOrGetOffBike) {
        this.bikeRoadAccessDismountOrGetOffBike = bikeRoadAccessDismountOrGetOffBike;
    }

    public double getMaxSpeedAboveThirty() {
        return maxSpeedAboveThirty;
    }

    public void setMaxSpeedAboveThirty(double maxSpeedAboveThirty) {
        this.maxSpeedAboveThirty = maxSpeedAboveThirty;
    }

    @Override
    public String toString() {
        return "PreferencesDto:" +
                "\ncyclewayLane=" + cyclewayLane +
                ",\ncyclewayTrack=" + cyclewayTrack +
                ",\nroadClassCycleway=" + roadClassCycleway +
                ",\nroadClassPrimarySecondaryTrunk=" + roadClassPrimarySecondaryTrunk +
                ",\nroadClassResidential=" + roadClassResidential +
                ",\nroadClassPath=" + roadClassPath +
                ",\nroadClassFootway=" + roadClassFootway +
                ",\nsurfaceCobblestoneGravelUnpaved=" + surfaceCobblestoneGravelUnpaved +
                ",\ninclineAvgAboveFourPercent=" + inclineAvgAboveFourPercent +
                ",\ndeclineAvgAboveFourPercent=" + declineAvgAboveFourPercent +
                ",\nnoCarAccess=" + noCarAccess +
                ",\nbikeRoadAccessDesignated=" + bikeRoadAccessDesignated +
                ",\nbikeRoadAccessDismountOrGetOffBike=" + bikeRoadAccessDismountOrGetOffBike +
                ",\nmaxSpeedAboveThirty=" + maxSpeedAboveThirty;
    }
}
