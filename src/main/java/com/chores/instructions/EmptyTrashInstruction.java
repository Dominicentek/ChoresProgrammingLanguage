package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.Chore;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class EmptyTrashInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expectingNone(arguments);
        if (!House.heldItems.contains(Item.TRASH_CAN) && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("What do you even want to empty");
        if (!House.standingNear(Item.GARBAGE_CAN) && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You can't just... dump the trash on ground");
        House.doChore(Chore.GARBAGE);
    }
}
