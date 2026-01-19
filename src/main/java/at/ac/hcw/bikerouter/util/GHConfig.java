package at.ac.hcw.bikerouter.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.LMProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.reader.dem.SRTMProvider;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GHConfig {
    @Bean
    public GraphHopper graphHopper() {
        System.out.println("GraphHopper is initializing ...");

        GraphHopper hopper = new GraphHopper();

        hopper.setOSMFile("src/main/resources/data/austria-251026.osm.pbf");

        hopper.setElevationProvider(new SRTMProvider());

        hopper.setGraphHopperLocation("target/graph-cache");

        hopper.setEncodedValuesString(
                "bike_priority, bike_access, roundabout, bike_average_speed, bike_road_access, foot_road_access, average_slope, mtb_rating, hike_rating, country, road_class, surface, bike_network"
        );

        hopper.setProfiles(
                new Profile(BikeProfile.FAST.label).setCustomModel(GHUtility.loadCustomModelFromJar("bike.json")),
                new Profile(BikeProfile.SAFE.label).setCustomModel(CustomModels.safe()),
                new Profile(BikeProfile.COMFORT.label).setCustomModel(CustomModels.comfort()).putHint("elevation", true),
                new Profile(BikeProfile.SCENIC.label).setCustomModel(CustomModels.safe())
        );

        // enable landmarks for faster queries
        //hopper.getLMPreparationHandler().setLMProfiles(new LMProfile("bike"));

        hopper.importOrLoad();

        System.out.println("Graphhopper loaded.");

        return hopper;
    }
}
