package dev.necron.waypoint.user;

import com.hakan.core.HCore;
import dev.necron.waypoint.WaypointPlugin;
import dev.necron.waypoint.commands.WaypointCommand;
import dev.necron.waypoint.configuration.WaypointConfiguration;
import dev.necron.waypoint.database.WaypointDatabase;
import dev.necron.waypoint.listeners.PlayerRegisterListener;
import dev.necron.waypoint.listeners.PlayerWorldChangeListener;
import dev.necron.waypoint.route.WaypointRouter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WaypointUserHandler {

    private static final Map<UUID, WaypointUser> waypointUsers = new HashMap<>();

    public static void initialize(WaypointPlugin plugin) {

        //CONFIGURATION
        WaypointConfiguration.initialize(plugin);


        //DATABASE
        WaypointDatabase.initialize();


        //CACHE
        WaypointDatabase.getProvider().getValues()
                .forEach(waypointUser -> waypointUsers.put(waypointUser.getUID(), waypointUser));
        WaypointRouter.initialize();


        //BUKKIT
        HCore.registerCommands(
                new WaypointCommand());
        HCore.registerListeners(
                new PlayerWorldChangeListener(),
                new PlayerRegisterListener());
    }

    public static void uninitialize() {
        WaypointDatabase.getProvider()
                .getUpdater().updateAll();
    }


    public static Map<UUID, WaypointUser> getContentSafe() {
        return new HashMap<>(waypointUsers);
    }

    public static Map<UUID, WaypointUser> getContent() {
        return waypointUsers;
    }

    public static Collection<WaypointUser> getValuesSafe() {
        return new ArrayList<>(waypointUsers.values());
    }

    public static Collection<WaypointUser> getValues() {
        return waypointUsers.values();
    }

    public static Optional<WaypointUser> findByUID(UUID uuid) {
        return Optional.ofNullable(waypointUsers.get(uuid));
    }

    public static WaypointUser getByUID(UUID uuid) {
        return WaypointUserHandler.findByUID(uuid).orElseThrow(() -> new NullPointerException("waypoint user couldn't find for uid: " + uuid));
    }

    public static void register(UUID uid) {
        WaypointUserHandler.register(new WaypointUser(uid));
    }

    public static void register(WaypointUser user) {
        user.getDatabase().insertAsync();
        waypointUsers.put(user.getUID(), user);
    }

    public static void removeByUID(UUID uuid) {
        WaypointUserHandler.remove(WaypointUserHandler.getByUID(uuid));
    }

    public static void remove(WaypointUser user) {
        user.getDatabase().insertAsync();
        waypointUsers.remove(user.getUID());
    }
}