package at.ac.hcw.bikerouter.service;

import at.ac.hcw.bikerouter.model.RouteRequest;
import at.ac.hcw.bikerouter.model.RouteResponse;
import at.ac.hcw.bikerouter.preferences.BikeProfile;
import at.ac.hcw.bikerouter.preferences.CustomModelBuilder;
import at.ac.hcw.bikerouter.preferences.RoutingMode;
import at.ac.hcw.bikerouter.util.RouteTranslator;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.Translation;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Locale;

@Service
public class RoutingService {
    private final GraphHopper hopper;
    private final RouteTranslator routeTranslator;

    public RoutingService(GraphHopper hopper, RouteTranslator routeTranslator) {
        this.hopper = hopper;
        this.routeTranslator = routeTranslator;
    }

    public RouteResponse route(RouteRequest request) {
        // Map the API request to a GH request
        GHRequest ghRequest = routeTranslator.toGHRequest(request);

        ghRequest.putHint("elevation", true);
        ghRequest.setLocale("en");

        if (request.getMode() == RoutingMode.PRESET) {
            System.out.println("Preset mode. Use profile " + request.getProfile());
            ghRequest.setProfile(request.getProfile().label);
        } else if (request.getMode() == RoutingMode.CUSTOM) {
            ghRequest.setProfile(BikeProfile.FAST.label);
            System.out.println("Custom mode. Use preferences " + request.getPreferences());
            ghRequest.setCustomModel(CustomModelBuilder.build(request.getPreferences()));
        }

        Instant start = Instant.now();

        // Perform the route request through the GH instance
        GHResponse res = hopper.route(ghRequest);

        Instant end = Instant.now();

        Duration timeElapsed = Duration.between(start, end);

        if (res.hasErrors()) throw new RuntimeException(res.getErrors().toString());

        ResponsePath path = res.getBest();

        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);

        // Map the GH response back to an API response
        return routeTranslator.toAPIResponse(path, request.getProfile(), timeElapsed.toMillis(), tr);
    }
}
