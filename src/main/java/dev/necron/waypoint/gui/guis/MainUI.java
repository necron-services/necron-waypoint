package dev.necron.waypoint.gui.guis;

import com.hakan.core.ui.inventory.HInventory;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.InventoryGUI;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class MainUI extends InventoryGUI {

    public MainUI() {
        super(WaypointConfiguration.getByPath("guis/main.gui"));
    }

    @Override
    public void onOpen(@Nonnull HInventory hInventory, @Nonnull Player player) {

    }
}