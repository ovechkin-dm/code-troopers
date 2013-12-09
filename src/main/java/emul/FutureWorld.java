package emul;

import model.*;
import util.WorldUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FutureWorld {

    private final World source;
    private final Trooper trooper;
    private final Game game;
    private List<FutureTrooper> teammates = new ArrayList<FutureTrooper>();
    private List<FutureTrooper> enemies = new ArrayList<FutureTrooper>();
    private FutureTrooper current;
    private double[][] cellWeights;
    private List<Bonus> bonuses = new ArrayList<Bonus>();
    private List<Move> trace = new ArrayList<Move>();

    public FutureWorld(World source, Trooper trooper, Game game) {
        this.source = source;
        this.trooper = trooper;
        this.game = game;
        initCurrent();
        initEnemies();
        initBonuses();
    }

    private void initBonuses() {
        Collections.addAll(bonuses, source.getBonuses());
    }

    private void initEnemies() {
        for (Trooper enemy : WorldUtils.getEnemies(source)) {
            enemies.add(new FutureTrooper(enemy));
        }
    }

    private void initCurrent() {
        current = new FutureTrooper(trooper);
        for (Trooper trooper : source.getTroopers()) {
            if (trooper.isTeammate() && !(trooper.getId() == current.getSourceTrooper().getId())) {
                teammates.add(new FutureTrooper(trooper));
            }
        }
        teammates.add(current);
    }

    public World getSource() {
        return source;
    }

    private Trooper getTrooper() {
        return trooper;
    }

    public Game getGame() {
        return game;
    }

    public FutureTrooper getCurrent() {
        return current;
    }

    public FutureWorld copy() {
        FutureWorld result = new FutureWorld(source, trooper, game);
        result.current = new FutureTrooper(current);
        result.teammates = copyTeammates();
        result.teammates.add(result.current);
        result.cellWeights = this.cellWeights;
        result.enemies = copyEnemies();
        result.bonuses = copyBonuses();
        result.trace = copyTrace();
        return result;
    }

    private List<Move> copyTrace() {
        List<Move> result = new ArrayList<Move>();
        for (Move move : trace) {
            result.add(move);
        }
        return result;
    }

    private List<Bonus> copyBonuses() {
        List<Bonus> result = new ArrayList<Bonus>();
        result.addAll(this.bonuses);
        return bonuses;
    }

    private List<FutureTrooper> copyEnemies() {
        List<FutureTrooper> result = new ArrayList<FutureTrooper>();
        for (FutureTrooper enemy : this.enemies) {
            result.add(new FutureTrooper(enemy));
        }
        return result;
    }

    private double[][] copyCellWeights() {
        double[][] result = new double[getSource().getWidth()][getSource().getHeight()];
        for (int i = 0; i < getSource().getWidth(); i++) {
            for (int j = 0; j < getSource().getHeight(); j++) {
                result[i][j] = cellWeights[i][j];
            }
        }
        return result;
    }

    private List<FutureTrooper> copyTeammates() {
        List<FutureTrooper> result = new ArrayList<FutureTrooper>();
        for (FutureTrooper teammate : teammates) {
            if (teammate.getSourceTrooper().getId() != current.getSourceTrooper().getId()) {
                result.add(new FutureTrooper(teammate));
            }
        }
        return result;
    }

    public List<FutureTrooper> getTeammates() {
        return teammates;
    }

    public double[][] getCellWeights() {
        return cellWeights;
    }

    public void setCellWeights(double[][] cellWeights) {
        this.cellWeights = cellWeights;
    }

    public List<FutureTrooper> getEnemies() {
        return enemies;
    }

    public List<FutureTrooper> getRestOfTeam() {
        List<FutureTrooper> result = new ArrayList<FutureTrooper>();
        result.addAll(teammates);
        result.remove(current);
        return result;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void setEnemies(List<FutureTrooper> enemies) {
        this.enemies = enemies;
    }

    public void setBonuses(List<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    public List<Move> getTrace() {
        return trace;
    }

    public void setTrace(List<Move> trace) {
        this.trace = trace;
    }
}
