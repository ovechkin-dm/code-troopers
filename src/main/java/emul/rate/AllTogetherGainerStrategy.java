package emul.rate;

import emul.FutureWorld;
import emul.rate.together.*;

import java.util.ArrayList;
import java.util.List;

public class AllTogetherGainerStrategy implements RateGainer {

    private static final List<AllTogetherGainer> gainers = new ArrayList<AllTogetherGainer>();
    static {
        gainers.add(new SimpleMoveTogetherGainer());
    }

    @Override
    public double getRate(FutureWorld world, FutureWorld source) {
        return gainers.get(0).getScore(world, source);
    }
}
