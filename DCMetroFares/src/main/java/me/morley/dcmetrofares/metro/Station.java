package me.morley.dcmetrofares.metro;

import android.location.Address;

import java.util.Locale;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Station {

    private int id;
    private String slug;
    private String name;
    private Address location;
    private double parking;

    public Station(String slug, String name) {
        this.slug = slug;
        this.name = name;
        this.location = new Address(Locale.US);
    }

    public Station(String slug, String name, double latitude, double longitude, double parking) {
        this(slug, name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        this.parking = parking;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public String toString() {
        return name;
    }
}