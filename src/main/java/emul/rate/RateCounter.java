package emul.rate;

import emul.FutureWorld;
import emul.rate.position.AtGoodPositionRateGainer;
import emul.rate.together.MergeSplittedGroupsGainer;
import emul.rate.together.SupportFarFromLeaderGainer;

import java.util.ArrayList;
import java.util.List;

public class RateCounter {

    private List<RateGainer> rateGainers = new ArrayList<RateGainer>();

    {
        rateGainers.add(new MapOpenerGainer());
        rateGainers.add(new AllTogetherGainerStrategy());
        rateGainers.add(new ChangeStanceRateGainer());
        rateGainers.add(new MedicHealRateGainer());
        rateGainers.add(new ShootingRateGainer());
        rateGainers.add(new EatBonusGainer());
        rateGainers.add(new LeaderBlockerGainer());
        rateGainers.add(new EnemyIsDeadGainer());
        rateGainers.add(new FieldRationBonusWasteGainer());
        rateGainers.add(new HitAtOnePersonRateGainer());
        rateGainers.add(new SniperCanShootGainer());
        rateGainers.add(new InjuredFarFromEnemyGainer());
        rateGainers.add(new EnemyCanThrowGrenadeGainer());
        rateGainers.add(new AvoidDangerAreaGainer());
        rateGainers.add(new HideFromEnemiesGainer());
        rateGainers.add(new HideFromPlayerGainer());
        rateGainers.add(new FarFromEnemyGainer());
        rateGainers.add(new LandedOnSameSpotGainer());
        //rateGainers.add(new ChangeStanceSplitMapGainer());
        rateGainers.add(new SniperKneeStanceWeightGainer());
        rateGainers.add(new CloseToCenterMoveGainer());
        rateGainers.add(new AtGoodPositionRateGainer());
        rateGainers.add(new SupportFarFromLeaderGainer());
        rateGainers.add(new MergeSplittedGroupsGainer());
        rateGainers.add(new HighlightedEnemiesRateGainer());
        rateGainers.add(new VisibleToProneRateGainer());
        rateGainers.add(new EnemyCanShootRateGainer());
    }

    public double getRateForMove(FutureWorld current, FutureWorld source) {
        double result = 0.0;
        for (RateGainer rateGainer : rateGainers) {
            result += rateGainer.getRate(current, source);
        }
        return result;
    }

}
