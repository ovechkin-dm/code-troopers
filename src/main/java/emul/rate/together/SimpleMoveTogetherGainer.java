package emul.rate.together;

import emul.Constants;
import emul.FutureTrooper;
import emul.FutureWorld;
import emul.rate.MapOpenerGainer;
import emul.state.WorldStateHolder;
import util.MoveUtils;
import util.MyPoint;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleMoveTogetherGainer implements AllTogetherGainer {


    @Override
    public double getScore(FutureWorld world, FutureWorld source) {
        if (noEnemiesNearby(world)) {
            return getRate(world, source);
        } else {
            return 0.0;
        }
    }

    @Override
    public boolean withinCondition(FutureWorld world, FutureWorld source) {
        return noEnemiesNearby(world);
    }

    private boolean noEnemiesNearby(FutureWorld world) {
        return world.getEnemies().isEmpty();
    }

    private double getRate(FutureWorld world, FutureWorld source) {
        FutureTrooper leader = findLeader(world);
        if (onlyOneLeft(world)) {
            return 0;
        } else if (isCurrent(leader, world)) {
            return getLeaderDistancePenalty(leader, world);
        } else {
            return getFollowerPenalty(leader, world);
        }
    }

    private double getFollowerPenalty(FutureTrooper leader, FutureWorld world) {
        double distanceToLead = getDistanceToLead(leader, world);
        if (distanceToLead >= getTeamDistanceToLeadBound()) {
            double distanceCoeff = distanceToLead / 3;
            return (Constants.FOLLOWER_DISTANCE_PENALTY) * distanceCoeff;
        } else {
            return (1 / distanceToLead) * 20;
        }
    }

    private double getTeamDistanceToLeadBound() {
        if (WorldStateHolder.getInstance().getLabyMap()) {
            return Constants.TEAM_DISTANCE_TO_LEAD + 1;
        }
        return Constants.TEAM_DISTANCE_TO_LEAD;
    }

    private double getLeaderDistancePenalty(FutureTrooper leader, FutureWorld world) {
        double distance = getDistanceToMiddleTeammate(world);
        if (distance >= getLeadDistanceToTeam()) {
            double distanceCoeff = Math.log(distance);
            return (Constants.LEADER_DISTANCE_PENALTY) * distanceCoeff;
        } else {
            return 0;
        }
    }

    private double getLeadDistanceToTeam() {
        if (WorldStateHolder.getInstance().getWorld().getMoveIndex() <= 5) {
            return Constants.LEAD_DISTANCE_TO_TEAM + 1;
        } else if (WorldStateHolder.getInstance().getLabyMap()) {
            return Constants.LEAD_DISTANCE_TO_TEAM + 2;
        } else {
            return Constants.LEAD_DISTANCE_TO_TEAM;
        }
    }

    private boolean onlyOneLeft(FutureWorld world) {
        return world.getTeammates().size() <= 1;
    }

    private double getDistanceToLead(FutureTrooper leader, FutureWorld world) {
        FutureTrooper trooper = world.getCurrent();
        MyPoint current = new MyPoint(trooper.getX(), trooper.getY());
        double result = MoveUtils.getDistanceTo(current, new MyPoint(leader.getX(), leader.getY()));
        return result;
    }

    private double getDistanceToFarTeammate(FutureWorld world) {
        MyPoint trooperPoint = new MyPoint(world.getCurrent().getX(), world.getCurrent().getY());
        double minDistance = Integer.MIN_VALUE;
        for (FutureTrooper trooper : world.getRestOfTeam()) {
            MyPoint point = new MyPoint(trooper.getX(), trooper.getY());
            if (MoveUtils.getDistanceTo(trooperPoint, point) > minDistance) {
                minDistance = point.distanceTo(trooperPoint);
            }
        }
        return minDistance;
    }

    private double getDistanceToMiddleTeammate(FutureWorld world) {
        MyPoint trooperPoint = new MyPoint(world.getCurrent().getX(), world.getCurrent().getY());
        double minDistance = Integer.MIN_VALUE;
        List<FutureTrooper> rest = world.getRestOfTeam();
        Collections.sort(rest, getComparator(world.getCurrent()));
        if (world.getRestOfTeam().size() == 1) {
            MyPoint first = new MyPoint(rest.get(0).getX(), rest.get(0).getY());
            minDistance = MoveUtils.getDistanceTo(trooperPoint, first);
            return minDistance;
        } else {
            MyPoint first = new MyPoint(rest.get(0).getX(), rest.get(0).getY());
            minDistance = MoveUtils.getDistanceTo(trooperPoint, first);
        }
        return minDistance;
    }

    private Comparator<? super FutureTrooper> getComparator(final FutureTrooper current) {
        return new Comparator<FutureTrooper>() {
            @Override
            public int compare(FutureTrooper o1, FutureTrooper o2) {
                MyPoint trooperPoint = new MyPoint(current.getX(), current.getY());
                MyPoint first = new MyPoint(o1.getX(), o1.getY());
                MyPoint second = new MyPoint(o2.getX(), o2.getY());
                int distanceFirst = MoveUtils.getDistanceTo(trooperPoint, first);
                int distanceSecond = MoveUtils.getDistanceTo(trooperPoint, second);
                if (distanceFirst > distanceSecond) {
                    return 1;
                } else if (distanceFirst < distanceSecond) {
                    return -1;
                }
                return 0;
            }
        };
    }


    private boolean isCurrent(FutureTrooper trooper, FutureWorld world) {

        return trooper.getSourceTrooper().getId() == world.getCurrent().getSourceTrooper().getId();
    }

    private FutureTrooper findLeader(FutureWorld world) {
        for (FutureTrooper trooper : world.getTeammates()) {
            if (trooper.getType().equals(WorldStateHolder.getInstance().getLeader())) {
                return trooper;
            }
        }
        return null;
    }

}
