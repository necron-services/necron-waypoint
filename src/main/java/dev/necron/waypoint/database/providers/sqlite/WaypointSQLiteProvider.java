package dev.necron.waypoint.database.providers.sqlite;

import com.hakan.core.database.DatabaseProvider;
import com.hakan.core.utils.query.create.CreateQuery;
import com.hakan.core.utils.query.delete.DeleteQuery;
import com.hakan.core.utils.query.insert.InsertQuery;
import com.hakan.core.utils.query.select.SelectQuery;
import com.hakan.core.utils.query.update.UpdateQuery;
import com.hakan.core.utils.yaml.HYaml;
import dev.necron.waypoint.user.WaypointUser;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WaypointSQLiteProvider extends DatabaseProvider<WaypointUser> {

    private final Connection connection;

    public WaypointSQLiteProvider(String location) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        HYaml.createFile(location);
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + location);
    }

    @Override
    public void create() {
        try (Statement statement = this.connection.createStatement()) {
            CreateQuery createQuery = new CreateQuery("waypoints");
            for (WaypointSQLiteField field : WaypointSQLiteField.values())
                createQuery.value(field.getPath(), field.getType());
            statement.executeUpdate(createQuery.build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    @Override
    public List<WaypointUser> getValues() {
        try (Statement statement = this.connection.createStatement()) {
            List<WaypointUser> waypoints = new ArrayList<>();

            SelectQuery query = new SelectQuery("waypoints").fromAll();
            ResultSet resultSet = statement.executeQuery(query.build());
            while (resultSet.next())
                waypoints.add(new WaypointUser(resultSet));

            return waypoints;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Nonnull
    @Override
    public WaypointUser getValue(@Nonnull String key, @Nonnull Object value) {
        try (Statement statement = this.connection.createStatement()) {
            SelectQuery query = new SelectQuery("waypoints").where(key, value).fromAll();
            ResultSet resultSet = statement.executeQuery(query.build());
            resultSet.next();

            return new WaypointUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(@Nonnull WaypointUser user) {
        this.insert(Collections.singletonList(user));
    }

    @Override
    public void update(@Nonnull WaypointUser user) {
        this.update(Collections.singletonList(user));
    }

    @Override
    public void delete(@Nonnull WaypointUser user) {
        this.delete(Collections.singletonList(user));
    }

    @Override
    public void insert(@Nonnull Collection<WaypointUser> users) {
        if (users.size() == 0)
            return;

        try (Statement statement = this.connection.createStatement()) {
            for (WaypointUser user : users)
                statement.execute(this.toInsertSQL(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@Nonnull Collection<WaypointUser> users) {
        if (users.size() == 0)
            return;

        try (Statement statement = this.connection.createStatement()) {
            for (WaypointUser user : users)
                statement.execute(this.toUpdateSQL(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@Nonnull Collection<WaypointUser> users) {
        if (users.size() == 0)
            return;

        try (Statement statement = this.connection.createStatement()) {
            for (WaypointUser user : users)
                statement.execute(this.toDeleteSQL(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    CONVERTERS
     */
    private String toInsertSQL(WaypointUser user) {
        InsertQuery query = new InsertQuery("waypoints");
        Arrays.asList(WaypointSQLiteField.values())
                .forEach(field -> query.value(field.getPath(), field.getValue(user)));
        return query.build();
    }

    private String toUpdateSQL(WaypointUser user) {
        UpdateQuery query = new UpdateQuery("waypoints");
        query.where(WaypointSQLiteField.UID.getPath(), WaypointSQLiteField.UID.getValue(user));
        Arrays.asList(WaypointSQLiteField.values())
                .forEach(field -> query.value(field.getPath(), field.getValue(user)));
        return query.build();
    }

    private String toDeleteSQL(WaypointUser user) {
        DeleteQuery query = new DeleteQuery("waypoints");
        query.where(WaypointSQLiteField.UID.getPath(), WaypointSQLiteField.UID.getValue(user));
        return query.build();
    }
}