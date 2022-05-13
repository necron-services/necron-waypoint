package dev.necron.waypoint.commands;

import com.hakan.core.command.HCommandExecutor;
import dev.necron.waypoint.WaypointPlugin;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.configuration.config.WaypointConfigContainer;
import dev.necron.waypoint.configuration.lang.WaypointLangContainer;
import dev.necron.waypoint.gui.guis.MainUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class WaypointCommand extends HCommandExecutor {

    public WaypointCommand(@Nonnull String command, @Nonnull String... aliases) {
        super(command, null, aliases);
        super.subCommand("reload");
    }

    @Override
    public void onCommand(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                new MainUI().open(player);
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage("Reloading waypoint plugin...");

                WaypointConfiguration.initialize(WaypointPlugin.getInstance());
                WaypointConfigContainer.reload();
                WaypointLangContainer.reload();

                sender.sendMessage("Successfully reloaded waypoint plugin.");
            }
        }
    }
}