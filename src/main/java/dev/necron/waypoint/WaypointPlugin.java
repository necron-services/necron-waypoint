package dev.necron.waypoint;

import com.hakan.core.HCore;
import dev.necron.core.NecronPlugin;

public class WaypointPlugin extends NecronPlugin {

    @Override
    public void whenEnabled() {
        HCore.initialize(this);
    }

    @Override
    public void whenDisabled() {

    }
}