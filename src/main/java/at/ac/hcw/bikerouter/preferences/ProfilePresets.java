package at.ac.hcw.bikerouter.preferences;

public class ProfilePresets {
    public static Preferences safe() {
        Preferences p = new Preferences();
        p.setAvoidTraffic(AvoidTraffic.LOW);
        p.setPreferBikeInfra(PreferBikeInfra.HIGH);
        p.setAvoidBadSurfaces(AvoidBadSurfaces.LOW);
        return p;
    }

    public static Preferences comfort() {
        Preferences p = new Preferences();
        p.setAvoidHills(AvoidHills.HIGH);
        p.setAvoidBadSurfaces(AvoidBadSurfaces.HIGH);
        return p;
    }

    public static Preferences scenic() {
        Preferences p = new Preferences();
        p.setAvoidTraffic(AvoidTraffic.HIGH);
        p.setPreferParks(PreferParks.HIGH);
        return p;
    }
}
