package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperType;
import util.WorldUtils;

public class EnemySniperCanSeeRateGainer implements RateGainer{
    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper enemySniper = WorldUtils.findEnemy(world, TrooperType.SNIPER);
        if (enemySniper == null) {
            return 0.0;
        }
        if (canSee(enemySniper)) {
            return Constants.ENEMY_SNIPER_CAN_SEE_PENALTY;
        }
        return 0.0;
    }

    private boolean canSee(FutureTrooper enemySniper) {
        return false;
    }
}
