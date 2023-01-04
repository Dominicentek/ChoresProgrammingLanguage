package com.chores.variable;

import com.chores.ChoresException;

public class Variable {
    public String name;
    public Object value;
    public VariableType<?> type;
    public Variable(String name, Object value) {
        if (value instanceof Integer) type = VariableType.NUMBER;
        else if (value instanceof String) type = VariableType.STRING;
        else if (value instanceof Boolean) type = VariableType.BOOLEAN;
        else throw new IllegalStateException("Unknown variable type: " + value.getClass().getSimpleName());
        this.name = name;
        this.value = value;
    }
    public <T> T get(VariableType<T> vartype) {
        if (type != vartype) throw ChoresException.programError("Invalid type; expected " + vartype + ", found " + type);
        return (T)value;
    }
    public int getSize() {
        if (type == VariableType.NUMBER) return 4;
        else if (type == VariableType.STRING) return ((String)value).length();
        return 0;
    }
}