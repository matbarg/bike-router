package at.ac.hcw.bikerouter.controller;

import at.ac.hcw.bikerouter.dto.PointDto;
import at.ac.hcw.bikerouter.dto.RouteRequestDto;
import at.ac.hcw.bikerouter.dto.RouteResponseDto;
import at.ac.hcw.bikerouter.preferences.RoutingMode;
import at.ac.hcw.bikerouter.service.RoutingService;
import at.ac.hcw.bikerouter.preferences.BikeProfile;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoutingController {
    private static final Logger LOG = LoggerFactory.getLogger(RoutingController.class);
    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponseDto testRoute() {
        // Start: Museumsquartier
        PointDto start = new PointDto(48.20487, 16.35801);

        // Destination: FH Campus
        PointDto destination = new PointDto(48.15975, 16.38305);

        RouteRequestDto request = new RouteRequestDto(start, destination, BikeProfile.BASE, RoutingMode.PRESET, null);

        return routingService.route(request);
    }

    @GetMapping(path = "/test-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public RouteResponseDto testProfile(@RequestParam BikeProfile profile) {
        //RoutePoint start = new RoutePoint(48.20487, 16.35801); // Museumsquartier
        PointDto start = new PointDto(48.17538, 16.29133); // Küniglberg

        //RoutePoint destination = new RoutePoint(48.15975, 16.38305); // FH Campus
        PointDto destination = new PointDto(48.19982, 16.31119); // Hütteldorfer Straße U

        RouteRequestDto request = new RouteRequestDto(start, destination, profile, RoutingMode.PRESET, null);

        return routingService.route(request);
    }

    @PostMapping(
            path = "/route",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RouteResponseDto route(@Valid @RequestBody RouteRequestDto request) {
        LOG.debug("Received request with preferences: {}", request.getPreferencesDto());
        return routingService.route(request);
    }
}
