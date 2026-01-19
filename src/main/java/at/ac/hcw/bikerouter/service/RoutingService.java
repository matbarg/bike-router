package at.ac.hcw.bikerouter.service;

import at.ac.hcw.bikerouter.model.RouteRequest;
import at.ac.hcw.bikerouter.model.RouteResponse;
import at.ac.hcw.bikerouter.util.RouteTranslator;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import org.springframework.stereotype.Service;

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

        ghRequest.setProfile(request.getProfile().label);
        ghRequest.putHint("elevation", true);

        // Perform the route request through the GH instance
        GHResponse res = hopper.route(ghRequest);

        System.out.println("Number of paths found: " + res.getAll().size());

        // todo: add custom exception
        if (res.hasErrors()) throw new RuntimeException(res.getErrors().toString());

        ResponsePath path = res.getBest();

        System.out.println("Path ascend: " + path.getAscend());

        // Map the GH response back to an API response
        return routeTranslator.toAPIResponse(path);
    }
}
