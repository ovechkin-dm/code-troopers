package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperType;

public class SniperCanShootGainer implements RateGainer {

    ShootingRateGainer shootingRateGainer = new ShootingRateGainer();

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (allAreInvisible(world)) {
            return 0.0;
        } else if (sniperCanShoot(world, source)) {
            return Constants.SNIPER_CAN_SHOOT_GAIN;
        } else if (sniperExists(world)) {
            return -Constants.SNIPER_CAN_SHOOT_GAIN;
        }
        return 0.0;
    }

    private boolean allAreInvisible(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (!currentIsInvisible(trooper, world)) {
                return false;
            }
        }
        return true;
    }

    private boolean currentIsInvisible(FutureTrooper trooper, FutureWorld world) {
        for (FutureTrooper enemy : world.getEnemies()) {
            boolean canSee = world.getSource().isVisible(enemy.getSourceTrooper().getVisionRange(),
                    enemy.getX(), enemy.getY(), enemy.getStance(),
                    trooper.getX(), trooper.getY(), trooper.getStance());
            if (canSee) {
                return false;
            }
        }
        return true;
    }

    private boolean sniperExists(FutureWorld world) {
        return findSniper(world) != null;
    }

    private boolean sniperCanShoot(FutureWorld world, FutureWorld source) {
        FutureTrooper sniper = findSniper(world);
        if (sniper == null) {
            return false;
        }
        for (FutureTrooper trooper : world.getEnemies()) {
            boolean visible = world.getSource().isVisible(sniper.getSourceTrooper().getShootingRange(),
                    sniper.getX(), sniper.getY(), sniper.getStance(),
                    trooper.getX(), trooper.getY(), trooper.getStance()
            );
            if (visible) {
                return true;
            }
        }
        return false;
    }

    private FutureTrooper findSniper(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getType().equals(TrooperType.SNIPER)) {
                return trooper;
            }
        }
        return null;
    }

}
