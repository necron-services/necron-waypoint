package dev.necron.waypoint.gui.adapter.input.guis;

import com.hakan.core.HCore;
import com.hakan.core.ui.sign.HSign;
import com.hakan.core.ui.sign.HSignType;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.gui.adapter.input.InputConnector;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SignConnector extends InputConnector {

    public SignConnector(@Nonnull WaypointConfiguration configuration) {
        super(configuration);
    }

    public void open(Player player) {
        HSignType hSignType = null;
        for (HSignType signType : HSignType.values())
            if (signType.asMaterial().equals(super.itemType))
                hSignType = signType;
        if (hSignType == null)
            throw new IllegalArgumentException("invalid sign type: " + super.itemType.name());

        HSign sign = HCore.signBuilder(player)
                .type(hSignType)
                .lines(super.lines)
                .build();

        sign.whenInputReceived(strings -> HCore.syncScheduler().after(1).run(() -> {
            String input = strings[super.inputLine];
            if (input == null || input.isEmpty()) {
                super.cancelCallback.accept(player);
            } else {
                super.inputCallback.accept(player, input);
            }
        })).open();
    }
}