package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.Chore;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

public class SubtractInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class, AnyArgument.class);
        if (!House.standingNear(Item.DESK) && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You have to do math while sitting at a desk to increase productivity");
        House.doChore(Chore.HOMEWORK);
        Variable variable = Main.getVariable((String)arguments[0].value);
        if (variable == null) throw ChoresException.programError("Variable '" + arguments[0].value + "' doesn't exist");
        int x = variable.get(VariableType.NUMBER);
        int y = Argument.getValue(arguments[1], VariableType.NUMBER);
        Main.setVariable(new Variable(variable.name, x - y));
    }
}
