package util;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.Trooper;
import model.TrooperType;
import model.World;

import java.util.ArrayList;
import java.util.List;

public class WorldUtils {

    public static List<Trooper> getTeammates(World world) {
        List<Trooper> result = new ArrayList<Trooper>();
        for (Trooper trooper : world.getTroopers()) {
            if (trooper.isTeammate()) {
                result.add(trooper);
            }
        }
        return result;
    }

    public static List<Trooper> getRestOfTeam(World world, Trooper current) {
        List<Trooper> result = new ArrayList<Trooper>();
        for (Trooper trooper : world.getTroopers()) {
            if (trooper.isTeammate() && trooper.getId() != current.getId()) {
                result.add(trooper);
            }
        }
        return result;
    }

    public static List<Trooper> getEnemies(World world) {
        List<Trooper> result = new ArrayList<Trooper>();
        for (Trooper trooper : world.getTroopers()) {
            if (!trooper.isTeammate()) {
                result.add(trooper);
            }
        }
        return result;
    }

    public static FutureTrooper findEnemy(FutureWorld world, TrooperType type) {
        for (FutureTrooper enemy : world.getEnemies()) {
            if (enemy.getType().equals(type)) {
                return enemy;
            }
        }
        return null;
    }

}
