package dev.necron.waypoint.database.providers.sqlite;

import dev.necron.waypoint.user.WaypointUser;

import javax.annotation.Nonnull;

public enum WaypointSQLiteField {

    UID("uid", "VARCHAR(36) PRIMARY KEY"),
    CURRENT_WAYPOINT("current_waypoint", "TEXT"),
    WAYPOINTS("waypoints", "TEXT"),
    ;

    private final String path;
    private final String type;

    WaypointSQLiteField(String path, String type) {
        this.path = path;
        this.type = type;
    }

    @Nonnull
    public String getPath() {
        return this.path;
    }

    @Nonnull
    public String getType() {
        return this.type;
    }

    @Nonnull
    public String getValue(WaypointUser user) {
        switch (this) {
            case UID:
                return user.getUID().toString();
            case CURRENT_WAYPOINT:
                return user.getCurrentWaypoint() != null ? user.getCurrentWaypoint().getName() : "";
            case WAYPOINTS:
                return user.getWaypointListAsJson();
            default:
                return "";
        }
    }
}