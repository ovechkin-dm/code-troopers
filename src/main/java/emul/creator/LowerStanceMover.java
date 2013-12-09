package emul.creator;

import emul.FutureWorld;
import model.ActionType;
import model.Move;

public class LowerStanceMover implements FutureWorldMover{

    @Override
    public void makeMove(FutureWorld result, Move move) {
        result.getCurrent().changeStance(move.getAction());
    }

    @Override
    public ActionType getType() {
        return ActionType.LOWER_STANCE;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getGame().getStanceChangeCost();
    }
}
