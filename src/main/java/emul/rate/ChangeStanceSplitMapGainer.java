package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.TrooperStance;

public class ChangeStanceSplitMapGainer implements RateGainer{


    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (!WorldStateHolder.getInstance().getSplitMap()) {
            return 0.0;
        } else {
            FutureTrooper trooper = world.getCurrent();
            if (trooper.getStance().equals(TrooperStance.STANDING)) {
                return Constants.STAND_STANCE_WEIGHT;
            } else if (trooper.getStance().equals(TrooperStance.PRONE)) {
                return 17;
            } else {
                return 15;
            }
        }
    }
}
