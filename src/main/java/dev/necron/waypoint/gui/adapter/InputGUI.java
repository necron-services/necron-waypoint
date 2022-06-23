package dev.necron.waypoint.gui.adapter;

import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.input.InputConnector;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public abstract class InputGUI {

    protected final InputConnector connector;

    public InputGUI(@Nonnull WaypointConfiguration configuration) {
        this.connector = InputConnector.register(configuration);
        this.connector.onInput(this::onInput);
        this.connector.onCancel(this::onCancel);
    }

    public void open(Player player) {
        this.connector.open(player);
    }

    public abstract void onInput(Player player, String input);

    public abstract void onCancel(Player player);
}