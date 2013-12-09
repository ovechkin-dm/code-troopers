package emul.rate.together;

import emul.FutureWorld;

public interface AllTogetherGainer {

    double getScore(FutureWorld world, FutureWorld source);

    boolean withinCondition(FutureWorld world, FutureWorld source);

}
