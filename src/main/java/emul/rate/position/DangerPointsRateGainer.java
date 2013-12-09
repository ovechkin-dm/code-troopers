package emul.rate.position;

import emul.FutureWorld;
import util.MyPoint;

import java.util.Set;

public interface DangerPointsRateGainer {

    public double getRate(Set<MyPoint> points, FutureWorld world);

}
