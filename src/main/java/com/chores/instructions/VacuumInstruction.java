package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class VacuumInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expectingNone(arguments);
        if (House.standingNear(Item.VACUUM_CLEANER) || House.heldItems.contains(Item.VACUUM_CLEANER) || !Main.getProperty("restrict", VariableType.BOOLEAN)) {
            House.vacuumRunning = !House.vacuumRunning;
            if (!House.vacuumRunning && House.containsDust() && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You aren't done vacuuming yet! There's still dust left");
        }
        else throw ChoresException.choresError("You can't just... turn on the vacuum cleaner from like 500 km away... (metric system is better fuck the imperial one)");
        if ((House.standingNear(Item.VACUUM_CLEANER) && House.heldItems.contains(Item.VACUUM_CLEANER)) || !Main.getProperty("restrict", VariableType.BOOLEAN)) House.startVacuuming();
    }
}
