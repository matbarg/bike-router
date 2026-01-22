package at.ac.hcw.bikerouter.preferences;

public class Preferences {
    private AvoidBadSurfaces avoidBadSurfaces;
    private AvoidHills avoidHills;
    private AvoidTraffic avoidTraffic;
    private PreferBikeInfra preferBikeInfra;
    private PreferParks preferParks;

    public Preferences() {
    }

    public Preferences(AvoidBadSurfaces avoidBadSurfaces, AvoidHills avoidHills, AvoidTraffic avoidTraffic, PreferBikeInfra preferBikeInfra, PreferParks preferParks) {
        this.avoidBadSurfaces = avoidBadSurfaces;
        this.avoidHills = avoidHills;
        this.avoidTraffic = avoidTraffic;
        this.preferBikeInfra = preferBikeInfra;
        this.preferParks = preferParks;
    }

    public AvoidBadSurfaces getAvoidBadSurfaces() {
        return avoidBadSurfaces;
    }

    public void setAvoidBadSurfaces(AvoidBadSurfaces avoidBadSurfaces) {
        this.avoidBadSurfaces = avoidBadSurfaces;
    }

    public AvoidHills getAvoidHills() {
        return avoidHills;
    }

    public void setAvoidHills(AvoidHills avoidHills) {
        this.avoidHills = avoidHills;
    }

    public AvoidTraffic getAvoidTraffic() {
        return avoidTraffic;
    }

    public void setAvoidTraffic(AvoidTraffic avoidTraffic) {
        this.avoidTraffic = avoidTraffic;
    }

    public PreferBikeInfra getPreferBikeInfra() {
        return preferBikeInfra;
    }

    public void setPreferBikeInfra(PreferBikeInfra preferBikeInfra) {
        this.preferBikeInfra = preferBikeInfra;
    }

    public PreferParks getPreferParks() {
        return preferParks;
    }

    public void setPreferParks(PreferParks preferParks) {
        this.preferParks = preferParks;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "avoidBadSurfaces=" + avoidBadSurfaces +
                ", avoidHills=" + avoidHills +
                ", avoidTraffic=" + avoidTraffic +
                ", preferBikeInfra=" + preferBikeInfra +
                ", preferParks=" + preferParks +
                '}';
    }
}
