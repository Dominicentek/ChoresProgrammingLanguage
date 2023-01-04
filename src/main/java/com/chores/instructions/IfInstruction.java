package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

public class IfInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class, AnyArgument.class);
        if (!House.isInBed && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("Since you're not in bed and trying to sleep, you don't have the thought generating capacity to control logic of the program");
        Variable variable = Main.getVariable((String)arguments[0].value);
        if (variable == null) throw ChoresException.programError("Variable '" + arguments[0].value + "' doesn't exist");
        Object value = Argument.getValue(arguments[1], null);
        if (!variable.value.equals(value)) Main.programIndex++;
    }
}
