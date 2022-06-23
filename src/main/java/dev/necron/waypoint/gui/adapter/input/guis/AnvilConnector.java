package dev.necron.waypoint.gui.adapter.input.guis;

import com.hakan.core.HCore;
import com.hakan.core.ui.anvil.HAnvil;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.input.InputConnector;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AnvilConnector extends InputConnector {

    public AnvilConnector(@Nonnull WaypointConfiguration configuration) {
        super(configuration);
    }

    public void open(Player player) {
        HAnvil anvil = HCore.anvilBuilder(player)
                .leftItem(new ItemStack(super.itemType))
                .title(super.lines[0])
                .text(super.lines[1])
                .build();
        anvil.whenInputReceived(input -> HCore.syncScheduler().after(1).run(() -> super.inputCallback.accept(player, input)))
                .whenClosed(() -> HCore.syncScheduler().after(1).run(() -> super.cancelCallback.accept(player)))
                .open();
    }
}