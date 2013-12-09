package emul.moves;

import emul.FutureWorld;
import model.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableMovesFacade implements AvailableMove {

    List<AvailableMove> availableMovesList = new ArrayList<AvailableMove>();

    {
        availableMovesList.add(new ShootAvailableMove());
        availableMovesList.add(new UseGrenadeAvailableMove());
        availableMovesList.add(new HealAvailableMove());
        availableMovesList.add(new UseMedkitAvailableMove());
        availableMovesList.add(new EatFootBonusMove());
        availableMovesList.add(new MovementAvailableMove());
        availableMovesList.add(new ChangeStanceAvailableMove());

    }

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        List<Move> result = new ArrayList<Move>();
        //Collections.shuffle(availableMovesList);
        for (AvailableMove availableMove : availableMovesList) {
            result.addAll(availableMove.getAvailableMoves(world));
        }
        return result;
    }
}
