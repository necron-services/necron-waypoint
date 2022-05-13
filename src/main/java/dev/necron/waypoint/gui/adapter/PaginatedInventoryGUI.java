package dev.necron.waypoint.gui.adapter;

import com.hakan.core.HCore;
import com.hakan.core.ui.inventory.HInventory;
import com.hakan.core.ui.inventory.item.ClickableItem;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.item.MenuItem;
import dev.necron.waypoint.utils.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public abstract class PaginatedInventoryGUI extends InventoryGUI {

    public PaginatedInventoryGUI(@Nonnull String id, @Nonnull String title, int size, @Nonnull InventoryType type, @Nonnull Set<Option> options) {
        super(id, title, size, type, options);
    }

    public PaginatedInventoryGUI(@Nonnull String id, @Nonnull String title, int size, @Nonnull InventoryType type) {
        super(id, title, size, type);
    }

    public PaginatedInventoryGUI(@Nonnull WaypointConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void onOpen(@Nonnull HInventory hInventory, @Nonnull Player player) {
        super.pagination.setSlots(super.configuration.get("item-slots"));
        super.pagination.setItems(this.load(player));
        super.fillAir(true);
        super.fillPage(super.pagination.getCurrentPage());
        super.loadOtherItems();

        if (super.pagination.getPreviousPage() >= super.pagination.getFirstPage()) {
            super.setItem(MenuItem.fromConfiguration(super.configuration, "items.previous-page"), (event) -> {
                if (HCore.spam("hclaims_ui_click_" + player.getUniqueId(), 100))
                    return;

                SoundUtil.playButtonClick(player);
                super.pagination.setCurrentPage(super.pagination.getPreviousPage());
                super.open(player);
            });
        }

        if (super.pagination.getNextPage() <= super.pagination.getLastPage()) {
            super.setItem(MenuItem.fromConfiguration(super.configuration, "items.next-page"), (event) -> {
                if (HCore.spam("hclaims_ui_click_" + player.getUniqueId(), 100))
                    return;

                SoundUtil.playButtonClick(player);
                super.pagination.setCurrentPage(super.pagination.getNextPage());
                super.open(player);
            });
        }
    }


    public abstract List<ClickableItem> load(@Nonnull Player player);
}