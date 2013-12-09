package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

public class LandedOnSameSpotGainer implements RateGainer {
    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper last = world.getCurrent();
        FutureTrooper first = source.getCurrent();
        if (last.getX() == first.getX() && last.getY() == first.getY()) {
            return Constants.LANDED_ON_SAME_POSITION_PENALTY;
        }
        return 0.0;
    }
}
