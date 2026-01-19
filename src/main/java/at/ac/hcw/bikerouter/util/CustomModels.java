package at.ac.hcw.bikerouter.util;

import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.*;

public class CustomModels {
    public static CustomModel safe() {
        CustomModel m = new CustomModel();

        m.addToPriority(If("road_class == PRIMARY || road_class == SECONDARY", MULTIPLY, "0.3"))
                .addToPriority(If("bike_network == MISSING", MULTIPLY, "0.5"))
                //.addToPriority(If("max_speed >= 30", MULTIPLY, "0.1"))
                .addToPriority(If("surface == GRAVEL || surface == UNPAVED", MULTIPLY, "0.6"));

        return CustomModel.merge(GHUtility.loadCustomModelFromJar("bike.json"), m);
    }

    public static CustomModel comfort() {
        CustomModel m = new CustomModel();

        m.addToPriority(If("average_slope > 0.05", MULTIPLY, "0.4"))
                .addToPriority(If("surface == COBBLESTONE", MULTIPLY, "0.3"))
                .addToPriority(If("surface == GRAVEL || surface == UNPAVED", MULTIPLY, "0.5"))
                .addToPriority(If("get_off_bike", MULTIPLY, "0.1"));

        return CustomModel.merge(GHUtility.loadCustomModelFromJar("bike.json"), m);
    }

    public static CustomModel scenic() {
        CustomModel m = new CustomModel();

        m.addToPriority(If("road_class == PRIMARY || road_class == TRUNK", MULTIPLY, "0.2"))
                .addToPriority(If("bike_network != MISSING", MULTIPLY, "0.3"));

        return CustomModel.merge(GHUtility.loadCustomModelFromJar("bike.json"), m);
    }
}
