package com.chores.instructions;

import com.chores.Main;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

public class RememberInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class, AnyArgument.class);
        String varname = (String)arguments[0].value;
        Object value = Argument.getValue(arguments[1], null);
        Main.setVariable(new Variable(varname, value));
    }
}
