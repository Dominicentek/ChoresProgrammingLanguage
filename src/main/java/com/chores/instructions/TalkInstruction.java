package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.house.Tile;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class TalkInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, AnyArgument.class);
        Object value = Argument.getValue(arguments[0], null);
        if (House.layout[House.y][House.x] == Tile.TALKING_ROOM.ordinal() || !Main.getProperty("restrict", VariableType.BOOLEAN)) System.out.print(value);
        else throw ChoresException.choresError("You cannot speak outside the talking room, I can because I am a parent.");
    }
}
