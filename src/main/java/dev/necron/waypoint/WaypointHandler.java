package dev.necron.waypoint;

import com.hakan.core.HCore;
import dev.necron.waypoint.commands.WaypointCommand;

public class WaypointHandler {

    public static void initialize(WaypointPlugin plugin) {
        HCore.registerCommands(new WaypointCommand("waypoint", "compass"));
    }
}