package at.ac.hcw.bikerouter.preferences;

public enum AvoidBadSurfaces {
    DEFAULT(1),
    LOW(0.7),
    HIGH(0.4);

    public final double factor;

    AvoidBadSurfaces (double factor) {
        this.factor = factor;
    }

    public boolean isActive() {
        return this != DEFAULT;
    }
}
