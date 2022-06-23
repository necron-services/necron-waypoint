package dev.necron.waypoint.listeners;

import dev.necron.waypoint.route.WaypointRouter;
import dev.necron.waypoint.user.WaypointUser;
import dev.necron.waypoint.user.WaypointUserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        WaypointUser user = WaypointUserHandler.getByUID(player.getUniqueId());
        if (user.getCurrentWaypoint() != null)
            WaypointRouter.finish(player);
    }
}