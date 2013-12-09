package emul.creator;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

import java.util.Iterator;

public class ThrowGrenadeMover implements FutureWorldMover {


    @Override
    public void makeMove(FutureWorld world, Move move) {
        for (FutureTrooper trooper : world.getEnemies()) {
            affectByGrenade(move, trooper, world);
        }
        removeDead(world);
    }

    private void removeDead(FutureWorld world) {
        Iterator<FutureTrooper> enemies = world.getEnemies().iterator();
        while (enemies.hasNext()) {
            FutureTrooper enemy = enemies.next();
            if (enemy.getHitPoints() <= 0) {
                enemies.remove();
            }
        }
    }

    private void affectByGrenade(Move move, FutureTrooper trooper, FutureWorld world) {
        int diffY = Math.abs(trooper.getY() - move.getY());
        int diffX = Math.abs(trooper.getX() - move.getX());
        int diff = diffX + diffY;
        if (diff == 0) {
            trooper.decreaseHitPoints(world.getGame().getGrenadeDirectDamage());
        } else if (diff == 1) {
            trooper.decreaseHitPoints(world.getGame().getGrenadeCollateralDamage());
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.THROW_GRENADE;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getGame().getGrenadeThrowCost();
    }
}
