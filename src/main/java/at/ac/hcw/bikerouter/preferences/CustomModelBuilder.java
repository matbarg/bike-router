package at.ac.hcw.bikerouter.preferences;

import at.ac.hcw.bikerouter.dto.PreferencesDto;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.*;

public class CustomModelBuilder {

    private static void weighBikeInfra(CustomModel m, double factor) {
        //m.addToPriority(If("bike_network != MISSING", MULTIPLY, String.valueOf(factor)));
        m.addToPriority(If("road_class == CYCLEWAY", MULTIPLY, String.valueOf(factor)));
    }

    private static void weighSurfaces(CustomModel m, double factor) {
        m.addToPriority(If("surface == GRAVEL || surface == UNPAVED || surface == COBBLESTONE",
                MULTIPLY, String.valueOf(factor)));
    }

    private static void weighOffRoadSurfaces(CustomModel m, double factor) {
        m.addToPriority(If("surface == GROUND || surface == GRASS",
                MULTIPLY, String.valueOf(factor)));
    }

    private static void weighMainRoads(CustomModel m, double factor) {
        m.addToPriority(If(
                "road_class == PRIMARY || road_class == SECONDARY || road_class == TRUNK",
                MULTIPLY, String.valueOf(factor)));
    }

    private static void weighSideRoads(CustomModel m, double factor) {
        m.addToPriority(If(
                "road_class == TRACK || road_class == PATH || road_class == FOOTWAY",
                MULTIPLY, String.valueOf(factor)));
    }

    private static void weighHills(CustomModel m, double factor) {
        m.addToPriority(If("average_slope >= 6", MULTIPLY, String.valueOf(factor)));
        //m.addToPriority(If("average_slope >= 9", MULTIPLY, String.valueOf(factor * 0.6)));
        m.addToPriority(If("average_slope <= -8", MULTIPLY, String.valueOf(factor)));
        //m.addToPriority(If("average_slope <= -12", MULTIPLY, String.valueOf(factor * 0.8)));
    }

    private static void preferParks(CustomModel m, double factor) {
        m.addToPriority(If("road_class == TRACK || road_class == PATH || road_class == FOOTWAY",
                MULTIPLY, String.valueOf(factor * 1.4)));
        m.addToPriority(If("!car_access", MULTIPLY, String.valueOf(factor * 1.2)));
        m.addToPriority(If("surface == GROUND || surface == GRASS",
                MULTIPLY, String.valueOf(factor)));
    }

    private static void weighCarFree(CustomModel m, double factor) {
        m.addToPriority(If("!car_access", MULTIPLY, String.valueOf(factor)));
    }

    private static void weighResidential(CustomModel m, double factor) {
        m.addToPriority(If("road_class == RESIDENTIAL", MULTIPLY, String.valueOf(factor)));
    }

    public static CustomModel base() {
        CustomModel base = GHUtility.loadCustomModelFromJar("bike.json");
        base.getPriority().removeIf(s -> s.condition() != null && s.condition().contains("bike_network"));
        CustomModel elevation = GHUtility.loadCustomModelFromJar("bike_elevation.json");
        return CustomModel.merge(base, elevation);
    }

    public static CustomModel safe() {
        return build(ProfilePresetsDiscrete.safe());
    }

    public static CustomModel comfort() {
        return build(ProfilePresetsDiscrete.comfort());
    }

    public static CustomModel scenic() {
        return build(ProfilePresetsDiscrete.scenic());
    }

    private static CustomModel mergeToBase(CustomModel m) {
        return CustomModel.merge(base(), m);
    }

    public static CustomModel build(Preferences pref) {
        CustomModel m = new CustomModel();

        if (pref.getAvoidBadSurfaces() != null && pref.getAvoidBadSurfaces().isActive()) {
            weighSurfaces(m, pref.getAvoidBadSurfaces().factor);
        }

        if (pref.getAvoidTraffic() != null && pref.getAvoidTraffic().isActive()) {
            weighMainRoads(m, pref.getAvoidTraffic().factor);
        }

        if (pref.getAvoidHills() != null && pref.getAvoidHills().isActive()) {
            weighHills(m, pref.getAvoidHills().factor);
        }

        if (pref.getPreferBikeInfra() != null && pref.getPreferBikeInfra().isActive()) {
            weighBikeInfra(m, pref.getPreferBikeInfra().factor);
        }

        if (pref.getPreferParks() != null && pref.getPreferParks().isActive()) {
            preferParks(m, pref.getPreferParks().factor);
        }

        return mergeToBase(m);
    }
    
    public static CustomModel build(PreferencesDto preferences) {
        CustomModel model = new CustomModel();
        
        weighBikeInfra(model, preferences.getBikeInfra());
        weighSurfaces(model, preferences.getSurfaces());
        weighHills(model, preferences.getHills());
        weighCarFree(model, preferences.getCarFree());
        weighMainRoads(model, preferences.getMainRoads());
        weighResidential(model, preferences.getResidential());

        return mergeToBase(model);
    }
}
