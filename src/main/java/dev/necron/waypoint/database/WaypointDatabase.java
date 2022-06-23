package dev.necron.waypoint.database;

import com.hakan.core.HCore;
import com.hakan.core.database.DatabaseProvider;
import dev.necron.waypoint.WaypointPlugin;
import dev.necron.waypoint.database.providers.sqlite.WaypointSQLiteProvider;
import dev.necron.waypoint.user.WaypointUser;

import java.util.concurrent.TimeUnit;

public class WaypointDatabase {

    public static void initialize() {
        try {
            DatabaseProvider<WaypointUser> databaseProvider = new WaypointSQLiteProvider(WaypointPlugin.getInstance().getDataFolder() + "/data/waypoints.db");
            databaseProvider.create();
            databaseProvider.updateEvery(10, TimeUnit.MINUTES);
            HCore.registerDatabaseProvider(WaypointUser.class, databaseProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DatabaseProvider<WaypointUser> getProvider() {
        return HCore.getDatabaseProvider(WaypointUser.class);
    }


    private final WaypointUser waypointUser;

    public WaypointDatabase(WaypointUser waypointUser) {
        this.waypointUser = waypointUser;
    }

    public WaypointUser getWaypointUser() {
        return this.waypointUser;
    }

    public void insert() {
        getProvider().insert(this.waypointUser);
    }

    public void update() {
        getProvider().update(this.waypointUser);
    }

    public void delete() {
        getProvider().delete(this.waypointUser);
    }

    public void insertAsync() {
        HCore.asyncScheduler().run(this::insert);
    }

    public void updateAsync() {
        HCore.asyncScheduler().run(this::update);
    }

    public void deleteAsync() {
        HCore.asyncScheduler().run(this::delete);
    }
}