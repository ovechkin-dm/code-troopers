package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperStance;

public class ChangeStanceRateGainer implements RateGainer {


    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper trooper = world.getCurrent();
        if (trooper.getStance().equals(TrooperStance.STANDING)) {
            return Constants.STAND_STANCE_WEIGHT;
        } else if (trooper.getStance().equals(TrooperStance.PRONE)) {
            return Constants.PRONE_STANCE_WEIGHT;
        } else {
            return Constants.KNEE_STANCE_WEIGHT;
        }
    }
}
