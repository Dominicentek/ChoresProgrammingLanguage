package com.chores;

import com.chores.house.Chore;

public class ChoresException extends RuntimeException {
    private ChoresException(String message) {
        super(message);
    }
    public static ChoresException programError(String message) {
        return new ChoresException(message + " at line " + (Main.programIndex + 1));
    }
    public static ChoresException choresError(String message) {
        return new ChoresException(message + " (line " + (Main.programIndex + 1) + ")");
    }
}
