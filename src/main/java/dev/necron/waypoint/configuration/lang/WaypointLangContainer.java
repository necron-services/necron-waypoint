package dev.necron.waypoint.configuration.lang;

import com.hakan.core.utils.ColorUtil;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum WaypointLangContainer {

    STARTED_THE_WALK("started-the-walk", ""),
    CANCELLED_THE_WALK("cancelled-the-walk", ""),
    REMOVED_WAYPOINT("removed-waypoint", ""),
    WAYPOINT_ALREADY_CURRENT("already-current-waypoint", ""),
    REACHED_WAYPOINT("reached", "");


    public static void reload() {
        Arrays.asList(WaypointLangContainer.values())
                .forEach(container -> {
                    Object message = WaypointConfiguration.LANG.get(container.path);
                    if (message != null) {
                        container.message = ColorUtil.colored(message.toString());
                    } else {
                        container.message = ColorUtil.colored(container.defaultValue);
                        WaypointConfiguration.LANG.set(container.path, container.defaultValue);
                        WaypointConfiguration.LANG.save();
                    }
                });
    }


    private final String path;
    private final String defaultValue;
    private String message;

    WaypointLangContainer(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;

        Object message = WaypointConfiguration.LANG.get(path);
        if (message != null) {
            this.message = ColorUtil.colored(message.toString());
        } else {
            this.message = ColorUtil.colored(defaultValue);
            WaypointConfiguration.LANG.set(this.path, defaultValue);
            WaypointConfiguration.LANG.save();
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getMessage() {
        return this.message;
    }

    public void send(Player player) {
        player.sendMessage(this.message);
    }

    @Override
    public String toString() {
        return this.message;
    }
}