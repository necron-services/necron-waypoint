package dev.necron.waypoint.route;

import com.hakan.core.HCore;
import com.hakan.core.hologram.HHologram;
import com.hakan.core.message.bossbar.HBarColor;
import com.hakan.core.message.bossbar.HBarStyle;
import com.hakan.core.message.bossbar.HBossBar;
import dev.necron.waypoint.Waypoint;
import dev.necron.waypoint.configuration.config.WaypointConfigContainer;
import dev.necron.waypoint.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class WaypointRoute {

    private final UUID player;
    private final Location start;
    private final Waypoint waypoint;
    private final HHologram hologram;
    private HBossBar bossBar;
    private String actionBar;

    public WaypointRoute(Player player, Waypoint waypoint) {
        this.waypoint = waypoint;
        this.start = player.getLocation();
        this.player = player.getUniqueId();
        this.hologram = this.createHologram();

        if (WaypointConfigContainer.BOSSBAR_ACTIVE.asBoolean())
            this.bossBar = this.createBossBar();
        if (WaypointConfigContainer.ACTIONBAR_ACTIVE.asBoolean())
            this.actionBar = this.createActionBar();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.player);
    }

    public Waypoint getWaypoint() {
        return this.waypoint;
    }

    public Location getStartLocation() {
        return this.start;
    }

    public HHologram getHologram() {
        return this.hologram;
    }

    public HBossBar getBossBar() {
        return this.bossBar;
    }

    public String getActionBar() {
        return this.actionBar;
    }

    public String updateActionBar() {
        Player player = this.getPlayer();
        if (player == null) return null;
        if (this.actionBar == null) return null;


        double distance = this.waypoint.getLocation().distance(player.getLocation());
        return this.actionBar = WaypointConfigContainer.ACTIONBAR_TEXT.asString()
                .replace("%waypoint_name%", this.waypoint.getName())
                .replace("%distance%", (int) distance + "")
                .replace("%direction%", LocationUtil.getRelativeDirection(player.getLocation(), this.waypoint.getLocation()));
    }

    public HBossBar updateBossBar() {
        Player player = this.getPlayer();
        if (player == null) return null;
        if (this.bossBar == null) return null;


        double distance = this.waypoint.getLocation().distance(player.getLocation());
        double progress = distance / this.start.distance(this.waypoint.getLocation());
        if (progress > 1.0) progress = 1.0;
        if (progress < 0.0) progress = 0.0;

        String text = WaypointConfigContainer.BOSSBAR_TEXT.asString()
                .replace("%distance%", (int) distance + "")
                .replace("%waypoint_name%", this.waypoint.getName())
                .replace("%direction%", LocationUtil.getRelativeDirection(player.getLocation(), this.waypoint.getLocation()));
        HBarColor color = HBarColor.valueOf(WaypointConfigContainer.BOSSBAR_COLOR.asString());
        HBarStyle style = HBarStyle.valueOf(WaypointConfigContainer.BOSSBAR_STYLE.asString());

        this.bossBar.setTitle(text);
        this.bossBar.setColor(color);
        this.bossBar.setStyle(style);
        this.bossBar.setProgress(progress);
        return this.bossBar;
    }


    private String createActionBar() {
        Player player = this.getPlayer();
        if (player == null) return null;

        int distance = (int) this.waypoint.getLocation().distance(player.getLocation());
        return WaypointConfigContainer.ACTIONBAR_TEXT.asString()
                .replace("%distance%", distance + "")
                .replace("%waypoint_name%", this.waypoint.getName())
                .replace("%direction%", LocationUtil.getRelativeDirection(player.getLocation(), this.waypoint.getLocation()));
    }

    private HBossBar createBossBar() {
        Player player = this.getPlayer();
        if (player == null) return null;

        int distance = (int) this.waypoint.getLocation().distance(player.getLocation());
        String text = WaypointConfigContainer.BOSSBAR_TEXT.asString()
                .replace("%distance%", distance + "")
                .replace("%waypoint_name%", this.waypoint.getName())
                .replace("%direction%", LocationUtil.getRelativeDirection(player.getLocation(), this.waypoint.getLocation()));
        HBarColor color = HBarColor.valueOf(WaypointConfigContainer.BOSSBAR_COLOR.asString());
        HBarStyle style = HBarStyle.valueOf(WaypointConfigContainer.BOSSBAR_STYLE.asString());

        HBossBar bossBar = HCore.createBossBar(text, color, style);
        bossBar.addPlayer(player);
        return bossBar;
    }

    private HHologram createHologram() {
        String id = "waypoint_" + this.player + this.waypoint.getName();
        Location location = this.waypoint.getLocation().add(0.5, WaypointConfigContainer.HOLOGRAM_HEIGHT.asDouble(), 0.5);

        HHologram hologram = HCore.createHologram(id, location);
        hologram.addViewerByUID(this.player);
        hologram.showEveryone(false);

        List<String> lines = WaypointConfigContainer.HOLOGRAM_LINES.getValue();
        lines.forEach(line -> hologram.addLine(line.replace("%waypoint_name%", this.waypoint.getName())));

        return hologram;
    }
}