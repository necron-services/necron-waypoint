package dev.necron.waypoint.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String serialize(Location location) {
        if (location == null || location.getWorld() == null)
            return null;

        return location.getWorld().getName() + ":" + location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
    }

    public static Location deserialize(String stringLoc) {
        if (stringLoc == null)
            return null;

        String[] splitLoc = stringLoc.split(":");
        return new Location(Bukkit.getWorld(splitLoc[0]), Double.parseDouble(splitLoc[1]), Double.parseDouble(splitLoc[2]), Double.parseDouble(splitLoc[3]));
    }

    public static String getRelativeDirection(Location from, Location to) {
        double xDis = to.getX() - from.getX();
        double zDis = to.getZ() - from.getZ();

        double radian = -Math.toDegrees(zDis > 0 ? Math.atan(xDis / zDis) : Math.atan(xDis / zDis) + Math.PI) - 90;
        if (radian < 0) radian += 360;

        double yaw = from.getYaw() - radian - 90;
        if (yaw < 0) yaw += 360;
        if (yaw < 0) yaw += 360;

        if (yaw <= 20) return "⬆";
        else if (yaw <= 65) return "⬉";
        else if (yaw <= 110) return "⬅";
        else if (yaw <= 155) return "⬋";
        else if (yaw <= 190) return "⬇";
        else if (yaw <= 245) return "⬊";
        else if (yaw <= 290) return "➡";
        else if (yaw <= 335) return "⬈";
        else return "⬆";
    }
}