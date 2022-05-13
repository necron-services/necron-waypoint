package dev.necron.waypoint.configuration.lang;

import com.hakan.core.utils.ColorUtil;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public enum WaypointLangContainer {

    TIME_FORMAT("time-format"),

    ;


    public static void reload() {
        Arrays.asList(WaypointLangContainer.values())
                .forEach(container -> container.message = ColorUtil.colored(WaypointConfiguration.LANG.get(container.path)));
    }


    private final String path;
    private String message;

    WaypointLangContainer(String path) {
        this.path = path;
        this.message = ColorUtil.colored(WaypointConfiguration.LANG.get(this.path));
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