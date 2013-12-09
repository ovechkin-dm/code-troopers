package emul.rate;

import emul.Constants;
import emul.FutureWorld;
import model.TrooperStance;
import model.TrooperType;

public class SniperKneeStanceWeightGainer implements RateGainer{

    SniperCanShootGainer gainer = new SniperCanShootGainer();

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (!world.getCurrent().getType().equals(TrooperType.SNIPER)) {
            return 0.0;
        }
        if (world.getEnemies().isEmpty()) {
            return 0.0;
        }
        if (gainer.getRate(world, source) > 0 && !world.getCurrent().getStance().equals(TrooperStance.STANDING)) {
            return Constants.SNIPER_KNEE_STANCE_WEIGHT;
        }
        return 0.0;
    }
}
