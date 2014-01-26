package me.morley.dcmetrofares.metro;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MetroGraph {

    /**
     * A map of sample (dummy) items, by ID.
     */
    private List<Station> stations = new ArrayList<Station>();
    private List<Route> routes = new ArrayList<Route>();

    public MetroGraph(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    /*public MetroGraph(Map<String, Station> stations, Map<String, Route> routes) {
        this.stations = stations;
        this.routes = routes;
    }

    public Station getStation(String id) {
        return stations.get(id);
    }

    public Route getRoute(String id) {
        return routes.get(id);
    }

    public List<Station> StationList() {
        return new ArrayList(stations.values());
    }*/
}
