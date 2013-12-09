package emul.rate.position;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperStance;
import util.MyPoint;

import java.util.Set;

public class HiddenFromMostPointsGainer implements DangerPointsRateGainer {


    private static final double AVERAGE_VISIBILITY = 9;

    @Override
    public double getRate(Set<MyPoint> points, FutureWorld world) {
        double result = 0.0;
        FutureTrooper current = world.getCurrent();
        for (MyPoint point : points) {
            boolean visible = world.getSource().isVisible(AVERAGE_VISIBILITY,
                    (int) point.getX(), (int) point.getY(), TrooperStance.STANDING,
                    current.getX(), current.getY(), current.getStance());
            if (visible) {
                result -= 1;
            } else {
                result += 1;
            }
        }
        return result * Constants.HIDDEN_FROM_DANGER_AREA_WEIGHT;
    }

}
