package util;

public class PathNode {

    private int x;
    private int y;
    private PathNode prev;

    public PathNode(int x, int y, PathNode prev) {
        this.x = x;
        this.y = y;
        this.prev = prev;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PathNode getPrev() {
        return prev;
    }

    public void setPrev(PathNode prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathNode pathNode = (PathNode) o;

        if (x != pathNode.x) return false;
        if (y != pathNode.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }



}