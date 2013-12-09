package emul.creator;

import emul.FutureTrooper;
import emul.FutureWorld;
import emul.state.WorldStateHolder;
import model.*;

public class SimpleMoveMover implements FutureWorldMover {

    @Override
    public void makeMove(FutureWorld world, Move move) {
        FutureTrooper trooper = world.getCurrent();
        trooper.setX(move.getX());
        trooper.setY(move.getY());
        recountCellWeights(world);
        recountBonuses(world);
    }

    private void recountBonuses(FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        Bonus toEat = null;
        for (Bonus bonus : world.getBonuses()) {
            if (bonus.getX() == trooper.getX() && bonus.getY() == trooper.getY()) {
                toEat = bonus;
                break;
            }
        }
        if (toEat != null) {
            eatBonus(toEat, world);
        }
    }

    private void eatBonus(Bonus bonus, FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        world.getBonuses().remove(bonus);
        if (bonus.getType().equals(BonusType.FIELD_RATION)) {
            trooper.setHoldingFood(true);
        } else if (bonus.getType().equals(BonusType.GRENADE)) {
            trooper.setHoldingGrenade(true);
        } else if (bonus.getType().equals(BonusType.MEDIKIT)) {
            trooper.setHoldingMedkit(true);
        }
    }

    @Override
    public ActionType getType() {
        return ActionType.MOVE;
    }

    @Override
    public int getSpentActionPoints(FutureWorld result) {
        return getPointsToMakeMove(result);
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

    private void recountCellWeights(FutureWorld result) {
        double[][] cells = new double[result.getSource().getWidth()][result.getSource().getHeight()];
        for (int i = 0; i < result.getSource().getWidth(); i++) {
            for (int j = 0; j < result.getSource().getHeight(); j++) {
                if (isVisible(result, i, j)) {
                    cells[i][j] = 0;
                } else {
                    cells[i][j] = result.getCellWeights()[i][j];
                }
            }
        }
        result.setCellWeights(cells);
    }

    private boolean isVisible(FutureWorld world, int x, int y) {
        if (!WorldStateHolder.getInstance().getWorldCells()[x][y].equals(CellType.FREE)) {
            return false;
        }
        for (FutureTrooper futureTrooper : world.getTeammates()) {
            if (isVisibleForTrooper(world, futureTrooper, x, y)) {
                return true;
            }
        }
        return false;
    }


    private boolean isVisibleForTrooper(FutureWorld world, FutureTrooper troop, int x, int y) {
        double range = troop.getSourceTrooper().getVisionRange();
        if (world.getSource().isVisible(range, troop.getX(), troop.getY(), troop.getStance(), x, y, troop.getStance())) {
            return true;
        } else {
            return false;
        }
    }

}
