package emul.rate;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperStance;
import util.MoveUtils;
import util.MyPoint;

import java.util.List;

public class EnemyCanShootRateGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double result = 0.0;
        for (FutureTrooper enemy : world.getEnemies()) {
            if (canShoot(world, enemy)) {
                result++;
            }
        }
        return -result * 30;
    }

    private boolean canShoot(FutureWorld world, FutureTrooper enemy) {
        List<MyPoint> points = MoveUtils.getClosestPoints(enemy, 12, world);
        for (MyPoint point : points) {
            boolean canshoot = world.getSource().isVisible(enemy.getSourceTrooper().getShootingRange(),
                    (int)point.getX(), (int) point.getY(), TrooperStance.STANDING,
                    world.getCurrent().getX(), world.getCurrent().getY(), world.getCurrent().getStance());
            if (canshoot) {
                return true;
            }
        }
        return false;
    }

}
