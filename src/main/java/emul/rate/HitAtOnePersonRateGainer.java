package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HitAtOnePersonRateGainer implements RateGainer {
    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper sourceWeakest = getWeakestTrooper(source.getEnemies(), world);
        if (sourceWeakest == null) {
            return 0.0;
        }
        FutureTrooper current = findMatchingTrooper(sourceWeakest, world);
        double result = sourceWeakest.getHitPoints() - current.getHitPoints();
        return result * Constants.SHOOT_AT_WEAKEST_BONUS_WEIGHT;
    }

    private FutureTrooper findMatchingTrooper(FutureTrooper sourceWeakest, FutureWorld world) {
        for (FutureTrooper trooper : world.getEnemies()) {
            if (trooper.getSourceTrooper().getId() == sourceWeakest.getSourceTrooper().getId()) {
                return trooper;
            }
        }
        return null;
    }


    public FutureTrooper getWeakestTrooper(List<FutureTrooper> enemies, FutureWorld world) {
        double minHitPoints = Integer.MAX_VALUE;
        FutureTrooper weakest = null;
        for (FutureTrooper trooper : enemies) {
            if (trooper.getHitPoints() < minHitPoints && !isDead(trooper, world)) {
                minHitPoints = trooper.getHitPoints();
                weakest = trooper;
            }
        }
        return weakest;
    }

    private boolean isDead(FutureTrooper trooper, FutureWorld world) {
        for (FutureTrooper enemy : world.getEnemies()) {
            if (enemy.getSourceTrooper().getId() == trooper.getSourceTrooper().getId()) {
                return false;
            }
        }
        return true;
    }

}
