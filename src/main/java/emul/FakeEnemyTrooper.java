package emul;

import model.Trooper;

public class FakeEnemyTrooper extends FutureTrooper {

    public FakeEnemyTrooper(Trooper sourceTrooper) {
        super(sourceTrooper);
    }

    public FakeEnemyTrooper(FutureTrooper toCopy) {
        super(toCopy);
    }

    @Override
    public int getHitPoints() {
        if (hitPoints <= 0) {
            return 1;
        } else {
            return hitPoints;
        }
    }

    @Override
    public void decreaseHitPoints(int amount) {
        this.hitPoints -= amount / Constants.FAKE_ENEMY_DECREASE_HITPOINTS_PENALTY;
    }
}
