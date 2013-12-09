package util;

import model.CellType;
import model.Trooper;
import model.World;
import util.MyPoint;

import java.util.*;

public class AstarSolver {

    private int[][] field;
    private List<MyPoint> conditions = new ArrayList<MyPoint>();

    public AstarSolver(World world) {
        CellType[][] cells = world.getCells();
        field = new int[cells.length][cells[0].length];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                if (cells[x][y] != CellType.FREE) {
                    field[x][y] = 1;
                }
            }
        }
        for (Trooper trooper : WorldUtils.getTeammates(world)) {
            field[trooper.getX()][trooper.getY()] = 1;
        }
    }

    public AstarSolver(int[][] field) {
        this.field = field;
    }

    public List<MyPoint> solve(MyPoint from, MyPoint to) {
        List<MyPoint> result = new ArrayList<MyPoint>();
        if (from.equals(to)) {
            result.add(from);
        } else {
            PathNode node = solveNonEmpty(from, to);
            while (node != null) {
                result.add(new MyPoint(node.getX(), node.getY()));
                node = node.getPrev();
            }
            Collections.reverse(result);
        }
        return result;
    }

    public boolean isReachableAreaMoreThan(MyPoint point, int amount) {
        int result = 0;
        Stack<PathNode> open = new Stack<PathNode>();
        open.add(new PathNode((int) point.getX(), (int) point.getY(), null));
        HashSet<PathNode> closed = new HashSet<PathNode>();
        while (!open.isEmpty() && result <= amount) {
            PathNode current = open.pop();
            result++;
            if (closed.contains(open)) {
                continue;
            }
            closed.add(current);
            for (PathNode neighbour : getNeighbours(current)) {
                if (!closed.contains(neighbour)) {
                    neighbour.setPrev(current);
                    open.add(neighbour);
                }
            }
        }
        return result > amount;
    }

    public List<MyPoint> getClosestPoints(MyPoint point, int amount) {
        int result = 0;
        List<MyPoint> points = new ArrayList<MyPoint>();
        Queue<PathNode> open = new LinkedList<PathNode>();
        open.add(new PathNode((int) point.getX(), (int) point.getY(), null));
        HashSet<PathNode> closed = new HashSet<PathNode>();
        while (!open.isEmpty() && points.size() < amount) {
            PathNode current = open.remove();
            points.add(new MyPoint(current.getX(), current.getY()));
            result++;
            if (closed.contains(open)) {
                continue;
            }
            closed.add(current);
            for (PathNode neighbour : getNeighbours(current)) {
                if (!closed.contains(neighbour)) {
                    neighbour.setPrev(current);
                    open.add(neighbour);
                }
            }
        }
        return points;
    }

    private PathNode solveNonEmpty(MyPoint from, MyPoint to) {
        PriorityQueue<PathNode> open = new PriorityQueue<PathNode>(10, getComparator(from, to));
        HashSet<PathNode> closed = new HashSet<PathNode>();
        open.add(new PathNode((int) from.getX(), (int) from.getY(), null));
        while (!open.isEmpty()) {
            PathNode current = open.poll();
            if (closed.contains(current)) {
                continue;
            }
            closed.add(current);
            if (current.getX() == to.getX() && current.getY() == to.getY()) {
                return current;
            }
            for (PathNode neighbour : getNeighbours(current)) {
                if (!closed.contains(neighbour)) {
                    neighbour.setPrev(current);
                    open.add(neighbour);
                }
            }
        }
        return null;
    }

    private Comparator<PathNode> getComparator(final MyPoint from, final MyPoint to) {
        return new Comparator<PathNode>() {
            @Override
            public int compare(PathNode first, PathNode second) {
                double toFirst = to.distanceTo(first.getX(), first.getY());
                double toSecond = to.distanceTo(second.getX(), second.getY());
                if (toFirst > toSecond) return 1;
                if (toSecond == toFirst) return 0;
                return -1;
            }
        };
    }

    private List<PathNode> getNeighbours(PathNode current) {
        List<PathNode> result = new ArrayList<PathNode>();
        if (withinBounds(current.getX() + 1, current.getY()))
            result.add(new PathNode(current.getX() + 1, current.getY(), null));
        if (withinBounds(current.getX() - 1, current.getY()))
            result.add(new PathNode(current.getX() - 1, current.getY(), null));
        if (withinBounds(current.getX(), current.getY() - 1))
            result.add(new PathNode(current.getX(), current.getY() - 1, null));
        if (withinBounds(current.getX(), current.getY() + 1))
            result.add(new PathNode(current.getX(), current.getY() + 1, null));

        return result;
    }

    private boolean withinBounds(int x, int y) {
        if (conditionsContains(x, y)) return false;
        if (x < 0 || x >= field.length) return false;
        if (y < 0 || y >= field[0].length) return false;
        if (field[x][y] == 1) return false;
        return true;
    }

    private boolean conditionsContains(int x, int y) {
        for (MyPoint point : conditions) {
            if (point.getX() == x && point.getY() == y) {
                return true;
            }
        }
        return false;
    }


    public void addCondition(MyPoint point) {
        conditions.add(point);
    }

    public int[][] getField() {
        return field;
    }
}
