package dev.necron.waypoint.gui.adapter.item;

import com.hakan.core.item.HItemBuilder;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MenuItem extends HItemBuilder implements Cloneable {

    public static MenuItem fromConfiguration(WaypointConfiguration configuration, String path) {
        HItemBuilder builder = new HItemBuilder(Material.valueOf(configuration.get(path + ".type")))
                .name(configuration.get(path + ".name"))
                .lores(configuration.get(path + ".lore"))
                .amount(configuration.get(path + ".amount"))
                .nbt(configuration.get(path + ".nbt"))
                .glow(configuration.get(path + ".glow"))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .durability(Byte.parseByte(configuration.get(path + ".datavalue").toString()));
        return new MenuItem(builder, configuration.get(path + ".slot", -1));
    }


    private int slot;

    public MenuItem(MenuItem menuItem) {
        super(menuItem);
        this.slot = menuItem.slot;
    }

    public MenuItem(Material material, int slot) {
        super(material);
        this.slot = slot;
    }

    public MenuItem(HItemBuilder builder, int slot) {
        super(builder);
        this.slot = slot;
    }

    public MenuItem(ItemStack itemStack, int slot) {
        super(itemStack);
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }

    public MenuItem slot(int slot) {
        this.slot = slot;
        return this;
    }

    @Nonnull
    @Override
    public MenuItem clone() {
        return new MenuItem(this);
    }
}