package com.chores.instructions;

public class InstructionNotFoundException extends RuntimeException {
    public InstructionNotFoundException(String message) {
        super(message);
    }
}
