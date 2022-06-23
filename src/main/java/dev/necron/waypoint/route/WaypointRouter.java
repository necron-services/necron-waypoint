package dev.necron.waypoint.route;

import com.hakan.core.HCore;
import com.hakan.core.message.bossbar.HBossBar;
import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.configuration.lang.WaypointLangContainer;
import dev.necron.waypoint.events.WaypointReachEvent;
import dev.necron.waypoint.events.WaypointTickEvent;
import dev.necron.waypoint.route.listeners.PlayerRouteListener;
import dev.necron.waypoint.user.WaypointUser;
import dev.necron.waypoint.user.WaypointUserHandler;
import dev.necron.waypoint.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WaypointRouter {

    private static final Map<UUID, WaypointRoute> routes = new HashMap<>();

    public static void initialize() {
        HCore.registerListeners(new PlayerRouteListener());

        HCore.syncScheduler().every(1).run(() -> {
            List<WaypointRoute> users = new ArrayList<>(routes.values());
            if (users.size() == 0) return;

            users.forEach(route -> {
                Waypoint waypoint = route.getWaypoint();
                String actionBar = route.getActionBar();
                HBossBar bossBar = route.getBossBar();

                Player player = route.getPlayer();
                if (player == null) return;

                WaypointTickEvent event = new WaypointTickEvent(route);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) return;


                if (bossBar != null)
                    route.updateBossBar();
                if (actionBar != null)
                    HCore.sendActionBar(player, route.updateActionBar());


                if (waypoint.getLocation().distance(player.getLocation()) <= 1.0) {
                    WaypointRouter.finish(route);
                    Bukkit.getPluginManager().callEvent(new WaypointReachEvent(player, waypoint));

                    player.sendMessage(WaypointLangContainer.REACHED_WAYPOINT.getMessage()
                            .replace("%waypoint_name%", waypoint.getName()));
                    SoundUtil.playExperienceOrb(player);
                }
            });
        });
    }

    public static WaypointRoute start(Player player, Waypoint waypoint) {
        WaypointUser user = WaypointUserHandler.getByUID(player.getUniqueId());
        user.setCurrentWaypoint(waypoint);

        WaypointRoute route = new WaypointRoute(player, waypoint);
        routes.put(player.getUniqueId(), route);
        return route;
    }

    public static void finish(Player player) {
        WaypointUser user = WaypointUserHandler.getByUID(player.getUniqueId());

        user.setCurrentWaypoint(null);
        WaypointRoute route = routes.remove(user.getUID());

        route.getHologram().delete();
        HBossBar bossBar = route.getBossBar();
        if (bossBar != null) bossBar.delete();
    }

    public static void finish(WaypointRoute route) {
        WaypointUser user = route.getWaypoint().getUser();

        user.setCurrentWaypoint(null);
        routes.remove(user.getUID());

        route.getHologram().delete();
        HBossBar bossBar = route.getBossBar();
        if (bossBar != null) bossBar.delete();
    }
}