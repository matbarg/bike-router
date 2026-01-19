package at.ac.hcw.bikerouter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeoJSONGeometry {
    private final String type = "LineString";
    private final List<Double[]> coordinates;

    @JsonCreator
    public GeoJSONGeometry(@JsonProperty("coordinates") List<Double[]> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<Double[]> getCoordinates() {
        return coordinates;
    }
}
