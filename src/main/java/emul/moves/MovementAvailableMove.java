package emul.moves;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class MovementAvailableMove implements AvailableMove {

    @Override
    public List<Move> getAvailableMoves(FutureWorld world) {
        int points = world.getCurrent().getActionPoints();
        int pointsToMove = getPointsToMakeMove(world);
        if (points >= pointsToMove) {
            return generateAvailableMoves(world, world.getCurrent());
        } else {
            return new ArrayList<Move>();
        }
    }

    private int getPointsToMakeMove(FutureWorld world) {
        TrooperStance stance = world.getCurrent().getStance();
        Game game = world.getGame();
        if (stance.equals(TrooperStance.STANDING)) {
            return game.getStandingMoveCost();
        } else if (stance.equals(TrooperStance.KNEELING)) {
            return game.getKneelingMoveCost();
        } else return game.getProneMoveCost();
    }

    private List<Move> generateAvailableMoves(FutureWorld world, FutureTrooper trooper) {
        List<Move> result = new ArrayList<Move>();
        if (isFree(world, trooper.getX(), trooper.getY() + 1)) result.add(getMove(trooper.getX(), trooper.getY() + 1));
        if (isFree(world, trooper.getX(), trooper.getY() - 1)) result.add(getMove(trooper.getX(), trooper.getY() - 1));
        if (isFree(world, trooper.getX() + 1, trooper.getY())) result.add(getMove(trooper.getX() + 1, trooper.getY()));
        if (isFree(world, trooper.getX() - 1, trooper.getY())) result.add(getMove(trooper.getX() - 1, trooper.getY()));
        return result;
    }

    private Move getMove(int x, int y) {
        Move move = new Move();
        move.setAction(ActionType.MOVE);
        move.setX(x);
        move.setY(y);
        return move;
    }

    private boolean isFree(FutureWorld world, int x, int y) {
        CellType[][] cells = WorldStateHolder.getInstance().getWorldCells();
        if (x < 0 || x > cells.length - 1) {
            return false;
        } else if (y < 0 || y > cells[0].length - 1) {
            return false;
        } else if (alreadyTakenByTeammate(x, y, world)) {
            return false;
        } else if (cells[x][y] != CellType.FREE) {
            return false;
        } else {
            return true;
        }
    }

    private boolean alreadyTakenByTeammate(int x, int y, FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getX() == x && trooper.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
