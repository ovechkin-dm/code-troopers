package emul.rate.together;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import emul.rate.RateGainer;
import emul.state.WorldStateHolder;
import model.TrooperType;
import util.MoveUtils;
import util.WorldUtils;

public class SupportFarFromLeaderGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper leader = findLeader(world);
        if (leader == null) {
            return 0.0;
        }
        if (!world.getEnemies().isEmpty()) {
            return 0.0;
        } else if (isSupport(world.getCurrent()) && !isSupport(leader)) {
            return supportCloseToLeaderPenalty(world, leader);
        }
        return 0.0;
    }

    private boolean isSupport(FutureTrooper current) {
        return current.getType().equals(TrooperType.FIELD_MEDIC) || current.getType().equals(TrooperType.SNIPER);
    }

    private double supportCloseToLeaderPenalty(FutureWorld world, FutureTrooper leader) {
        if (!MoveUtils.isReachableAreaMoreThan(world.getCurrent(), 5, world)) {
            return 0.0;
        }
        if (world.getCurrent().asPoint().distanceTo(leader.asPoint()) <= 1.1) {
            return Constants.SUPPORT_CLOSE_TO_LEADER_PENALTY;
        }
        return 0.0;
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
