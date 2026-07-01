package at.ac.hcw.bikerouter.dto;

import at.ac.hcw.bikerouter.preferences.BikeProfile;
import at.ac.hcw.bikerouter.preferences.Preferences;
import at.ac.hcw.bikerouter.preferences.RoutingMode;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class RouteRequestDto {
    private List<PointDto> points;
    private BikeProfile profile;
    private RoutingMode mode;
    private boolean withInstructions = true;
    @Valid
    private PreferencesDto preferencesDto;

    public RouteRequestDto() {
        points = new ArrayList<>();
    }

    public RouteRequestDto(PointDto start, PointDto destination, BikeProfile profile, RoutingMode mode) {
        points = new ArrayList<>();
        points.add(start);
        points.add(destination);
        this.profile = profile;
        this.mode = mode;
    }

    public RouteRequestDto(PointDto start, PointDto destination, List<PointDto> via, BikeProfile profile, RoutingMode mode) {
        points = new ArrayList<>();
        points.add(start);
        points.addAll(via);
        points.add(destination);
        this.profile = profile;
        this.mode = mode;
    }

    public List<PointDto> getPoints() {
        return points;
    }

    public void setPoints(List<PointDto> points) {
        this.points = points;
    }

    public BikeProfile getProfile() {
        return profile;
    }

    public void setProfile(BikeProfile profile) {
        this.profile = profile;
    }

    public RoutingMode getMode() {
        return mode;
    }

    public void setMode(RoutingMode mode) {
        this.mode = mode;
    }

    public PreferencesDto getPreferencesDto() {
        return preferencesDto;
    }

    public void setPreferencesDto(PreferencesDto preferencesDto) {
        this.preferencesDto = preferencesDto;
    }

    public boolean isWithInstructions() {
        return withInstructions;
    }

    public void setWithInstructions(boolean withInstructions) {
        this.withInstructions = withInstructions;
    }
}
