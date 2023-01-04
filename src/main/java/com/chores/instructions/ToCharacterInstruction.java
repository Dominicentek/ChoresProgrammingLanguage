package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

public class ToCharacterInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class);
        Variable variable = Main.getVariable((String)arguments[0].value);
        if (variable == null) throw ChoresException.programError("Variable '" + arguments[0].value + "' doesn't exist");
        int raw = variable.get(VariableType.NUMBER);
        Main.variables.remove(variable);
        Main.setVariable(new Variable(variable.name, "" + (char)raw));
    }
}
