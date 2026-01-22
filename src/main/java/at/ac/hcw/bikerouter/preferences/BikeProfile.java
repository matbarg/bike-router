package at.ac.hcw.bikerouter.preferences;

public enum BikeProfile {
    FAST("bike_fast"),
    SAFE("bike_safe"),
    COMFORT("bike_comfort"),
    SCENIC("bike_scenic");

    public final String label;

    private BikeProfile (String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
