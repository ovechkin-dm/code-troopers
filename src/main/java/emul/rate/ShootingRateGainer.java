package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;

import java.util.List;

public class ShootingRateGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double result = countHealth(source.getEnemies()) - countHealth(world.getEnemies());
        return result * Constants.ENEMY_SHOOT_SIMPLE_WEIGHT;
    }

    public double countHealth(List<FutureTrooper> enemies) {
        double result = 0.0;
        for (FutureTrooper trooper : enemies) {
            result += trooper.getHitPoints();
        }
        return result;
    }

}
