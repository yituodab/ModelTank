package com.model.tank.utils;

import net.minecraft.util.Mth;

public class MathUtils {
    public static double magnificationToFov(double magnification, double currentFov) {
        double currentTan = Math.tan(Math.toRadians(currentFov / 2));
        double newTan = currentTan / magnification;
        return Math.toDegrees(Math.atan(newTan)) * 2;
    }
    public static double getEquivalentArmor(int thickness, float cos){
        return thickness / cos;
    }
    public static double getEquivalentArmor(int thickness, double angle){
        return getEquivalentArmor(thickness,Mth.cos((float) angle * 0.017453292F));
    }
}
