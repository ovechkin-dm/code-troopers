package emul.rate;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.TrooperType;

public class MapOpenerGainer implements RateGainer {


    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        double diff = getDiff(world, source);
        if (supportGuy(world) && notLeader(world)) {
            return 0.0;
        }
        if (!world.getEnemies().isEmpty()) {
            return 0.0;
        }
        if (isLeader(world)) {
            return diff;
        } else {
            return diff * 0.2;
        }
    }

    private boolean notLeader(FutureWorld world) {
        return !world.getCurrent().getType().equals(WorldStateHolder.getInstance().getLeader());
    }

    private boolean supportGuy(FutureWorld world) {
        return world.getCurrent().getType().equals(TrooperType.FIELD_MEDIC) ||
                world.getCurrent().getType().equals(TrooperType.SNIPER);
    }

    private boolean isLeader(FutureWorld world) {
        FutureTrooper leader = findLeader(world);
        return isCurrent(leader, world);
    }

    private double getDiff(FutureWorld current, FutureWorld source) {
        double result = 0;
        for (int i = 0; i < current.getSource().getWidth(); i++) {
            for (int j = 0; j < current.getSource().getHeight(); j++) {
                result += source.getCellWeights()[i][j] - current.getCellWeights()[i][j];
            }
        }
        return result;
    }

    private boolean isCurrent(FutureTrooper trooper, FutureWorld world) {
        return trooper.getSourceTrooper().getId() == world.getCurrent().getSourceTrooper().getId();
    }

    private FutureTrooper findLeader(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getType().equals(WorldStateHolder.getInstance().getLeader())) {
                return trooper;
            }
        }
        return null;
    }


}
