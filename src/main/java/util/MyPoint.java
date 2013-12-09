package util;

public class MyPoint {

    private double x;
    private double y;

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(MyPoint other) {
        double xDist = Math.abs(x - other.x) * Math.abs(x - other.x);
        double yDist = Math.abs(y - other.y) * Math.abs(y - other.y);
        return Math.sqrt(xDist + yDist);
    }

    public double distanceTo(double otherX, double otherY) {
        double xDist = Math.abs(x - otherX) * Math.abs(x - otherX);
        double yDist = Math.abs(y - otherY) * Math.abs(y - otherY);
        return Math.sqrt(xDist + yDist);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyPoint point = (MyPoint) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
