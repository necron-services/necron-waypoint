package dev.necron.waypoint.configuration.config;

import dev.necron.waypoint.configuration.WaypointConfiguration;

import java.util.Arrays;

@SuppressWarnings({"unchecked"})
public enum WaypointConfigContainer {

    DISABLED_WORLDS("settings.disabled-worlds"),

    ;


    public static void reload() {
        Arrays.asList(WaypointConfigContainer.values())
                .forEach(container -> container.value = WaypointConfiguration.CONFIG.get(container.path));
    }


    private final String path;
    private Object value;

    WaypointConfigContainer(String path) {
        this.path = path;
        this.value = WaypointConfiguration.CONFIG.get(this.path);
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

    public int asInt() {
        return Integer.parseInt(this.value.toString());
    }

    public long asLong() {
        return Long.parseLong(this.value.toString());
    }

    public double asDouble() {
        return Double.parseDouble(this.value.toString());
    }

    public String asString() {
        return this.value.toString();
    }

    public boolean asBoolean() {
        return this.getValue(Boolean.class);
    }

    @Override
    public String toString() {
        return this.getValue(String.class);
    }
}