package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import util.MyPoint;

public class FarFromEnemyGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (world.getEnemies().isEmpty()) {
            return 0;
        } else {
            double diff = getDistanceToEnemiesScore(world) - getDistanceToEnemiesScore(source);

            return diff * Constants.DISTANCE_TO_ENEMIES_WEIGHT;
        }

    }

    private double getDistanceToEnemiesScore(FutureWorld world) {
        double distance = 0;
        FutureTrooper current = world.getCurrent();
        MyPoint point = new MyPoint(current.getX(), current.getY());
        for (FutureTrooper trooper : world.getEnemies()) {
            MyPoint enemyPoint = new MyPoint(trooper.getX(), trooper.getY());
            distance += point.distanceTo(enemyPoint);
        }
        return distance;
    }

}
