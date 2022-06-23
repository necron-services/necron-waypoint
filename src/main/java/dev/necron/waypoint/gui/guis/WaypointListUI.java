package dev.necron.waypoint.gui.guis;

import com.hakan.core.ui.inventory.item.ClickableItem;
import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.configuration.lang.WaypointLangContainer;
import dev.necron.waypoint.events.WaypointStartEvent;
import dev.necron.waypoint.gui.adapter.PaginatedInventoryGUI;
import dev.necron.waypoint.gui.adapter.item.MenuItem;
import dev.necron.waypoint.gui.guis.create.WaypointCreateUI;
import dev.necron.waypoint.route.WaypointRouter;
import dev.necron.waypoint.user.WaypointUser;
import dev.necron.waypoint.user.WaypointUserHandler;
import dev.necron.waypoint.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WaypointListUI extends PaginatedInventoryGUI {

    public static void openTo(Player player) {
        new WaypointListUI().open(player);
    }


    private WaypointListUI() {
        super(WaypointConfiguration.getByPath("guis/waypoint_list.gui"));
    }

    @Override
    protected void onOpen(@Nonnull Player player) {
        super.onOpen(player);

        super.setItem(MenuItem.fromConfiguration(super.configuration, "items.create-waypoint"), (event) -> {
            SoundUtil.playButtonClick(player);
            WaypointCreateUI.openTo(player);
        });

        super.setItem(MenuItem.fromConfiguration(super.configuration, "items.back"), (event) -> {
            SoundUtil.playButtonClick(player);
            super.close(player);
        });
    }

    @Override
    public List<ClickableItem> load(@Nonnull Player player) {
        WaypointUser waypointUser = WaypointUserHandler.getByUID(player.getUniqueId());
        MenuItem menuItem = MenuItem.fromConfiguration(super.configuration, "items.waypoint-item");

        List<ClickableItem> items = new ArrayList<>();
        for (Waypoint waypoint : waypointUser.getWaypoints().values()) {
            MenuItem item = menuItem.clone();
            item.name(item.getName().replace("%waypoint_name%", waypoint.getName()));
            item.glow(waypoint.equals(waypointUser.getCurrentWaypoint()));

            items.add(new ClickableItem(item.build(), event -> {
                SoundUtil.playButtonClick(player);
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (waypointUser.getCurrentWaypoint() != null) {
                        player.sendMessage(WaypointLangContainer.WAYPOINT_ALREADY_CURRENT.getMessage()
                                .replace("%waypoint_name%", waypoint.getName()));
                        return;
                    }

                    WaypointRouter.start(player, waypoint);
                    Bukkit.getPluginManager().callEvent(new WaypointStartEvent(player, waypoint));

                    player.sendMessage(WaypointLangContainer.STARTED_THE_WALK.getMessage()
                            .replace("%waypoint_name%", waypoint.getName()));

                    super.close(player);
                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    if (!waypoint.equals(waypointUser.getCurrentWaypoint())) return;

                    player.sendMessage(WaypointLangContainer.CANCELLED_THE_WALK.getMessage()
                            .replace("%waypoint_name%", waypoint.getName()));

                    WaypointRouter.finish(player);

                    WaypointListUI.openTo(player);
                } else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
                    player.sendMessage(WaypointLangContainer.REMOVED_WAYPOINT.getMessage()
                            .replace("%waypoint_name%", waypoint.getName()));

                    if (waypoint.equals(waypointUser.getCurrentWaypoint()))
                        WaypointRouter.finish(player);
                    waypointUser.removeWaypoint(waypoint);

                    WaypointListUI.openTo(player);
                }
            }));
        }

        return items;
    }
}