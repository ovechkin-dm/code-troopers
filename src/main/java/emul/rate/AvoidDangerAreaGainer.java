package emul.rate;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import emul.rate.RateGainer;
import emul.state.WorldStateHolder;
import model.CellType;

public class AvoidDangerAreaGainer implements RateGainer {

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (WorldStateHolder.getInstance().getLabyMap()) {
            return 0.0;
        }
        if (!isBlockedFromTwoSides(world.getCurrent(), world)) {
            return Constants.AVOID_DANGER_AREA_WEIGHT;
        }
        return 0.0;
    }

    private boolean isBlockedFromTwoSides(FutureTrooper current, FutureWorld world) {
        int blockedCounter = 0;
        if (!isFree(world, current.getX() - 1, current.getY())) blockedCounter = blockedCounter + 1;
        if (!isFree(world, current.getX() + 1, current.getY())) blockedCounter = blockedCounter + 1;
        if (!isFree(world, current.getX(), current.getY() + 1)) blockedCounter = blockedCounter + 1;
        if (!isFree(world, current.getX(), current.getY() - 1)) blockedCounter = blockedCounter + 1;
        return blockedCounter > 1;
    }

    private boolean isFree(FutureWorld world, int x, int y) {
        CellType[][] cells = WorldStateHolder.getInstance().getWorldCells();
        if (x < 0 || x > cells.length - 1) {
            return false;
        } else if (y < 0 || y > cells[0].length - 1) {
            return false;
        } else if (cells[x][y] != CellType.FREE) {
            return false;
        } else {
            return true;
        }
    }



}
