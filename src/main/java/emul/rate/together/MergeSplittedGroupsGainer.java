package emul.rate.together;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.rate.RateGainer;
import util.MoveUtils;
import util.MyPoint;

public class MergeSplittedGroupsGainer implements RateGainer {
    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (!world.getEnemies().isEmpty()) {
            return 0.0;
        } else {
            return getPenalty(world, source);
        }
    }

    private double getPenalty(FutureWorld world, FutureWorld source) {
        double distance = getDistanceToFarTeammate(world);
        if (distance >= 8) {
            return -getDistanceToFarTeammate(world);
        } else {
            return 0.0;
        }
    }

    private double getDistanceToFarTeammate(FutureWorld world) {
        MyPoint trooperPoint = new MyPoint(world.getCurrent().getX(), world.getCurrent().getY());
        double minDistance = Integer.MIN_VALUE;
        for (FutureTrooper trooper : world.getRestOfTeam()) {
            MyPoint point = new MyPoint(trooper.getX(), trooper.getY());
            double distance = MoveUtils.getDistanceTo(trooperPoint, point);
            if (distance > minDistance) {
                minDistance = distance;
            }
        }
        return minDistance;
    }


}
