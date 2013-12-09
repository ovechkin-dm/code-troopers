package emul;

import emul.creator.*;
import emul.state.WorldStateHolder;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class FutureWorldCreator {

    private List<FutureWorldMover> movers = new ArrayList<FutureWorldMover>();

    {
        movers.add(new SimpleMoveMover());
        movers.add(new HealMover());
        movers.add(new LowerStanceMover());
        movers.add(new UpperStanceMover());
        movers.add(new ShooterMover());
        movers.add(new EatFoodBonusMover());
        movers.add(new ThrowGrenadeMover());
        movers.add(new UseMedkitMover());
    }

    public FutureWorld create(FutureWorld last, Move move) {
        FutureWorld result = last.copy();
        result.getTrace().add(move);
        makeFutureMove(result, move);
        return result;
    }

    private void makeFutureMove(FutureWorld result, Move move) {
        for (FutureWorldMover mover : movers) {
            if (mover.getType().equals(move.getAction())) {
                mover.makeMove(result, move);
                result.getCurrent().decreaseActionPoints(mover.getSpentActionPoints(result));
                return;
            }
        }
    }

}
