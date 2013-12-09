package emul.rate.position;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import model.TrooperType;
import util.MyPoint;

import java.util.Set;

public class NearCommanderRateGainer implements DangerPointsRateGainer{

    @Override
    public double getRate(Set<MyPoint> points, FutureWorld world) {
        FutureTrooper commander = findCommander(world);
        if (commander == null || commanderIsCurrent(world)) {
            return 0.0;
        } else {
            return farFromCommanderPenalty(world, commander);
        }
    }

    private double farFromCommanderPenalty(FutureWorld world, FutureTrooper commander) {
        if (world.getCurrent().asPoint().distanceTo(commander.asPoint()) >= world.getGame().getCommanderAuraRange()) {
            return Constants.FAR_FROM_COMMANDER_PENALTY;
        }
        return 0.0;
    }

    private boolean commanderIsCurrent(FutureWorld world) {
        return world.getCurrent().getType().equals(TrooperType.COMMANDER);
    }

    private FutureTrooper findCommander(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getType().equals(TrooperType.COMMANDER)) {
                return trooper;
            }
        }
        return null;
    }

}
