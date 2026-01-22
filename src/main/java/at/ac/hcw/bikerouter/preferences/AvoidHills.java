package at.ac.hcw.bikerouter.preferences;

public enum AvoidHills {
    DEFAULT(1),
    LOW(0.7),
    HIGH(0.4);

    public final double factor;

    AvoidHills (double factor) {
        this.factor = factor;
    }

    public boolean isActive() {
        return this != DEFAULT;
    }
}
