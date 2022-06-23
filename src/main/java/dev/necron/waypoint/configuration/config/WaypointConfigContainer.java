package dev.necron.waypoint.configuration.config;

import com.hakan.core.utils.ColorUtil;
import dev.necron.waypoint.configuration.WaypointConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings({"unchecked"})
public enum WaypointConfigContainer {

    LANGUAGE_YAML("settings.language-yaml", "en.yml"),
    BOSSBAR_ACTIVE("settings.boss-bar.active", true),
    BOSSBAR_TEXT("settings.boss-bar.text", ""),
    BOSSBAR_COLOR("settings.boss-bar.color", "WHITE"),
    BOSSBAR_STYLE("settings.boss-bar.style", "SOLID"),
    ACTIONBAR_ACTIVE("settings.action-bar.active", true),
    ACTIONBAR_TEXT("settings.action-bar.text", ""),
    HOLOGRAM_HEIGHT("hologram.height", 1),
    HOLOGRAM_LINES("hologram.lines", new ArrayList<>()),
    ;


    public static void reload() {
        Arrays.asList(WaypointConfigContainer.values())
                .forEach(container -> {
                    container.value = WaypointConfiguration.CONFIG.get(container.path);
                    if (container.value == null) {
                        container.value = container.defaultValue;
                        WaypointConfiguration.CONFIG.set(container.path, container.defaultValue);
                        WaypointConfiguration.CONFIG.save();
                    }
                });
    }


    private final String path;
    private final Object defaultValue;
    private Object value;

    WaypointConfigContainer(String path, Object defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.value = WaypointConfiguration.CONFIG.get(this.path);

        if (this.value == null) {
            this.value = defaultValue;
            WaypointConfiguration.CONFIG.set(this.path, defaultValue);
            WaypointConfiguration.CONFIG.save();
        }
    }

    public String getPath() {
        return this.path;
    }

    public <T> T getValue() {
        return (T) this.value;
    }

    public <T> T getValue(Class<T> tClass) {
        return tClass.cast(this.value);
    }

    public String asString() {
        return ColorUtil.colored(this.value + "");
    }

    public int asInt() {
        return Integer.parseInt(this.asString());
    }

    public long asLong() {
        return Long.parseLong(this.asString());
    }

    public double asDouble() {
        return Double.parseDouble(this.asString());
    }

    public boolean asBoolean() {
        return this.getValue(Boolean.class);
    }

    @Override
    public String toString() {
        return this.asString();
    }
}