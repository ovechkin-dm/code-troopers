package emul.rate.position;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.rate.RateGainer;
import emul.state.WorldStateHolder;
import model.CellType;
import model.TrooperType;
import util.MyPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AtGoodPositionRateGainer implements RateGainer {

    private static List<DangerPointsRateGainer> gainers = new ArrayList<DangerPointsRateGainer>();

    static {
        gainers.add(new HiddenFromMostPointsGainer());
        gainers.add(new CanSeeMostPointsGainer());
        gainers.add(new NearCommanderRateGainer());
        gainers.add(new CanShootOnNextMoveGainer());
    }


    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (world.getEnemies().isEmpty()) {
            return 0.0;
        }
        Set<MyPoint> dangerPoints = getDangerPoints(world);
        double result = 0.0;
        for (DangerPointsRateGainer gainer : gainers) {
            result += gainer.getRate(dangerPoints, world);
        }
        return result;
    }

    private Set<MyPoint> getDangerPoints(FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        Set<MyPoint> result = new HashSet<MyPoint>();
        for (FutureTrooper enemy : world.getEnemies()) {
            double diffX = getSimpleDiff(trooper.getX(), enemy.getX());
            double diffY = getSimpleDiff(trooper.getY(), enemy.getY());
            result.addAll(findClosestPoints(enemy, diffX, diffY));
        }
        return result;
    }

    private Set<MyPoint> findClosestPoints(FutureTrooper enemy, double diffX, double diffY) {
        Set<MyPoint> result = new HashSet<MyPoint>();
        if (diffY != 0) {
            for (int i = enemy.getX() - 3; i < enemy.getX() + 4; i++) {
                for (int counter = enemy.getY(); counter != enemy.getY() + diffY * 2; counter += diffY) {
                    MyPoint point = new MyPoint(i, counter);
                    if (isFree(point)) {
                        result.add(point);
                    }
                }
            }
        }
        if (diffX != 0) {
            for (int i = enemy.getY() - 3; i < enemy.getY() + 4; i++) {
                for (int counter = enemy.getX(); counter < enemy.getX() + diffX * 2; counter += diffX) {
                    MyPoint point = new MyPoint(counter, i);
                    if (isFree(point)) {
                        result.add(point);
                    }
                }
            }
        }
        return result;
    }

    private boolean isFree(MyPoint first) {
        if (first.getX() < 0 || first.getX() > WorldStateHolder.getInstance().getWorld().getWidth() - 1) return false;
        if (first.getY() < 0 || first.getY() > WorldStateHolder.getInstance().getWorld().getHeight() - 1) return false;
        CellType[][] cells = WorldStateHolder.getInstance().getWorldCells();
        return cells[((int) first.getX())][((int) first.getY())] == CellType.FREE;
    }

    private double getSimpleDiff(int x, int enemyX) {
        if (Math.abs(x - enemyX) <= 3) {
            return 0;
        } else if (x > enemyX) {
            return 1;
        } else {
            return -1;
        }
    }

}
