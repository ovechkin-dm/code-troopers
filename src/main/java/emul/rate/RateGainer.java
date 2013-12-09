package emul.rate;

import emul.FutureWorld;
import model.World;

public interface RateGainer {

    public double getRate(FutureWorld world, FutureWorld source);

}
