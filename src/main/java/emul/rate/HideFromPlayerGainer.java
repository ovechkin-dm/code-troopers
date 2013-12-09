package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import util.MoveUtils;

public class HideFromPlayerGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        for (FutureTrooper enemy : world.getEnemies()) {
            if (visibleForEnemy(enemy, world)) {
                return 0;
            }
        }
        return Constants.SHOOT_AND_HIDE_WEIGHT;
    }

    private boolean visibleForEnemy(FutureTrooper enemy, FutureWorld world) {
        for (FutureTrooper teammate : world.getTeammates()) {
            if (isVisible(teammate, enemy, world)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVisible(FutureTrooper teammate, FutureTrooper enemy, FutureWorld world) {
        return world.getSource().isVisible(MoveUtils.getAverageVision(enemy),
                enemy.getX(), enemy.getY(), enemy.getStance(),
                teammate.getX(), teammate.getY(), teammate.getStance());
    }

}
