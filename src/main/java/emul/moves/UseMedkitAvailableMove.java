package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

import java.util.ArrayList;
import java.util.List;

public class UseMedkitAvailableMove implements AvailableMove {

    private static final double HP_LEFT_COEFF = 2;

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        if (hasMedKit(world) && hasPointForMedkit(world) && hasInjuredNear(world)) {
            return getMedkitMoves(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private List<Move> getMedkitMoves(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        List<FutureTrooper> injured = new ArrayList<FutureTrooper>();
        FutureTrooper current = world.getCurrent();
        for (FutureTrooper trooper : world.getTeammates()) {
            int diffY = Math.abs(trooper.getY() - current.getY());
            int diffX = Math.abs(trooper.getX() - current.getX());
            if (diffX + diffY <= 1 && trooper.getHitPoints() < trooper.getSourceTrooper().getMaximalHitpoints() / HP_LEFT_COEFF) {
                injured.add(trooper);
            }
        }
        for (FutureTrooper trooper : injured) {
            Move move = new Move();
            move.setAction(ActionType.USE_MEDIKIT);
            move.setX(trooper.getX());
            move.setY(trooper.getY());
            result.add(move);
        }
        return result;
    }

    private boolean hasInjuredNear(FutureWorld world) {
        FutureTrooper current = world.getCurrent();
        for (FutureTrooper trooper : world.getTeammates()) {
            int diffY = Math.abs(trooper.getY() - current.getY());
            int diffX = Math.abs(trooper.getX() - current.getX());
            if (diffX + diffY <= 1 && trooper.getHitPoints() < trooper.getSourceTrooper().getMaximalHitpoints() / HP_LEFT_COEFF) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPointForMedkit(FutureWorld world) {
        return world.getCurrent().getActionPoints() >= world.getGame().getMedikitUseCost();
    }

    private boolean hasMedKit(FutureWorld world) {
        return world.getCurrent().isHoldingMedkit();
    }

}
