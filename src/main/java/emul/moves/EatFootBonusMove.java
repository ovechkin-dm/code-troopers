package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

import java.util.ArrayList;
import java.util.List;

public class EatFootBonusMove implements AvailableMove {

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        if (isHoldingFood(world) && hasEnemyNearby(world) && needToUse(world) && hasEnoughPoints(world)) {
            return getFoodBonusMove(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private boolean hasEnoughPoints(FutureWorld world) {
        return world.getCurrent().getActionPoints() >= world.getGame().getFieldRationEatCost();
    }

    private boolean needToUse(FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        int foodBonus = world.getGame().getFieldRationBonusActionPoints() - world.getGame().getFieldRationEatCost();
        return trooper.getActionPoints() + foodBonus <= trooper.getSourceTrooper().getInitialActionPoints();
    }

    private List<Move> getFoodBonusMove(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        Move move = new Move();
        move.setAction(ActionType.EAT_FIELD_RATION);
        result.add(move);
        return result;
    }

    private boolean hasEnemyNearby(FutureWorld world) {
        return !world.getEnemies().isEmpty();
    }

    private boolean isHoldingFood(FutureWorld world) {
        return world.getCurrent().isHoldingFood();
    }
}
