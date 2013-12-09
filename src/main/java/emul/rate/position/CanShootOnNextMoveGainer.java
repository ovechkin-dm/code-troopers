package emul.rate.position;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperStance;
import util.MoveUtils;
import util.MyPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CanShootOnNextMoveGainer implements DangerPointsRateGainer {

    @Override
    public double getRate(Set<MyPoint> points, FutureWorld world) {
        double result = 0.0;
        for (FutureTrooper trooper : world.getEnemies()) {
            result += getResultForPoint(trooper.asPoint(), world);
        }
        return result * 3;
    }

    private double getResultForPoint(MyPoint point, FutureWorld world) {
        List<MyPoint> shootPoints = getShootPoints(point, world);
        FutureTrooper current = world.getCurrent();
        for (MyPoint shootPoint : shootPoints) {
            double distance = MoveUtils.getDistanceTo(shootPoint, current.asPoint());
            if (distance <= 4) {
                return 1.0;
            }
        }
        return 0.0;
    }

    private List<MyPoint> getShootPoints(MyPoint point, FutureWorld world) {
        List<MyPoint> closest = MoveUtils.getClosestPoints(world.getCurrent(), 10, world);
        List<MyPoint> result = new ArrayList<MyPoint>();
        for (MyPoint shootPoint : closest) {
            boolean canShoot = world.getSource().isVisible(world.getCurrent().getSourceTrooper().getShootingRange(),
                    (int) shootPoint.getX(), (int) shootPoint.getY(), TrooperStance.STANDING,
                    (int) point.getX(), (int) point.getY(), TrooperStance.STANDING);
            if (canShoot) {
                result.add(shootPoint);
            }
        }
        return result;
    }
}
