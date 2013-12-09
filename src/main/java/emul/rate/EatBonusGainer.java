package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

public class EatBonusGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double result = 0.0;
        FutureTrooper sourceTrooper = source.getCurrent();
        FutureTrooper destTrooper = world.getCurrent();
        if (!sourceTrooper.isHoldingFood() && destTrooper.isHoldingFood()) {
            result += Constants.GRAB_FOOD_BONUS_WEIGHT;
        }
        if (!sourceTrooper.isHoldingGrenade() && destTrooper.isHoldingGrenade()) {
            result += Constants.GRAB_GRENADE_BONUS_WEIGHT;
        }
        if (!sourceTrooper.isHoldingMedkit() && destTrooper.isHoldingMedkit()) {
            result += Constants.GRAB_MEDKIT_BONUS_WEIGHT;
        }
        return result;
    }

}
