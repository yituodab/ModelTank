package com.model.tank.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExplodeHelper {
    public static float[][] RadiusOfMass = {
            new float[]{0.005f, 0,025f},
            new float[]{0.01f, 0,15f},
            new float[]{0.05f, 0.5f},
            new float[]{0.5f, 0.6f},
            new float[]{1, 0.7f},
            new float[]{4, 1},
            new float[]{25, 5},
            new float[]{100,10},
            new float[]{1500,60},
            new float[]{5000,120}};
    public static void createExplode(Entity owner, float mass, Vec3 pos){
        if(!(owner.level() instanceof ServerLevel level))return;
        level.explode(owner, pos.x,pos.y,pos.z, getRadiusOfMass(mass),true, Level.ExplosionInteraction.TNT);
    }
    public static float getRadiusOfMass(float mass){
        if(RadiusOfMass[0][0] > mass)return RadiusOfMass[0][1]/RadiusOfMass[0][0]*mass;
        for (int i = 1;i<10;i++) {
            if(RadiusOfMass[i][0] == mass)return RadiusOfMass[i][1];
            if(i == 9)return RadiusOfMass[i][1]/RadiusOfMass[i][0]*mass;
            if(RadiusOfMass[i-1][0] < mass && RadiusOfMass[i+1][0] > mass)return RadiusOfMass[i+1][1]/RadiusOfMass[i+1][0]*mass;
        }
        return 1;
    }
}
