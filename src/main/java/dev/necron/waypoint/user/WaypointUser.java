package dev.necron.waypoint.user;

import com.hakan.core.database.DatabaseObject;
import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.database.WaypointDatabase;
import dev.necron.waypoint.database.providers.sqlite.WaypointSQLiteField;
import dev.necron.waypoint.utils.LocationUtil;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WaypointUser implements DatabaseObject {

    private final UUID uid;
    private final WaypointDatabase database;
    private final Map<String, Waypoint> waypoints = new HashMap<>();
    private Waypoint currentWaypoint;

    public WaypointUser(UUID uid) {
        this.uid = uid;
        this.currentWaypoint = null;
        this.database = new WaypointDatabase(this);
    }

    public WaypointUser(ResultSet resultSet) throws SQLException {
        JSONObject waypointList = new JSONObject(resultSet.getString(WaypointSQLiteField.WAYPOINTS.getPath()));
        waypointList.keySet().forEach(name -> this.waypoints.put(name, new Waypoint(this, name, LocationUtil.deserialize(waypointList.getString(name)))));

        this.uid = UUID.fromString(resultSet.getString(WaypointSQLiteField.UID.getPath()));
        this.currentWaypoint = this.waypoints.get(resultSet.getString(WaypointSQLiteField.CURRENT_WAYPOINT.getPath()));
        this.database = new WaypointDatabase(this);
    }

    public UUID getUID() {
        return this.uid;
    }

    public Waypoint getCurrentWaypoint() {
        return this.currentWaypoint;
    }

    public WaypointDatabase getDatabase() {
        return this.database;
    }

    public Map<String, Waypoint> getWaypoints() {
        return this.waypoints;
    }

    public Optional<Waypoint> findWaypointByName(String name) {
        return Optional.ofNullable(this.waypoints.get(name));
    }

    public Waypoint getWaypointByName(String name) {
        return this.findWaypointByName(name).orElseThrow(() -> new NullPointerException("waypoint couldn't find for name: " + name));
    }

    public String getWaypointListAsJson() {
        JSONObject jsonObject = new JSONObject();
        this.waypoints.forEach((key, value) -> jsonObject.put(key, LocationUtil.serialize(value.getLocation())));
        return jsonObject.toString();
    }


    /*
    HANDLERS
     */
    public void setCurrentWaypoint(Waypoint waypoint) {
        this.currentWaypoint = waypoint;
        WaypointDatabase.getProvider().addUpdateObject(this);
    }

    public void addWaypoint(Waypoint waypoint) {
        this.waypoints.put(waypoint.getName(), waypoint);
        WaypointDatabase.getProvider().addUpdateObject(this);
    }

    public void removeWaypointByName(String name) {
        this.waypoints.remove(name);
        WaypointDatabase.getProvider().addUpdateObject(this);
    }

    public void removeWaypoint(Waypoint waypoint) {
        this.waypoints.remove(waypoint.getName());
        WaypointDatabase.getProvider().addUpdateObject(this);
    }
}