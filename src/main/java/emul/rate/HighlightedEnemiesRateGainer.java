package emul.rate;

import emul.FutureTrooper;
import emul.FutureWorld;
import model.ActionType;
import model.Move;

import java.util.HashSet;
import java.util.Set;

public class HighlightedEnemiesRateGainer implements RateGainer{

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        if (world.getEnemies().isEmpty()) {
            return 0.0;
        } else {
            return getHighlightedEnemiesRate(world) * 10;
        }
    }

    private double getHighlightedEnemiesRate(FutureWorld world) {
        Set<FutureTrooper> highlightedEnemies = new HashSet<FutureTrooper>();
        for (Move move : world.getTrace()) {
            if (move.getAction().equals(ActionType.MOVE)) {
                putToResult(highlightedEnemies, world, move);
            }
        }
        return highlightedEnemies.size();
    }

    private void putToResult(Set<FutureTrooper> highlightedEnemies, FutureWorld world, Move move) {
        for (FutureTrooper trooper : world.getEnemies()) {
            boolean visible = world.getSource().isVisible(world.getCurrent().getSourceTrooper().getVisionRange(),
                    move.getX(), move.getY(), world.getCurrent().getStance(),
                    trooper.getX(), trooper.getY(), trooper.getStance());
            if (visible) {
                highlightedEnemies.add(trooper);
            }
        }
    }

}
