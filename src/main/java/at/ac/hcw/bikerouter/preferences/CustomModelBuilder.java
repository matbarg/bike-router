package at.ac.hcw.bikerouter.preferences;

import at.ac.hcw.bikerouter.dto.PreferencesDto;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.*;

public class CustomModelBuilder {

    private static void applyRule(CustomModel m, String condition, double factor) {
        m.addToPriority(If(condition, MULTIPLY, String.valueOf(factor)));
    }

    public static CustomModel base() {
        CustomModel base = GHUtility.loadCustomModelFromJar("bike.json");
        base.getPriority().removeIf(s -> s.condition() != null && s.condition().contains("bike_network"));
        CustomModel elevation = GHUtility.loadCustomModelFromJar("bike_elevation.json");
        return CustomModel.merge(base, elevation);
    }

    private static CustomModel merge(CustomModel m) {
        return CustomModel.merge(base(), m);
    }

    public static CustomModel from(PreferencesDto preferences) {
        CustomModel model = new CustomModel();

        applyRule(model, "cycleway == LANE", preferences.getRoadClassCycleway());
        applyRule(model, "cycleway == TRACK", preferences.getRoadClassCycleway());
        applyRule(model, "road_class == CYCLEWAY", preferences.getRoadClassCycleway());
        applyRule(model, "road_class == PRIMARY || road_class == SECONDARY || road_class == TRUNK", preferences.getRoadClassPrimarySecondaryTrunk());
        applyRule(model, "road_class == RESIDENTIAL", preferences.getRoadClassResidential());
        applyRule(model, "road_class == PATH", preferences.getRoadClassResidential());
        applyRule(model, "road_class == FOOTWAY || bike_road_access == YES", preferences.getRoadClassFootway());
        applyRule(model, "surface == GRAVEL || surface == UNPAVED || surface == COBBLESTONE", preferences.getSurfaceCobblestoneGravelUnpaved());
        applyRule(model, "average_slope >= 4", preferences.getInclineAvgAboveFourPercent());
        applyRule(model, "average_slope <= -4", preferences.getDeclineAvgAboveFourPercent());
        applyRule(model, "!car_access", preferences.getNoCarAccess());
        applyRule(model, "bike_road_access == DESIGNATED", preferences.getBikeRoadAccessDesignated());
        applyRule(model, "bike_road_access == DISMOUNT || get_off_bike", preferences.getBikeRoadAccessDismountOrGetOffBike());
        applyRule(model, "max_speed > 30", preferences.getMaxSpeedAboveThirty());


        return merge(model);
    }
}
