package me.morley.dcmetrofares.metro;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Route {

    private Station origin;
    private Station destination;
    private double offPeak;
    private double onPeak;
    private double senior;

    public Route(Station origin, Station destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Route(Station origin, Station destination, double offPeak, double onPeak, double senior) {
        this(origin, destination);
        this.offPeak = offPeak;
        this.onPeak = onPeak;
        this.senior = senior;
    }

    public double getOffPeak() {
        return offPeak;
    }

    public double getOnPeak() {
        return onPeak;
    }

    public double getSenior() {
        return senior;
    }

    public String getID() {
        return origin.getSlug() + " -> " + origin.getSlug();
    }

    @Override
    public String toString() {
        return origin + " -> " + destination;
    }
}