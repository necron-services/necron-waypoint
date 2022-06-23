package dev.necron.waypoint.gui.adapter.input;

import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.input.guis.AnvilConnector;
import dev.necron.waypoint.gui.adapter.input.guis.SignConnector;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings({"unchecked"})
public abstract class InputConnector {

    public static InputConnector register(WaypointConfiguration configuration) {
        String type = configuration.get("type");
        switch (type) {
            case "ANVIL":
                return new AnvilConnector(configuration);
            case "SIGN":
                return new SignConnector(configuration);
            default:
                throw new IllegalArgumentException("invalid input GUI type: " + type);
        }
    }


    protected final WaypointConfiguration configuration;
    protected final String type;
    protected final Material itemType;
    protected final String[] lines;
    protected final int inputLine;
    protected BiConsumer<Player, String> inputCallback;
    protected Consumer<Player> cancelCallback;

    public InputConnector(@Nonnull WaypointConfiguration configuration) {
        this.configuration = configuration;
        this.inputLine = configuration.get("input-line");
        this.type = configuration.get("type");
        this.itemType = Material.valueOf(configuration.get("item"));
        this.lines = (String[]) configuration.get("lines", List.class).toArray(new String[0]);
    }

    public void onInput(BiConsumer<Player, String> inputCallback) {
        this.inputCallback = inputCallback;
    }

    public void onCancel(Consumer<Player> cancelCallback) {
        this.cancelCallback = cancelCallback;
    }

    public abstract void open(Player player);
}