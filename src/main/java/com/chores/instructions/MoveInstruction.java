package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.Chore;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.house.Tile;
import com.chores.instructions.arguments.AnyArgument;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.VariableType;

import java.awt.Rectangle;

public class MoveInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class, AnyArgument.class);
        if (House.isInBed && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You cannot move in bed");
        String direction = (String)arguments[0].value;
        int amount = Argument.getValue(arguments[1], VariableType.NUMBER);
        int deltaX = 0;
        int deltaY = 0;
        if (direction.equals("up")) deltaY = -1;
        else if (direction.equals("left")) deltaX = -1;
        else if (direction.equals("down")) deltaY = 1;
        else if (direction.equals("right")) deltaX = 1;
        else throw ChoresException.programError("Unknown direction (" + direction + ")");
        for (int i = 0; i < amount; i++) {
            House.x += deltaX;
            House.y += deltaY;
            if (House.x < 0 || House.y < 0 || House.x >= 24 || House.y >= 24) throw ChoresException.choresError("Even with wall hacks, you cannot go outside the house boundaries");
            if (House.layout[House.y][House.x] == 0 && !Main.getProperty("wallhacks", VariableType.BOOLEAN)) throw ChoresException.choresError("You went into a wall! Be more careful!");
            if (House.layout[House.y][House.x] == Tile.OUTSIDE.ordinal()) House.doChore(Chore.OUTSIDE);
            if (!Main.getProperty("wallhacks", VariableType.BOOLEAN)) {
                for (Item item : Item.values()) {
                    if (!House.heldItems.contains(item) && new Rectangle(item.x, item.y, item.width, item.height).contains(House.x, House.y)) throw ChoresException.choresError("You tripped over " + item.name().toLowerCase().replaceAll("_", " ") + "! Be more careful!");
                }
            }
        }
    }
}
