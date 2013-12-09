package emul.creator;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

public class UseMedkitMover implements FutureWorldMover{

    @Override
    public void makeMove(FutureWorld world, Move move) {
        makeHeal(world, move);
    }

    @Override
    public ActionType getType() {
        return ActionType.USE_MEDIKIT;
    }

    @Override
    public int getSpentActionPoints(FutureWorld world) {
        return world.getGame().getMedikitUseCost();
    }


    private void makeHeal(FutureWorld world, Move move) {
        if (world.getCurrent().getX() == move.getX() && world.getCurrent().getY() == move.getY()) {
            world.getCurrent().increaseHitPoints(world.getGame().getMedikitHealSelfBonusHitpoints());
        } else {
            FutureTrooper toHeal = findTrooper(world, move.getX(), move.getY());
            toHeal.increaseHitPoints(world.getGame().getMedikitBonusHitpoints());
        }
    }

    private FutureTrooper findTrooper(FutureWorld result, int x, int y) {
        for (FutureTrooper trooper : result.getRestOfTeam()) {
            if (trooper.getX() == x && trooper.getY() == y) {
                return trooper;
            }
        }
        return null;
    }


}
