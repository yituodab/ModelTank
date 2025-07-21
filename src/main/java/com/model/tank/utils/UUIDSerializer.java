package com.model.tank.utils;

import com.google.gson.*;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDSerializer implements JsonDeserializer<UUID>, JsonSerializer<UUID> {
    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return UUID.fromString(GsonHelper.convertToString(json, "uuid"));
    }

    @Override
    public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
