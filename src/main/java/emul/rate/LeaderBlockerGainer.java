package emul.rate;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.CellType;
import util.MoveUtils;
import util.MyPoint;

public class LeaderBlockerGainer implements RateGainer {

    public static final double BLOCK_LEADER_WEIGHT = -100;

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper leader = findLeader(world);
        if (leader.getSourceTrooper().getId() == world.getCurrent().getSourceTrooper().getId()) {
            return 0;
        } else if (isBlocked(world, leader)) {
            return BLOCK_LEADER_WEIGHT;
        } else {
            return 0;
        }
    }

    private boolean isBlocked(FutureWorld world, FutureTrooper leader) {
        return !MoveUtils.isReachableAreaMoreThan(leader, 12, world);
    }

    private FutureTrooper findLeader(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getType().equals(WorldStateHolder.getInstance().getLeader())) {
                return trooper;
            }
        }
        return null;
    }

    private boolean isFree(FutureWorld world, int x, int y) {
        CellType[][] cells = world.getSource().getCells();
        if (x < 0 || x > cells.length - 1) {
            return false;
        } else if (y < 0 || y > cells[0].length - 1) {
            return false;
        } else if (alreadyTakenByTeammate(x, y, world)) {
            return false;
        } else if (cells[x][y] != CellType.FREE) {
            return false;
        } else {
            return true;
        }
    }

    private boolean alreadyTakenByTeammate(int x, int y, FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getX() == x && trooper.getY() == y) {
                return true;
            }
        }
        return false;
    }

}
