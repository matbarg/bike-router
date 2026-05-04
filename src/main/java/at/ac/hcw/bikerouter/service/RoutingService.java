package at.ac.hcw.bikerouter.service;

import at.ac.hcw.bikerouter.dto.RouteRequestDto;
import at.ac.hcw.bikerouter.dto.RouteResponseDto;
import at.ac.hcw.bikerouter.preferences.BikeProfile;
import at.ac.hcw.bikerouter.preferences.CustomModelBuilder;
import at.ac.hcw.bikerouter.preferences.RoutingMode;
import at.ac.hcw.bikerouter.util.RouteTranslator;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Service
public class RoutingService {
    private static final Logger LOG = LoggerFactory.getLogger(RoutingService.class);
    private final GraphHopper hopper;
    private final RouteTranslator routeTranslator;

    public RoutingService(GraphHopper hopper, RouteTranslator routeTranslator) {
        this.hopper = hopper;
        this.routeTranslator = routeTranslator;
    }

    public RouteResponseDto route(RouteRequestDto request) {
        // Map the API request to a GH request
        GHRequest ghRequest = routeTranslator.toGHRequest(request);

        ghRequest.putHint("elevation", true);
        ghRequest.setLocale("en");

        // path details
        List<String> details = List.of(
                "surface",
                //"smoothness",
                "max_speed",
                "road_class",
                "bike_network",
                "cycleway");
        ghRequest.setPathDetails(details);

        if (request.getMode() == RoutingMode.PRESET) {
            System.out.println("Preset mode. Use profile " + request.getProfile());
            ghRequest.setProfile(request.getProfile().label);
        } else if (request.getMode() == RoutingMode.CUSTOM) {
            ghRequest.setProfile(BikeProfile.BASE.label);
            ghRequest.setCustomModel(CustomModelBuilder.build(request.getPreferencesDto()));
        }

        Instant start = Instant.now();

        // Perform the route request through the GH instance
        GHResponse res = hopper.route(ghRequest);

        Instant end = Instant.now();

        Duration timeElapsed = Duration.between(start, end);

        if (res.hasErrors()) throw new RuntimeException(res.getErrors().toString());

        ResponsePath path = res.getBest();

        LOG.debug("Path details: {}", path.getPathDetails());

        if (request.isWithInstructions()) {
            Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
            return routeTranslator.toAPIResponse(path, request.getProfile(), timeElapsed.toMillis(), tr);
        } else {
            return routeTranslator.toAPIResponse(path, request.getProfile(), timeElapsed.toMillis());
        }
    }
}
