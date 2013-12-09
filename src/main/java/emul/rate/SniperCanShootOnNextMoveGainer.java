package emul.rate;

import emul.FutureWorld;

public class SniperCanShootOnNextMoveGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        return 0;
    }
}
