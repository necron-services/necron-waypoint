package dev.necron.waypoint.gui.guis.create;

import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.SignGUI;
import org.bukkit.entity.Player;

public class WaypointCreateUI extends SignGUI {

    public WaypointCreateUI() {
        super(WaypointConfiguration.getByPath("guis/waypoint_create.gui"));
    }

    @Override
    protected void onComplete(Player player, String[] lines) {
        String name = lines[this.inputLine];

        if (name.length() < 3) {
            //todo: send error message
            return;
        }


    }
}