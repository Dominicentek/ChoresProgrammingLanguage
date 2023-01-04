package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.Chore;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class DishesInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expectingNone(arguments);
        if (!House.heldItems.isEmpty() && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("Try doing the dishes when you hold an item gl lmao");
        if (House.standingNear(Item.SINK) || !Main.getProperty("restrict", VariableType.BOOLEAN)) House.doChore(Chore.DISHES);
        else throw ChoresException.choresError("You're standing nowhere near the sink. Do you want to clean the air or what?");
    }
}
