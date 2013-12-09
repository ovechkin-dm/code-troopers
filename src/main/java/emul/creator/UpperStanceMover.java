package emul.creator;

import emul.FutureWorld;
import model.ActionType;
import model.Move;

public class UpperStanceMover implements FutureWorldMover {

    @Override
    public void makeMove(FutureWorld world, Move move) {
        world.getCurrent().changeStance(move.getAction());
    }

    @Override
    public ActionType getType() {
        return ActionType.RAISE_STANCE;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getGame().getStanceChangeCost();
    }
}
