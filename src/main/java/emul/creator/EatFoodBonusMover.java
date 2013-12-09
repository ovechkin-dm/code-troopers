package emul.creator;

import emul.FutureWorld;
import model.ActionType;
import model.Move;

public class EatFoodBonusMover implements FutureWorldMover {

    @Override
    public void makeMove(FutureWorld world, Move move) {
        world.getCurrent().increaseActionPoints(world.getGame().getFieldRationBonusActionPoints());
        world.getCurrent().setHoldingFood(false);
    }

    @Override
    public ActionType getType() {
        return ActionType.EAT_FIELD_RATION;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getGame().getFieldRationEatCost();
    }
}
