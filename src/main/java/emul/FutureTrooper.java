package emul;

import model.*;
import util.MyPoint;

public class FutureTrooper {

    private Trooper sourceTrooper;
    private int x;
    private int y;
    private int actionPoints;
    private TrooperType type;
    private TrooperStance stance;
    protected int hitPoints;
    private boolean holdingGrenade;
    private boolean holdingMedkit;
    private boolean holdingFood;

    public FutureTrooper(Trooper sourceTrooper) {
        this.sourceTrooper = sourceTrooper;
        initWithSource();
    }

    private void initWithSource() {
        this.x = sourceTrooper.getX();
        this.y = sourceTrooper.getY();
        this.stance = sourceTrooper.getStance();
        this.type = sourceTrooper.getType();
        this.actionPoints = sourceTrooper.getActionPoints();
        this.hitPoints = sourceTrooper.getHitpoints();
        this.holdingGrenade = sourceTrooper.isHoldingGrenade();
        this.holdingMedkit = sourceTrooper.isHoldingMedikit();
        this.holdingFood = sourceTrooper.isHoldingFieldRation();
    }

    public FutureTrooper(FutureTrooper toCopy) {
        this.x = toCopy.x;
        this.y = toCopy.y;
        this.stance = toCopy.stance;
        this.type = toCopy.type;
        this.actionPoints = toCopy.actionPoints;
        this.sourceTrooper = toCopy.getSourceTrooper();
        this.hitPoints = toCopy.hitPoints;
        this.holdingFood = toCopy.holdingFood;
        this.holdingGrenade = toCopy.holdingGrenade;
        this.holdingMedkit = toCopy.holdingMedkit;

    }

    public Trooper getSourceTrooper() {
        return sourceTrooper;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void decreaseActionPoints(int points) {
        this.actionPoints = this.actionPoints - points;
    }

    public TrooperType getType() {
        return type;
    }

    public void setType(TrooperType type) {
        this.type = type;
    }

    public TrooperStance getStance() {
        return stance;
    }

    public void setStance(TrooperStance stance) {
        this.stance = stance;
    }

    public void changeStance(ActionType actionType) {
        if (actionType.equals(ActionType.LOWER_STANCE)) {
            lowerStance();
        } else {
            upStance();
        }
    }

    private void upStance() {
        if (stance.equals(TrooperStance.PRONE)) {
            stance = TrooperStance.KNEELING;
        } else if (stance.equals(TrooperStance.KNEELING)) {
            stance = TrooperStance.STANDING;
        }
    }

    private void lowerStance() {
        if (stance.equals(TrooperStance.STANDING)) {
            stance = TrooperStance.KNEELING;
        } else if (stance.equals(TrooperStance.KNEELING)) {
            stance = TrooperStance.PRONE;
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void increaseHitPoints(int amount) {
        double result = this.hitPoints + amount;
        if (result > sourceTrooper.getMaximalHitpoints()) {
            this.hitPoints = sourceTrooper.getMaximalHitpoints();
        } else {
            this.hitPoints = this.hitPoints + amount;
        }
    }

    public void decreaseHitPoints(int amount) {
        int result = this.hitPoints - amount;
        if (result < 0) {
            this.hitPoints = 0;
        } else {
            this.hitPoints = result;
        }
    }

    public boolean isHoldingGrenade() {
        return holdingGrenade;
    }

    public void setHoldingGrenade(boolean holdingGrenade) {
        this.holdingGrenade = holdingGrenade;
    }

    public boolean isHoldingMedkit() {
        return holdingMedkit;
    }

    public void setHoldingMedkit(boolean holdingMedkit) {
        this.holdingMedkit = holdingMedkit;
    }

    public boolean isHoldingFood() {
        return holdingFood;
    }

    public void setHoldingFood(boolean holdingFood) {
        this.holdingFood = holdingFood;
    }

    public void increaseActionPoints(int amount) {
        this.actionPoints += amount;
    }

    public MyPoint asPoint() {
        return new MyPoint(x, y);
    }


}
