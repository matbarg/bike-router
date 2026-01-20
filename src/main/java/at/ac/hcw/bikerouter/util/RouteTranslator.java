package at.ac.hcw.bikerouter.util;

import at.ac.hcw.bikerouter.model.*;
import com.graphhopper.GHRequest;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.Translation;
import com.graphhopper.util.shapes.GHPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public RouteResponse toAPIResponse(ResponsePath route) {
        // turn the PointList into a List of double arrays and add it to the GeoJSONGeometry
        List<Double[]> coordinates = new ArrayList<>();

        route.getPoints().forEach(ghPoint3D -> coordinates.add(new Double[] {ghPoint3D.getLon(), ghPoint3D.getLat()}));

        GeoJSONGeometry geometry = new GeoJSONGeometry(coordinates);

        GeoJSONProperties properties = new GeoJSONProperties(
                route.getDistance(),
                route.getTime(),
                route.getAscend(),
                route.getDescend()
        );

        return new RouteResponse(
                geometry,
                properties
        );
    }

    // with instructions
    public RouteResponse toAPIResponse(ResponsePath route, Translation tr) {
        // turn the PointList into a List of double arrays and add it to the GeoJSONGeometry
        List<Double[]> coordinates = new ArrayList<>();

        route.getPoints().forEach(ghPoint3D -> coordinates.add(new Double[] {ghPoint3D.getLon(), ghPoint3D.getLat()}));

        GeoJSONGeometry geometry = new GeoJSONGeometry(coordinates);

        List<RouteInstruction> instructions = new ArrayList<>();

        for (Instruction i : route.getInstructions()) {
            instructions.add(new RouteInstruction(
                    i.getName(),
                     i.getDistance(), // todo in km?
                    (int) i.getTime(), // todo in minutes?
                    i.getTurnDescription(tr)
            ));
        }

        GeoJSONProperties properties = new GeoJSONProperties(
                route.getDistance(),
                route.getTime(),
                route.getAscend(),
                route.getDescend(),
                instructions
        );

        return new RouteResponse(
                geometry,
                properties
        );
    }
}
