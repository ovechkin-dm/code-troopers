package emul.creator;

import emul.FutureWorld;
import model.ActionType;
import model.Move;

public interface FutureWorldMover {

    void makeMove(FutureWorld world, Move move);

    ActionType getType();

    int getSpentActionPoints(FutureWorld world);

}
