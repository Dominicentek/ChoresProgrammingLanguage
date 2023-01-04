package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

public class ToNumberInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class);
        Variable variable = Main.getVariable((String)arguments[0].value);
        if (variable == null) throw ChoresException.programError("Variable '" + arguments[0].value + "' doesn't exist");
        String raw = variable.get(VariableType.STRING);
        int x = 0;
        try {
            x = Integer.parseInt(raw);
        }
        catch (Exception e) {
            ChoresException.choresError("The string doesn't seem to be a valid 32-bit integer");
        }
        Main.variables.remove(variable);
        Main.setVariable(new Variable(variable.name, x));
    }
}
