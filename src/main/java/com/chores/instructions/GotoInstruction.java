package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class GotoInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, AnyArgument.class);
        if (!House.isInBed && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("Since you're not in bed and trying to sleep, you don't have the thought generating capacity to control logic of the program");
        Main.programIndex = Argument.getValue(arguments[0], VariableType.NUMBER) - 2;
    }
}
