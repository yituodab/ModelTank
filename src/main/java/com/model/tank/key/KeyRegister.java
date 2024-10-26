package com.model.tank.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyRegister {
    public static final String MODEL_TANK_CATEGORY = "key.category.modeltank";
    public static final String KEY_MOVE_W = "key.modeltank.move.w";
    public static final String KEY_MOVE_S = "key.modeltank.move.s";
    public static final String KEY_MOVE_A = "key.modeltank.move.a";
    public static final String KEY_MOVE_D = "key.modeltank.move.d";
    public static final KeyMapping W_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,KEY_MOVE_W);
    public static final KeyMapping S_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,KEY_MOVE_S);
    public static final KeyMapping A_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,KEY_MOVE_A);
    public static final KeyMapping D_KEY = new KeyMapping(MODEL_TANK_CATEGORY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_W,KEY_MOVE_D);
}
