package dev.necron.waypoint.route.listeners;

import com.hakan.core.message.bossbar.HBossBar;
import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.user.WaypointUser;
import dev.necron.waypoint.user.WaypointUserHandler;
import dev.necron.waypoint.route.WaypointRoute;
import dev.necron.waypoint.route.WaypointRouter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerRouteListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        WaypointUser user = WaypointUserHandler.getByUID(player.getUniqueId());
        Waypoint current = user.getCurrentWaypoint();

        if (current != null) {
            WaypointRoute route = WaypointRouter.start(player, current);

            HBossBar bossBar = route.getBossBar();
            bossBar.removeAll();
            bossBar.addPlayer(player);
        }
    }
}