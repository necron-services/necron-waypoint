package dev.necron.waypoint.listeners;

import dev.necron.waypoint.user.WaypointUserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerRegisterListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!WaypointUserHandler.findByUID(player.getUniqueId()).isPresent())
            WaypointUserHandler.register(player.getUniqueId());
    }
}