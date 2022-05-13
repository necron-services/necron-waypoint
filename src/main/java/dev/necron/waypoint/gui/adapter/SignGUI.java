package dev.necron.waypoint.gui.adapter;

import com.hakan.core.HCore;
import com.hakan.core.ui.sign.HSign;
import dev.necron.waypoint.WaypointPlugin;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings({"unchecked"})
public abstract class SignGUI {

    protected final WaypointConfiguration configuration;

    protected HSign hSign;
    protected int inputLine;

    public SignGUI(@Nonnull WaypointConfiguration configuration) {
        this.configuration = configuration;
        this.inputLine = configuration.get("input-line");
        this.hSign = HCore.createSign(Material.valueOf(configuration.get("type")), (String[]) configuration.get("lines", List.class).toArray(new String[0]));
    }

    public void open(Player player) {
        this.hSign.open(player);
        this.hSign.onComplete(strings -> this.onComplete(player, strings));
    }


    protected abstract void onComplete(Player player, String[] lines);
}