package com.chores.instructions;

import com.chores.instructions.arguments.Argument;

public class NoOperationInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expectingNone(arguments);
    }
}
