package at.ac.hcw.bikerouter.util;

import at.ac.hcw.bikerouter.preferences.BikeProfile;
import at.ac.hcw.bikerouter.preferences.CustomModelBuilder;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.reader.dem.SRTMProvider;
import com.graphhopper.routing.util.EncodingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GHConfig {
    private static final Logger LOG = LoggerFactory.getLogger(GHConfig.class);

    @Bean
    public GraphHopper graphHopper() {
        LOG.info("Initializing Graphhopper");
        GraphHopper hopper = new GraphHopper();
        String[] encodedValues = {
                "car_access",
                "bike_priority",
                "bike_access",
                "roundabout",
                "bike_average_speed",
                "bike_road_access",
                "foot_road_access",
                "average_slope",
                "mtb_rating",
                "hike_rating",
                "country",
                "road_class",
                "surface",
                "bike_network",
                "get_off_bike",
                "max_speed",
                "road_environment",
                "ferry_speed",
                "cycleway"
        };
        hopper.setOSMFile("src/main/resources/data/austria-latest.osm.pbf");
        hopper.setElevationProvider(new SRTMProvider());
        hopper.setGraphHopperLocation("target/graph-cache");
        hopper.setEncodedValuesString(String.join(", ", encodedValues));
        hopper.setProfiles(new Profile(BikeProfile.BASE.label).setCustomModel(CustomModelBuilder.base()));
        LOG.info("Default profiles: " + hopper.getProfile(BikeProfile.BASE.label).getCustomModel());

        hopper.importOrLoad();
        LOG.info("Graphhopper finished loading");
        return hopper;
    }
}
