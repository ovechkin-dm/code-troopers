package emul.state;

import emul.Constants;
import model.CellType;
import model.Trooper;
import model.TrooperType;
import model.World;
import util.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class WorldStateHolder {

    private static WorldStateHolder instance = new WorldStateHolder();
    private PotentialEnemiesHolder enemiesHolder = new PotentialEnemiesHolder();
    private LeaderChooserHolder leaderChooserHolder = new LeaderChooserHolder();
    private TeammatesHolder teammatesHolder = new TeammatesHolder();

    private Boolean splitMap;
    private CellType[] splitMapArray = {CellType.FREE, CellType.FREE, CellType.MEDIUM_COVER, CellType.LOW_COVER, CellType.LOW_COVER, CellType.FREE};
    private CellType[] labyMapArray = {CellType.FREE, CellType.FREE, CellType.HIGH_COVER, CellType.FREE, CellType.HIGH_COVER, CellType.HIGH_COVER, CellType.FREE};
    private Boolean labyMap;
    private World world;
    private Trooper trooper;
    private double[][] cellWeights;
    private CellType[][] worldCells;
    private int[][] field;
    private boolean merged = false;

    {
        cellWeights = new double[30][20];
        for (int i = 0; i < cellWeights.length; i++) {
            for (int j = 0; j < cellWeights[0].length; j++) {
                cellWeights[i][j] = Constants.CELL_WEIGHT_MULTIPLIER;
            }
        }
    }


    public static WorldStateHolder getInstance() {
        return instance;
    }

    public World getWorld() {
        return world;
    }

    public Trooper getTrooper() {
        return trooper;
    }

    public void setWorld(World world) {
        worldCells = world.getCells();
        this.world = world;
        mergeEnemies();
        recomputeShittyMap();
        recomputeLabyMap();
        leaderChooserHolder.recomputeLeader(world, splitMap, trooper);
        teammatesHolder.update(world);
    }

    private void recomputeLabyMap() {
        if (labyMap == null) {
            for (int i = 0; i < labyMapArray.length; i++) {
                if (!worldCells[5][i].equals(labyMapArray[i])) {
                    labyMap = false;
                    break;
                }
            }
            if (labyMap == null) {
                labyMap = true;
            }
        }
    }

    private void recomputeShittyMap() {
        if (splitMap == null) {
            for (int i = 0; i < 6; i++) {
                if (!worldCells[4][i].equals(splitMapArray[i])) {
                    splitMap = false;
                    break;
                }
            }
            if (splitMap == null) {
                splitMap = true;
            }
        }
    }

    private void mergeEnemies() {
        enemiesHolder.update(world);
    }

    public void setTrooper(Trooper trooper) {
        this.trooper = trooper;
    }

    public void recompute() {
        for (int i = 0; i < cellWeights.length; i++) {
            for (int j = 0; j < cellWeights[0].length; j++) {
                if (isVisible(i, j)) {
                    cellWeights[i][j] = 0;
                } else {
                    cellWeights[i][j] = cellWeights[i][j] + Constants.CELL_WEIGHT_MOVE_GAINER;
                }
            }
        }
    }

    public void recomputeTurn() {
        for (int i = 0; i < cellWeights.length; i++) {
            for (int j = 0; j < cellWeights[0].length; j++) {
                if (isVisible(i, j)) {
                    cellWeights[i][j] = 0;
                }
            }
        }
    }


    private void recomputeField() {
        field = new int[30][20];
        for (int i = 0; i < cellWeights.length; i++) {
            for (int j = 0; j < cellWeights[0].length; j++) {
                if (worldCells[i][j].equals(CellType.FREE)) {
                    field[i][j] = 0;
                } else {
                    field[i][j] = 1;
                }
            }
        }
    }


    private boolean isVisible(int x, int y) {
        if (!worldCells[x][y].equals(CellType.FREE)) {
            return false;
        }
        for (Trooper futureTrooper : WorldUtils.getTeammates(world)) {
            if (isVisibleForTrooper(futureTrooper, x, y)) {
                return true;
            }
        }
        return false;
    }


    private boolean isVisibleForTrooper(Trooper troop, int x, int y) {
        double range = troop.getVisionRange();
        if (world.isVisible(range, troop.getX(), troop.getY(), troop.getStance(), x, y, troop.getStance())) {
            return true;
        } else {
            return false;
        }
    }

    public double[][] getCellWeights() {
        return cellWeights;
    }

    public void setCellWeights(double[][] cellWeights) {
        this.cellWeights = cellWeights;
    }

    public CellType[][] getWorldCells() {
        return worldCells;
    }


    public int[][] getField() {
        if (field == null) {
            recomputeField();
        }
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public TrooperType getLeader() {
        return leaderChooserHolder.getLeader(world);
    }

    public List<Trooper> getPotentialEnemies() {
        return enemiesHolder.getEnemies();
    }

    public void expireEnemies() {
        enemiesHolder.expire(world);
    }

    public boolean isEnemyFake(Trooper trooper) {
        return enemiesHolder.isFake(trooper, world);
    }

    public Boolean getSplitMap() {
        return splitMap;
    }

    public Boolean getLabyMap() {
        return labyMap;
    }

    public boolean isMerged() {
        return leaderChooserHolder.isMerged();
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public List<Trooper> getProneEnemies() {
        return enemiesHolder.getProneEnemies();
    }

    public boolean hasShotFromNowhere(TrooperType trooperType) {
        return teammatesHolder.shotFromNowhere(trooperType);
    }

}
