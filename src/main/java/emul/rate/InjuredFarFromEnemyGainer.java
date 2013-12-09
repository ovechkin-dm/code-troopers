package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import util.MoveUtils;

public class InjuredFarFromEnemyGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (world.getCurrent().getHitPoints() > world.getCurrent().getSourceTrooper().getMaximalHitpoints() / 1.5) {
            return Constants.INJURED_VISIBLE_WEIGHT;
        }
        for (FutureTrooper enemy : world.getEnemies()) {
            if (isVisible(world.getCurrent(), enemy, world)) {
                return -Constants.INJURED_VISIBLE_WEIGHT;
            }
        }
        return Constants.INJURED_VISIBLE_WEIGHT;
    }


    private boolean isVisible(FutureTrooper teammate, FutureTrooper enemy, FutureWorld world) {
        return world.getSource().isVisible(MoveUtils.getAverageVision(enemy),
                enemy.getX(), enemy.getY(), enemy.getStance(),
                teammate.getX(), teammate.getY(), teammate.getStance());
    }


}
