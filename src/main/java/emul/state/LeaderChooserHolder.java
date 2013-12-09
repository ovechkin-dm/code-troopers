package emul.state;

import model.Trooper;
import model.TrooperType;
import model.World;
import util.MoveUtils;
import util.MyPoint;
import util.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class LeaderChooserHolder {

    private static List<TrooperType> leaderPriorities = new ArrayList<TrooperType>();
    private static List<TrooperType> firstGroupPriorities = new ArrayList<TrooperType>();
    private static List<TrooperType> secondGroupPriorities = new ArrayList<TrooperType>();
    private boolean merged = false;

    private TrooperType leader;

    public TrooperType getLeader(World world) {
        return leader;
    }

    static {
        leaderPriorities.add(TrooperType.SCOUT);
        leaderPriorities.add(TrooperType.COMMANDER);
        leaderPriorities.add(TrooperType.SOLDIER);
        leaderPriorities.add(TrooperType.SNIPER);
        leaderPriorities.add(TrooperType.FIELD_MEDIC);

        firstGroupPriorities.add(TrooperType.SNIPER);
        firstGroupPriorities.add(TrooperType.FIELD_MEDIC);

        secondGroupPriorities.add(TrooperType.SCOUT);
        secondGroupPriorities.add(TrooperType.COMMANDER);
        secondGroupPriorities.add(TrooperType.SOLDIER);

    }

    public void recomputeLeader(World world, Boolean splitMap, Trooper current) {
        computeNormalLeader(world);
    }

    private void recomputeMerged(Boolean splitMap, Trooper current, World world) {
        if (splitMap) {
            double distance = Integer.MIN_VALUE;
            MyPoint currentPoint = new MyPoint(current.getX(), current.getY());
            for (Trooper trooper : WorldUtils.getRestOfTeam(world, current)) {
                MyPoint trooperPoint = new MyPoint(trooper.getX(), trooper.getY());
                double result = MoveUtils.getDistanceTo(currentPoint, trooperPoint);
                if (distance < result) {
                    distance = result;
                }
            }
            if (distance <= 10) {
                merged = true;
            }
        }
    }

    private boolean groupsMerged(World world) {
        return merged;
    }

    private void computeSplitMapLeader(World world, Trooper current) {
        List<TrooperType> currentPriorities;
        if (firstGroupPriorities.contains(current.getType())) {
            currentPriorities = firstGroupPriorities;
        } else {
            currentPriorities = secondGroupPriorities;
        }
        for (TrooperType trooperType : currentPriorities) {
            for (Trooper troop : WorldUtils.getTeammates(world)) {
                if (troop.getType().equals(trooperType)) {
                    leader = trooperType;
                    return;
                }
            }
        }
    }

    private Trooper getOverallLeader(World world) {
        for (TrooperType trooperType : leaderPriorities) {
            for (Trooper troop : WorldUtils.getTeammates(world)) {
                if (troop.getType().equals(trooperType)) {
                    return troop;
                }
            }
        }
        return null;
    }

    private void computeNormalLeader(World world) {
        for (TrooperType trooperType : leaderPriorities) {
            for (Trooper troop : WorldUtils.getTeammates(world)) {
                if (troop.getType().equals(trooperType)) {
                    leader = trooperType;
                    return;
                }
            }
        }
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }
}
