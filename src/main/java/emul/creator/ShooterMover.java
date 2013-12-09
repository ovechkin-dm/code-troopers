package emul.creator;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

public class ShooterMover implements FutureWorldMover {
    @Override
    public void makeMove(FutureWorld world, Move move) {
        FutureTrooper enemy = findEnemy(world, move);
        if (enemy != null) {
            shootAt(enemy, world);
        }
    }

    private void shootAt(FutureTrooper enemy, FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        int damage = getDamageByStance(trooper);
        enemy.decreaseHitPoints(damage);
        if (enemy.getHitPoints() <= 0) {
            removeEnemyFromWorld(enemy, world);
        }
    }

    private void removeEnemyFromWorld(FutureTrooper enemy, FutureWorld world) {
        world.getEnemies().remove(enemy);
    }

    private int getDamageByStance(FutureTrooper trooper) {
        return trooper.getSourceTrooper().getDamage();
    }

    private FutureTrooper findEnemy(FutureWorld world, Move move) {
        for (FutureTrooper trooper : world.getEnemies()) {
            if (trooper.getX() == move.getX() || trooper.getY() == move.getY()) {
                return trooper;
            }
        }
        return null;
    }

    @Override
    public ActionType getType() {
        return ActionType.SHOOT;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getCurrent().getSourceTrooper().getShootCost();
    }
}
