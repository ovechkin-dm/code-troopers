package emul.rate;

import emul.Constants;
import emul.FutureWorld;
import util.MyPoint;

public class CloseToCenterMoveGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double last = getDistanceToCenter(world);
        double first = getDistanceToCenter(source);
        double diff = first - last;
        if (diff == 0) {
            return 0.0;
        }
        return diff * Constants.CLOSE_TO_CENTER_MOVE_GAINER;
    }

    private double getDistanceToCenter(FutureWorld world) {
        MyPoint center = new MyPoint(world.getSource().getWidth() / 2, world.getSource().getHeight() / 2);
        MyPoint trooper = new MyPoint(world.getCurrent().getX(), world.getCurrent().getY());
        return trooper.distanceTo(center);
    }
}
