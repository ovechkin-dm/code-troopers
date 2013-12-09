package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;
import model.TrooperType;

import java.util.ArrayList;
import java.util.List;

public class HealAvailableMove implements AvailableMove {

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        if (hasEnoughPointsToHeal(world) && isAbleToHeal(world) && injuredNear(world)) {
            return getHealMoves(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private List<Move> getHealMoves(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        List<FutureTrooper> injured = new ArrayList<FutureTrooper>();
        FutureTrooper current = world.getCurrent();
        FutureTrooper weakest = null;
        int lowestHitPoints = Integer.MAX_VALUE;
        for (FutureTrooper trooper : world.getTeammates()) {
            int diffY = Math.abs(trooper.getY() - current.getY());
            int diffX = Math.abs(trooper.getX() - current.getX());
            if (diffX + diffY <= 1 && trooper.getHitPoints() < trooper.getSourceTrooper().getMaximalHitpoints()) {
                if (trooper.getHitPoints() < lowestHitPoints) {
                    weakest = trooper;
                    lowestHitPoints = trooper.getHitPoints();
                }
                //injured.add(trooper);
            }
        }
        injured.add(weakest);
        for (FutureTrooper trooper : injured) {
            Move move = new Move();
            move.setAction(ActionType.HEAL);
            move.setX(trooper.getX());
            move.setY(trooper.getY());
            result.add(move);
        }
        return result;
    }

    private boolean injuredNear(FutureWorld world) {
        FutureTrooper current = world.getCurrent();
        for (FutureTrooper trooper : world.getTeammates()) {
            int diffY = Math.abs(trooper.getY() - current.getY());
            int diffX = Math.abs(trooper.getX() - current.getX());
            if (diffX + diffY <= 1 && trooper.getHitPoints() < trooper.getSourceTrooper().getMaximalHitpoints()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEnoughPointsToHeal(FutureWorld world) {
        return world.getCurrent().getActionPoints() >= world.getGame().getFieldMedicHealCost() * 2;
    }

    private boolean isAbleToHeal(FutureWorld world) {
        return world.getCurrent().getType().equals(TrooperType.FIELD_MEDIC);
    }
}
