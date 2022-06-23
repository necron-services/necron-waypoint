package dev.necron.waypoint.events;

import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.route.WaypointRoute;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class WaypointTickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }


    private final WaypointRoute route;
    private boolean cancelled;

    public WaypointTickEvent(WaypointRoute route) {
        this.route = route;
    }

    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }

    public WaypointRoute getTrack() {
        return this.route;
    }

    public Player getPlayer() {
        return this.route.getPlayer();
    }

    public Waypoint getWaypoint() {
        return this.route.getWaypoint();
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}