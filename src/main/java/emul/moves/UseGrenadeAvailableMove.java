package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.ActionType;
import model.Move;
import util.MyPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UseGrenadeAvailableMove implements AvailableMove {

    private FutureTrooper current;

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        this.current = world.getCurrent();
        if (hasGrenade(world) && hasPoints(world) && withinDistance(world)) {
            return getGrenadeMoves(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private boolean withinDistance(FutureWorld world) {
        FutureTrooper current = world.getCurrent();
        MyPoint currentPoint = new MyPoint(current.getX(), current.getY());
        for (FutureTrooper enemy : world.getEnemies()) {
            MyPoint enemyPoint = new MyPoint(enemy.getX(), enemy.getY());
            double distance = enemyPoint.distanceTo(currentPoint) + 1;
            if (distance <= 6) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPoints(FutureWorld world) {
        return world.getCurrent().getActionPoints() >= world.getGame().getGrenadeThrowCost();
    }

    private boolean hasGrenade(FutureWorld world) {
        return world.getCurrent().isHoldingGrenade();
    }

    private List<Move> getGrenadeMoves(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        Move move = getBestAvailableMove(world);
        if (move != null) {
            result.add(move);
        }
        return result;
    }

    private Move getBestAvailableMove(FutureWorld world) {
        List<MyPoint> throwPoints = generateThrowPoints(world);
        int affectedSize = 0;
        MyPoint result = null;
        for (MyPoint point : throwPoints) {
            int size = getAffectedSize(point, world);
            if (size > affectedSize) {
                affectedSize = size;
                result = point;
            }
        }
        if (result != null) {
            Move move = new Move();
            move.setAction(ActionType.THROW_GRENADE);
            move.setX((int) result.getX());
            move.setY((int) result.getY());
            return move;
        }
        return null;
    }

    private int getAffectedSize(MyPoint point, FutureWorld world) {
        int result = 0;
        for (FutureTrooper trooper : world.getEnemies()) {
            int diffY = (int) Math.abs(trooper.getY() - point.getY());
            int diffX = (int) Math.abs(trooper.getX() - point.getX());
            int diff = diffX + diffY;
            if (diff <= 1) {
                result++;
            }
        }
        return result;
    }

    private List<MyPoint> generateThrowPoints(FutureWorld world) {
        List<MyPoint> result = new ArrayList<MyPoint>();
        for (FutureTrooper enemy : world.getEnemies()) {
            result.addAll(getNeighbours(enemy));
        }
        return result;
    }

    private Collection<? extends MyPoint> getNeighbours(FutureTrooper enemy) {
        List<MyPoint> result = new ArrayList<MyPoint>();
        if (withinBounds(enemy.getX(), enemy.getY())) result.add(new MyPoint(enemy.getX(), enemy.getY()));
        if (withinBounds(enemy.getX() + 1, enemy.getY())) result.add(new MyPoint(enemy.getX() + 1, enemy.getY()));
        if (withinBounds(enemy.getX() - 1, enemy.getY())) result.add(new MyPoint(enemy.getX() - 1, enemy.getY()));
        if (withinBounds(enemy.getX(), enemy.getY() + 1)) result.add(new MyPoint(enemy.getX(), enemy.getY() + 1));
        if (withinBounds(enemy.getX(), enemy.getY() - 1)) result.add(new MyPoint(enemy.getX(), enemy.getY() - 1));
        return result;
    }

    private boolean withinBounds(int x, int y) {
        int width = WorldStateHolder.getInstance().getWorld().getWidth();
        int height = WorldStateHolder.getInstance().getWorld().getHeight();
        if (x < 0 || x >= width) return false;
        if (y < 0 || y >= height) return false;
        MyPoint trooperPoint = new MyPoint(current.getX(), current.getY());
        if (trooperPoint.distanceTo(new MyPoint(x, y)) > 5) return false;
        return true;
    }

    private void affectByGrenade(Move move, FutureTrooper trooper, FutureWorld world) {
        int diffY = Math.abs(trooper.getY() - move.getY());
        int diffX = Math.abs(trooper.getX() - move.getX());
        int diff = diffX + diffY;
        if (diff == 0) {
            trooper.decreaseHitPoints(world.getGame().getGrenadeDirectDamage());
        } else if (diff == 1) {
            trooper.decreaseActionPoints(world.getGame().getGrenadeCollateralDamage());
        }
    }

}
