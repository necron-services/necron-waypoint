package dev.necron.waypoint;

import dev.necron.waypoint.user.WaypointUser;
import org.bukkit.Location;

public class Waypoint {

    private final WaypointUser user;
    private final String name;
    private final Location location;

    public Waypoint(WaypointUser user, String name, Location location) {
        this.user = user;
        this.name = name;
        this.location = location;
    }

    public WaypointUser getUser() {
        return this.user;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location.clone();
    }
}