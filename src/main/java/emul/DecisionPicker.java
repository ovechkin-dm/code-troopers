package emul;

import emul.moves.AvailableMovesFacade;
import emul.rate.RateCounter;
import emul.state.WorldStateHolder;
import model.Game;
import model.Move;
import model.Trooper;
import model.World;

import java.util.ArrayList;
import java.util.List;

public class DecisionPicker {

    private AvailableMovesFacade availableMoves = new AvailableMovesFacade();
    private FutureWorldCreator creator = new FutureWorldCreator();
    private int iterations = 0;
    private static final int MAX_ITERATIONS = 4800;

    public void makeMove(World world, Trooper trooper, Game game, Move move) {
        iterations = 0;
        long start = System.currentTimeMillis();
        FutureWorld futureWorld = new FutureWorld(world, trooper, game);
        updateEnemies(futureWorld);
        futureWorld.setCellWeights(WorldStateHolder.getInstance().getCellWeights());
        RateCounter rateCounter = new RateCounter();
        FutureWorldCreator creator = new FutureWorldCreator();
        Move result = getBestMove(futureWorld, rateCounter, creator);
        addActionToMove(move, result, game, trooper);
        long end = System.currentTimeMillis() - start;
        System.out.println(end + " " + iterations);
    }

    private void updateEnemies(FutureWorld futureWorld) {
        List<FutureTrooper> result = new ArrayList<FutureTrooper>();
        for (Trooper enemy : WorldStateHolder.getInstance().getPotentialEnemies()) {
            if (WorldStateHolder.getInstance().isEnemyFake(enemy)) {
                result.add(new FakeEnemyTrooper(enemy));
            } else {
                result.add(new FutureTrooper(enemy));
            }
        }
        futureWorld.setEnemies(result);
    }

    private Move getBestMove(FutureWorld world, RateCounter rateCounter, FutureWorldCreator creator) {
        List<Move> moves = availableMoves.getAvailableMoves(world);
        Move result = null;
        double maxScore = Integer.MIN_VALUE;
        for (Move move : moves) {
            FutureWorld futureWorld = creator.create(world, move);
            double score = getBestMoveScore(futureWorld, rateCounter, world);
            if (score > maxScore) {
                result = move;
                maxScore = score;
            }
        }
        return result;
    }

    private double getBestMoveScore(FutureWorld world, RateCounter rateCounter, FutureWorld source) {
        if (iterations > MAX_ITERATIONS) {
            return Integer.MIN_VALUE;
        }
        List<Move> moves = availableMoves.getAvailableMoves(world);
        if (moves.isEmpty()) {
            return rateCounter.getRateForMove(world, source);
        }
        double maxScore = Integer.MIN_VALUE;
        iterations++;
        List<Move> maxTrace = new ArrayList<Move>();
        for (Move move : moves) {
            FutureWorld futureWorld = creator.create(world, move);
            double current = getBestMoveScore(futureWorld, rateCounter, source);
            if (current > maxScore) {
                maxScore = current;
            }
        }
        return maxScore;
    }

    private void addActionToMove(Move move, Move result, Game game, Trooper trooper) {
        if (result != null) {
            move.setAction(result.getAction());
            move.setX(result.getX());
            move.setY(result.getY());
        }
    }


}
