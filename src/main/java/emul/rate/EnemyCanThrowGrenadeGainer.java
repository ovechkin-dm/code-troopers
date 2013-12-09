package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

public class EnemyCanThrowGrenadeGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        for (FutureTrooper enemy : world.getEnemies()) {
            if (enemyCanThrowGrenade(enemy, world.getCurrent(), world)) {
                return Constants.ENEMY_CAN_THROW_GRENADE_PENALTY;
            }
        }
        return 0.0;
    }

    private Boolean enemyCanThrowGrenade(FutureTrooper enemy, FutureTrooper current, FutureWorld world) {
      if (!enemy.isHoldingGrenade()) {
          return false;
      } else if (enemy.asPoint().distanceTo(current.asPoint()) <= 7.1) {
          return true;
      }
      return false;
    }


}
