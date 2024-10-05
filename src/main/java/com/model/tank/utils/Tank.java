package com.model.tank.utils;

import java.util.ArrayList;
import java.util.List;

public class Tank{
    public final String name;
    public List<Model> models = new ArrayList<>();

    public Tank(String name) {
        this.name = name;
    }
}
