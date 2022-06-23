package dev.necron.waypoint.gui.guis.create;

import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.InputGUI;
import dev.necron.waypoint.gui.guis.WaypointListUI;
import dev.necron.waypoint.user.WaypointUser;
import dev.necron.waypoint.user.WaypointUserHandler;
import org.bukkit.entity.Player;

public class WaypointCreateUI extends InputGUI {

    public static void openTo(Player player) {
        new WaypointCreateUI().open(player);
    }


    private WaypointCreateUI() {
        super(WaypointConfiguration.getByPath("guis/waypoint_create.gui"));
    }

    @Override
    public void onInput(Player player, String name) {
        WaypointUser waypointUser = WaypointUserHandler.getByUID(player.getUniqueId());
        waypointUser.addWaypoint(new Waypoint(waypointUser, name, player.getLocation()));
        WaypointListUI.openTo(player);
    }

    @Override
    public void onCancel(Player player) {
        WaypointListUI.openTo(player);
    }
}