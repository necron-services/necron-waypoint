package dev.necron.waypoint.commands;

import com.hakan.core.command.HCommandAdapter;
import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import dev.necron.waypoint.WaypointPlugin;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.configuration.config.WaypointConfigContainer;
import dev.necron.waypoint.configuration.lang.WaypointLangContainer;
import dev.necron.waypoint.gui.guis.WaypointListUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@BaseCommand(
        name = "waypoint",
        aliases = "compass",
        description = "Waypoint command",
        usage = "/waypoint <reload>"
)
public class WaypointCommand implements HCommandAdapter {

    @SubCommand
    public void execute(Player player, String[] args) {
        WaypointListUI.openTo(player);
    }

    @SubCommand(
            args = "reload",
            permission = "waypoint.reload",
            permissionMessage = "§cYou don't have permission to use that command."
    )
    public void reloadCommand(CommandSender sender, String[] args) {
        sender.sendMessage("Reloading waypoint plugin...");

        WaypointConfiguration.initialize(WaypointPlugin.getInstance());
        WaypointConfigContainer.reload();
        WaypointLangContainer.reload();

        sender.sendMessage("Successfully reloaded waypoint plugin.");
    }
}