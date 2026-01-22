package at.ac.hcw.bikerouter.util;

import at.ac.hcw.bikerouter.model.*;
import at.ac.hcw.bikerouter.preferences.BikeProfile;
import com.graphhopper.GHRequest;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouteTranslator {
    public GHRequest toGHRequest(RouteRequest apiRequest) {
        GHRequest ghRequest = new GHRequest();
        ghRequest.setProfile(apiRequest.getProfile().label);

        for (RoutePoint rp : apiRequest.getPoints()) {
            ghRequest.addPoint(new GHPoint(rp.getLat(), rp.getLon()));
        }

        return ghRequest;
    }

    // without instructions
    public RouteResponse toAPIResponse(ResponsePath route, BikeProfile profile, long calcTime) {
        // turn the PointList into a List of double arrays and add it to the GeoJSONGeometry
        List<Double[]> coordinates = new ArrayList<>();

        route.getPoints().forEach(ghPoint3D -> coordinates.add(new Double[] {ghPoint3D.getLon(), ghPoint3D.getLat()}));

        GeoJSONGeometry geometry = new GeoJSONGeometry(coordinates);

        GeoJSONProperties properties = new GeoJSONProperties(
                route.getDistance(),
                route.getTime(),
                route.getAscend(),
                route.getDescend(),
                profile,
                calcTime
        );

        return new RouteResponse(
                geometry,
                properties
        );
    }

    // with instructions
    public RouteResponse toAPIResponse(ResponsePath route, BikeProfile profile, long calcTime, Translation tr) {
        // turn the PointList into a List of double arrays and add it to the GeoJSONGeometry
        List<Double[]> coordinates = new ArrayList<>();

        route.getPoints().forEach(ghPoint3D -> coordinates.add(new Double[] {ghPoint3D.getLon(), ghPoint3D.getLat()}));

        GeoJSONGeometry geometry = new GeoJSONGeometry(coordinates);

        List<RouteInstruction> instructions = new ArrayList<>();

        for (Instruction i : route.getInstructions()) {
            instructions.add(new RouteInstruction(
                    i.getName(),
                     i.getDistance(),
                    (int) i.getTime(),
                    i.getTurnDescription(tr)
            ));
        }

        GeoJSONProperties properties = new GeoJSONProperties(
                route.getDistance(),
                route.getTime(),
                route.getAscend(),
                route.getDescend(),
                profile,
                calcTime,
                instructions
        );

        return new RouteResponse(
                geometry,
                properties
        );
    }
}
