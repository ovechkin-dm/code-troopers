package util;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.Trooper;

import java.util.List;

public class MoveUtils {

    public static int getDistanceTo(MyPoint from, MyPoint to) {
        AstarSolver solver = new AstarSolver(WorldStateHolder.getInstance().getField());
        List<MyPoint> result = solver.solve(from, to);
        if (result.size() == 0) {
            return 0;
        } else {
            return result.size() - 1;
        }
    }

    public static boolean isReachableAreaMoreThan(FutureTrooper trooper, int amount, FutureWorld futureWorld) {
        AstarSolver solver = new AstarSolver(WorldStateHolder.getInstance().getField());
        MyPoint point = new MyPoint(trooper.getX(), trooper.getY());
        for (FutureTrooper teammate : futureWorld.getTeammates()) {
            if (teammate.getSourceTrooper().getId() != trooper.getSourceTrooper().getId()) {
                solver.addCondition(new MyPoint(teammate.getX(), teammate.getY()));
            }
        }
        return solver.isReachableAreaMoreThan(point, amount);
    }

    public static double getAverageVision(FutureTrooper trooper) {
        //return Math.min(9, trooper.getSourceTrooper().getVisionRange() + 3);
        return 9.0;
    }

    public static List<MyPoint> getClosestPoints(FutureTrooper trooper, int amount, FutureWorld futureWorld) {
        AstarSolver solver = new AstarSolver(WorldStateHolder.getInstance().getField());
        MyPoint point = new MyPoint(trooper.getX(), trooper.getY());
        for (FutureTrooper teammate : futureWorld.getTeammates()) {
            if (teammate.getSourceTrooper().getId() != trooper.getSourceTrooper().getId()) {
                solver.addCondition(new MyPoint(teammate.getX(), teammate.getY()));
            }
        }
        return solver.getClosestPoints(point, amount);
    }

}
