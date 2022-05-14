package dev.necron.waypoint.gui.guis;

import com.hakan.core.ui.inventory.HInventory;
import com.hakan.core.ui.inventory.item.ClickableItem;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.PaginatedInventoryGUI;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class WaypointListUI extends PaginatedInventoryGUI {

    public WaypointListUI() {
        super(WaypointConfiguration.getByPath("guis/waypoint_list.gui"));
    }

    @Override
    public void onOpen(@Nonnull HInventory hInventory, @Nonnull Player player) {

    }

    @Override
    public List<ClickableItem> load(@Nonnull Player player) {
        return null;
    }
}