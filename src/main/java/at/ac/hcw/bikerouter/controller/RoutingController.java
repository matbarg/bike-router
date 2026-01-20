package at.ac.hcw.bikerouter.controller;

import at.ac.hcw.bikerouter.model.RoutePoint;
import at.ac.hcw.bikerouter.model.RouteRequest;
import at.ac.hcw.bikerouter.model.RouteResponse;
import at.ac.hcw.bikerouter.service.RoutingService;
import at.ac.hcw.bikerouter.util.BikeProfile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoutingController {
    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponse testRoute() {
        // Start: Museumsquartier
        RoutePoint start = new RoutePoint(48.20487, 16.35801);

        // Destination: FH Campus
        RoutePoint destination = new RoutePoint(48.15975, 16.38305);

        RouteRequest request = new RouteRequest(start, destination, BikeProfile.FAST);

        return routingService.route(request);
    }

    @GetMapping(path = "/test-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponse testProfile(@RequestParam BikeProfile profile) {
        //RoutePoint start = new RoutePoint(48.20487, 16.35801); // Museumsquartier
        RoutePoint start = new RoutePoint(48.17538, 16.29133); // Küniglberg

        //RoutePoint destination = new RoutePoint(48.15975, 16.38305); // FH Campus
        RoutePoint destination = new RoutePoint(48.19982, 16.31119); // Hütteldorfer Straße U

        RouteRequest request = new RouteRequest(start, destination, profile);

        return routingService.route(request);
    }

    @PostMapping(
            path = "/route",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RouteResponse route(@RequestBody RouteRequest request) {
        return routingService.route(request);
    }
}
