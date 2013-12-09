package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

public class EnemyIsDeadGainer implements RateGainer {
    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        int deadEnemies = source.getEnemies().size() - world.getEnemies().size();
        double result = deadEnemies * Constants.DEAD_ENEMY_WEIGHT;
        return result;
    }
}
