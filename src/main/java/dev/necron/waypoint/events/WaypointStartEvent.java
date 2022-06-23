package dev.necron.waypoint.events;

import dev.necron.waypoint.Waypoint;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class WaypointStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();


    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }


    private final Player player;
    private final Waypoint waypoint;

    public WaypointStartEvent(Player player, Waypoint waypoint) {
        this.player = player;
        this.waypoint = waypoint;
    }

    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Waypoint getWaypoint() {
        return this.waypoint;
    }
}