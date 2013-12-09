import emul.DecisionPicker;
import emul.state.WorldStateHolder;
import model.Game;
import model.Move;
import model.Trooper;
import model.World;

import java.util.Random;

public final class MyStrategy implements Strategy {

    private static int moveCount = -1;

    @Override
    public void move(Trooper self, World world, Game game, Move move) {
        WorldStateHolder.getInstance().setTrooper(self);
        WorldStateHolder.getInstance().setWorld(world);
        WorldStateHolder.getInstance().recomputeTurn();
        if (world.getMoveIndex() > moveCount) {
            moveCount = world.getMoveIndex();
            WorldStateHolder.getInstance().expireEnemies();
            WorldStateHolder.getInstance().recompute();
        }
        new DecisionPicker().makeMove(world, self, game, move);
    }
}
