package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

public class MedicHealRateGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double result = getHitPoints(world) - getHitPoints(source);
        return Constants.MEDIC_HEAL_WEIGHT * result;
    }

    private double getHitPoints(FutureWorld world) {
        double result = 0.0;
        for (FutureTrooper trooper : world.getTeammates()) {
            result = result + trooper.getHitPoints();
        }
        return result;
    }

}
