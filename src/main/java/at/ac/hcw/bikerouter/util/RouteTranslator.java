package at.ac.hcw.bikerouter.util;

import at.ac.hcw.bikerouter.dto.*;
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
    private final RouteFeaturesCalculator routeFeaturesCalculator;

    public RouteTranslator(RouteFeaturesCalculator routeFeaturesCalculator) {
        this.routeFeaturesCalculator = routeFeaturesCalculator;
    }

    public GHRequest toGHRequest(RouteRequestDto apiRequest) {
        GHRequest ghRequest = new GHRequest();
        ghRequest.setProfile(apiRequest.getProfile().label);

        for (PointDto rp : apiRequest.getPoints()) {
            ghRequest.addPoint(new GHPoint(rp.getLat(), rp.getLon()));
        }

        return ghRequest;
    }

    // without instructions
    public RouteResponseDto toAPIResponse(ResponsePath route, BikeProfile profile, long calcTime) {
        // turn the PointList into a List of double arrays and add it to the GeoJSONGeometry
        List<Double[]> coordinates = new ArrayList<>();

        route.getPoints().forEach(ghPoint3D -> coordinates.add(new Double[]{ghPoint3D.getLon(), ghPoint3D.getLat()}));

        GeoJSONGeometry geometry = new GeoJSONGeometry(coordinates);

        GeoJSONProperties properties = new GeoJSONProperties(
                route.getDistance(),
                route.getTime() / 60_000.0,
                route.getAscend(),
                route.getDescend(),
                profile,
                calcTime
        );
        properties.setRouteFeatures(routeFeaturesCalculator.calculateRouteFeaturesRatios(route));

        return new RouteResponseDto(
                geometry,
                properties
        );
    }

    // with instructions
    public RouteResponseDto toAPIResponseWithInstructions(ResponsePath route, BikeProfile profile, long calcTime, Translation tr) {
        List<RouteInstructionDto> instructions = new ArrayList<>();

        for (Instruction i : route.getInstructions()) {
            instructions.add(new RouteInstructionDto(
                    i.getName(),
                    i.getDistance(),
                    (int) i.getTime(),
                    i.getTurnDescription(tr)
            ));
        }

        RouteResponseDto response = toAPIResponse(route, profile, calcTime);
        response.getProperties().setInstructions(instructions);
        return response;
    }
}
