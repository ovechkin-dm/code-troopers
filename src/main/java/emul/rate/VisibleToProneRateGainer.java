package emul.rate;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.Trooper;
import model.TrooperStance;

import java.util.List;

public class VisibleToProneRateGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        List<Trooper> enemies = WorldStateHolder.getInstance().getProneEnemies();
        double result = 0.0;
        for (Trooper enemy : enemies) {
            if (visibleToStand(enemy, world.getCurrent(), world)) {
                result += 1;
            }
        }
        return result * (-60);
    }

    private boolean visibleToStand(Trooper enemy, FutureTrooper current, FutureWorld world) {
        world.getSource().isVisible(enemy.getVisionRange(),
                enemy.getX(), enemy.getY(), TrooperStance.STANDING,
                current.getX(), current.getY(), current.getStance());
        return true;
    }


}
