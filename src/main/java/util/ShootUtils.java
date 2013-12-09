package util;

import model.Trooper;

public class ShootUtils {

    public static double getAverageShootingWeight(Trooper trooper) {
        double result = (trooper.getInitialActionPoints() / trooper.getShootCost()) * trooper.getDamage();
        return result;
    }

}
