package dev.necron.waypoint;

import com.hakan.core.HCore;
import dev.necron.core.NecronPlugin;

public class WaypointPlugin extends NecronPlugin {

    private static WaypointPlugin INSTANCE;

    public static WaypointPlugin getInstance() {
        return INSTANCE;
    }



    @Override
    public void whenEnabled() {
        INSTANCE = this;
        HCore.initialize(this);
        WaypointHandler.initialize(this);
    }

    @Override
    public void whenDisabled() {

    }
}