package at.ac.hcw.bikerouter.example;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.PointList;

import java.util.Locale;

public class RoutingExample {
    public static void main(String[] args) {
        GraphHopper hopper = new GraphHopper();

        hopper.setOSMFile("src/main/resources/data/austria-251026.osm.pbf");

        hopper.setGraphHopperLocation("target/graph-cache");

        hopper.setEncodedValuesString(
                "bike_access, bike_average_speed, bike_priority, road_class, country, bike_road_access, mtb_rating, hike_rating"
        );

        hopper.setProfiles(
                new Profile("bike").setCustomModel(GHUtility.loadCustomModelFromJar("bike.json"))
        );

        // enables Contraction Hierarchies
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("bike"));

        hopper.importOrLoad();

        GHRequest req = new GHRequest(
                48.20487, 16.35801, // from
                48.15975, 16.38305 // to
        ).setProfile("bike").setLocale("en");

        System.out.println("Computing route ...");

        GHResponse res = hopper.route(req);

        if (res.hasErrors()) throw new RuntimeException(res.getErrors().toString());

        ResponsePath path = res.getBest();

        PointList points = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();
        double timeInMin = (double) timeInMs / 1000 / 60;

        String output = """
                Route found:
                Points: %s
                Distance: %.2f m
                Time: %.2f min
                """.formatted(points, distance, timeInMin);

        System.out.println(output);

        System.out.println("Path description: ");
        System.out.println(path.getDescription());

        System.out.println("Path instruction list");
        System.out.println(path.getInstructions());

        System.out.println("Path in GeoJSON: ");
        System.out.println(toGeoJSON(points));
    }

    public static String toGeoJSON(PointList points) {
        StringBuilder sb = new StringBuilder("""
        {
          "type": "Feature",
          "geometry": {
            "type": "LineString",
            "coordinates": [
        """);

        for (int i = 0; i < points.size(); i++) {
            sb.append(String.format(Locale.ROOT,
                    "[%.6f, %.6f]",
                    points.get(i).getLon(),
                    points.get(i).getLat())
            );

            if (i < points.size() - 1) sb.append(",");
        }

        sb.append("""
            ]
          },
          "properties": {}
        }
        """);

        return sb.toString();
    }
}
