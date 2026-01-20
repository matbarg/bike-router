package at.ac.hcw.bikerouter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/***
 * Is serialized into GeoJSON format:
 *  {
 *     "type": "Feature",
 *     "geometry": {
 *         "type": "LineString",
 *         "coordinates": [[double, double], [double, double]]
 *     },
 *     "properties": {
 *         "distance": double, // meters
 *         "time": int, // milliseconds
 *         "ascend": double,
 *         "descend": double
 *     }
 *  }
 */
public class RouteResponse {
    private final String type = "Feature";
    private final GeoJSONGeometry geometry;
    private final GeoJSONProperties properties;

    @JsonCreator
    public RouteResponse(
            @JsonProperty("geometry") GeoJSONGeometry geometry,
            @JsonProperty("properties") GeoJSONProperties properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public GeoJSONGeometry getGeometry() {
        return geometry;
    }

    public GeoJSONProperties getProperties() {
        return properties;
    }
}
