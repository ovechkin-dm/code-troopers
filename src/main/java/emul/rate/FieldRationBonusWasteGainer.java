package emul.rate;

import emul.Constants;
import emul.FutureWorld;

public class FieldRationBonusWasteGainer implements RateGainer{

    private ShootingRateGainer shootingRateGainer = new ShootingRateGainer();

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (!world.getCurrent().isHoldingFood() && source.getCurrent().isHoldingFood()) {
            if (shootingRateGainer.getRate(world, source) == 0) {
                return Constants.FIELD_RATION_BONUS_WASTE_PENALTY;
            }
        }
        return 0.0;
    }
}
