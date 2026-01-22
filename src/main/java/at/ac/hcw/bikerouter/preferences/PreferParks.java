package at.ac.hcw.bikerouter.preferences;

public enum PreferParks {
    DEFAULT(1),
    LOW(1.2),
    HIGH(1.4);

    public final double factor;

    PreferParks (double factor) {
        this.factor = factor;
    }

    public boolean isActive() {
        return this != DEFAULT;
    }
}
