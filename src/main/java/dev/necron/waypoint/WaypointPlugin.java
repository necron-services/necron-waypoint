package dev.necron.waypoint;

import com.hakan.core.HCore;
import dev.necron.waypoint.user.WaypointUserHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class WaypointPlugin extends JavaPlugin {

    private static WaypointPlugin INSTANCE;

    public static WaypointPlugin getInstance() {
        return INSTANCE;
    }


    @Override
    public void onEnable() {
        INSTANCE = this;
        HCore.initialize(this);
        WaypointUserHandler.initialize(this);
    }

    @Override
    public void onDisable() {
        WaypointUserHandler.uninitialize();
    }
}