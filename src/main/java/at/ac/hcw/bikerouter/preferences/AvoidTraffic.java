package at.ac.hcw.bikerouter.preferences;

public enum AvoidTraffic {
    DEFAULT(1),
    LOW(0.5),
    HIGH(0.3);

    public final double factor;

    AvoidTraffic (double factor) {
        this.factor = factor;
    }

    public boolean isActive() {
        return this != DEFAULT;
    }
}
