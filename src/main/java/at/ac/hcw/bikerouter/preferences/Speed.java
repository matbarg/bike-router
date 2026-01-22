package at.ac.hcw.bikerouter.preferences;

public enum Speed {
    SLOW(15),
    MODERATE(20),
    FAST(25);

    public final int kmh;

    private Speed (int kmh) {
        this.kmh = kmh;
    }
}
