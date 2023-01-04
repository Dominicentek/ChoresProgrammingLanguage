package com.chores.variable;

import java.lang.reflect.Field;

public class VariableType<T> {
    public static final VariableType<Integer> NUMBER = new VariableType<>(Integer.class);
    public static final VariableType<String> STRING = new VariableType<>(String.class);
    public static final VariableType<Boolean> BOOLEAN = new VariableType<>(Boolean.class);
    private final Class<T> type;
    private VariableType(Class<T> type) {
        this.type = type;
    }
    public String toString() {
        return type.getSimpleName();
    }
    public String name() {
        try {
            for (Field field : VariableType.class.getFields()) {
                if (field.get(null) == this) return field.getName();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
