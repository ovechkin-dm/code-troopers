package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import util.MoveUtils;

public class HideFromEnemiesGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        int size = getEnemiesCantSeeTrooperSize(world);
        int sourceSize = getEnemiesCantSeeTrooperSize(source);
        double result = size - sourceSize;
        return result * Constants.HIDE_FROM_ENEMIES_WEIGHT;
    }

    private int getEnemiesCantSeeTrooperSize(FutureWorld world) {
        int result = 0;
        for (FutureTrooper enemy : world.getEnemies()) {
            boolean canSee = world.getSource().isVisible(MoveUtils.getAverageVision(enemy),
                    enemy.getX(), enemy.getY(), enemy.getStance(),
                    world.getCurrent().getX(), world.getCurrent().getY(), world.getCurrent().getStance());
            if (!canSee) {
                result++;
            }
        }
        return result;
    }

}
