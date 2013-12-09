package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

import java.util.ArrayList;
import java.util.List;

public class ShootAvailableMove implements AvailableMove {
    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        if (hasEnemy(world) && canShoot(world) && enoughPoints(world)) {
            return getShootMoves(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private List<Move> getShootMoves(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        FutureTrooper current = world.getCurrent();
        for (FutureTrooper trooper : world.getEnemies()) {
            boolean canShoot = world.getSource().isVisible(current.getSourceTrooper().getShootingRange(),
                    current.getX(), current.getY(), current.getStance(),
                    trooper.getX(), trooper.getY(), trooper.getStance());
            if (canShoot) {
                result.add(getShootingMove(trooper));
            }
        }
        return result;
    }

    private Move getShootingMove(FutureTrooper trooper) {
        Move move = new Move();
        move.setAction(ActionType.SHOOT);
        move.setX(trooper.getX());
        move.setY(trooper.getY());
        return move;
    }

    private boolean enoughPoints(FutureWorld world) {
        return world.getCurrent().getActionPoints() >= world.getCurrent().getSourceTrooper().getShootCost();
    }

    private boolean canShoot(FutureWorld world) {
        FutureTrooper current = world.getCurrent();
        for (FutureTrooper trooper : world.getEnemies()) {
            boolean canShoot = world.getSource().isVisible(current.getSourceTrooper().getShootingRange(),
                    current.getX(), current.getY(), current.getStance(),
                    trooper.getX(), trooper.getY(), trooper.getStance());
            if (canShoot) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnemy(FutureWorld world) {
        return !world.getEnemies().isEmpty();
    }
}
