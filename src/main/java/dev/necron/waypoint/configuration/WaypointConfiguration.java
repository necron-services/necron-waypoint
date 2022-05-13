package dev.necron.waypoint.configuration;

import com.hakan.core.utils.HYaml;
import dev.necron.waypoint.WaypointPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"unchecked"})
public class WaypointConfiguration {

    public static WaypointConfiguration CONFIG;
    public static WaypointConfiguration LANG;
    private final static Map<String, WaypointConfiguration> configurations = new HashMap<>();

    public static void initialize(WaypointPlugin plugin) {
        Arrays.asList("config.yml",

                "langs/en.yml",

                "guis/main.gui",
                "guis/waypoint_create.gui",
                "guis/waypoint_list.gui"
        ).forEach(path -> configurations.put(path, new WaypointConfiguration(HYaml.create(plugin, path, path))));

        CONFIG = configurations.get("config.yml");
        LANG = configurations.get("langs/" + CONFIG.get("settings.language-yaml"));
    }

    public static Map<String, WaypointConfiguration> getConfigurationsMap() {
        return configurations;
    }

    public static Collection<WaypointConfiguration> getConfigurations() {
        return configurations.values();
    }

    public static Optional<WaypointConfiguration> findByPath(String path) {
        return Optional.ofNullable(configurations.get(path));
    }

    public static WaypointConfiguration getByPath(String path) {
        return WaypointConfiguration.findByPath(path).orElseThrow(() -> new NullPointerException("claim configuration couldn't for path: " + path));
    }


    private final HYaml yaml;

    public WaypointConfiguration(HYaml yaml) {
        this.yaml = yaml;
    }

    public HYaml getYaml() {
        return this.yaml;
    }

    public void save() {
        this.yaml.save();
    }

    public void reload() {
        this.yaml.reload();
    }

    public void delete() {
        this.yaml.delete();
    }

    public <T> Optional<T> find(String path) {
        return Optional.ofNullable((T) this.yaml.get(path));
    }

    public <T> Optional<T> find(String path, T tDefault) {
        Object object = this.yaml.get(path);
        return Optional.ofNullable((object != null) ? (T) object : tDefault);
    }

    public <T> Optional<T> find(String path, Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(this.yaml.get(path)));
    }

    public <T> Optional<T> find(String path, T tDefault, Class<T> clazz) {
        Object object = this.yaml.get(path);
        return Optional.ofNullable((object != null) ? clazz.cast(this.yaml.get(path)) : tDefault);
    }

    public <T> T get(String path) {
        return (T) this.yaml.get(path);
    }

    public <T> T get(String path, T tDefault) {
        Object object = this.yaml.get(path);
        return (object != null) ? (T) object : tDefault;
    }

    public <T> T get(String path, Class<T> clazz) {
        return clazz.cast(this.yaml.get(path));
    }

    public <T> T get(String path, T tDefault, Class<T> clazz) {
        Object object = this.yaml.get(path);
        return (object != null) ? clazz.cast(this.yaml.get(path)) : tDefault;
    }

    public void set(String path, Object value) {
        this.yaml.set(path, value);
    }
}