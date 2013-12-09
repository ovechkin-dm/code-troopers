package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;
import model.TrooperStance;

import java.util.ArrayList;
import java.util.List;

public class ChangeStanceAvailableMove implements AvailableMove {
    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        if (isEnoughPoints(world)) {
            return getChangeStanceVariations(world);
        } else {
            return new ArrayList<Move>();
        }
    }

    private List<Move> getChangeStanceVariations(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        FutureTrooper trooper = world.getCurrent();
        if (!trooper.getStance().equals(TrooperStance.STANDING)) {
            Move toAdd = new Move();
            toAdd.setAction(ActionType.RAISE_STANCE);
            result.add(toAdd);
        }
        if (!trooper.getStance().equals(TrooperStance.PRONE)) {
            Move toAdd = new Move();
            toAdd.setAction(ActionType.LOWER_STANCE);
            result.add(toAdd);
        }

        return result;
    }


    private boolean isEnoughPoints(FutureWorld world) {
        int currentPoints = world.getCurrent().getActionPoints();
        return currentPoints >= world.getGame().getStanceChangeCost();
    }
}
