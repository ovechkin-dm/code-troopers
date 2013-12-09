package emul.rate.position;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperStance;
import util.MyPoint;

import java.util.Set;

public class CanSeeMostPointsGainer implements DangerPointsRateGainer {


    private static final double AVERAGE_VISIBILITY = 9;

    @Override
    public double getRate(Set<MyPoint> points, FutureWorld world) {
        double result = 0.0;
        FutureTrooper current = world.getCurrent();
        for (MyPoint point : points) {
            boolean visible = world.getSource().isVisible(getAverageVisibility(world),
                    current.getX(), current.getY(), TrooperStance.STANDING,
                    (int) point.getX(), (int) point.getY(), TrooperStance.STANDING
            );
            if (visible) {
                result += 1;
            } else {
                result -= 1;
            }
        }
        return result * Constants.CAN_SEE_DANGER_AREA_WEIGHT;
    }

    private double getAverageVisibility(FutureWorld world) {
        return AVERAGE_VISIBILITY;
    }

}
