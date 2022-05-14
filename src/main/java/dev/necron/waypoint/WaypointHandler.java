package dev.necron.waypoint;

import com.hakan.core.HCore;
import dev.necron.waypoint.commands.WaypointCommand;
import dev.necron.waypoint.configuration.WaypointConfiguration;

public class WaypointHandler {

    public static void initialize(WaypointPlugin plugin) {

        //CONFIGURATION
        WaypointConfiguration.initialize(plugin);


        //BUKKIT
        HCore.registerCommands(new WaypointCommand("waypoint", "compass"));
    }
}