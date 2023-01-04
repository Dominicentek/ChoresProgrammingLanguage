package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

import java.util.Scanner;

public class ReadInstruction extends Instruction {
    public static final Scanner scanner = new Scanner(System.in);
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class);
        if (!House.standingNear(Item.DESK) && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You cannot read a book, it's on your desk");
        String string = scanner.nextLine();
        Variable variable = new Variable((String)arguments[0].value, string);
        Main.setVariable(variable);
    }
}
