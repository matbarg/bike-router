package at.ac.hcw.bikerouter.preferences;

public enum PreferBikeInfra {
    DEFAULT(1),
    LOW(1.2),
    HIGH(1.4);

    public final double factor;

    PreferBikeInfra (double factor) {
        this.factor = factor;
    }

    public boolean isActive() {
        return this != DEFAULT;
    }
}
