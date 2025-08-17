package com.model.tank.utils;

import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.Function;

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
    public static <T> List<T> sort(List<T> list, Function<T, Boolean> function){
        for (int i = 1; i < list.size(); i++) {
            boolean swap = true;
            for (int j = 0; j < list.size() - i; j++) {
                if (function.apply(list.get(j))) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swap = false;
                }
            }
            if (swap) {
                break;
            }
        }
        return list;
    }
}
