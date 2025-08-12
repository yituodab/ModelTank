package com.model.tank.api.resource.data;

import com.google.gson.annotations.SerializedName;
import com.model.tank.resource.data.Module;

public class ModularEntityData {
    @SerializedName("modules")
    private Module[] modules = {};

    public Module[] getModules() {
        return modules;
    }
}
