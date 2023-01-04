package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.variable.VariableType;

public class BedInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        if (House.standingNear(Item.BED) || !Main.getProperty("restrict", VariableType.BOOLEAN)) {
            if (House.containsDust() && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You aren't done vacuuming yet! There's still dust left");
            House.isInBed = !House.isInBed;
            House.bedInstructionsRemaining = Main.getProperty("bedlimit", VariableType.NUMBER);
        }
        else throw ChoresException.choresError("You are standing nowhere near a bed");
    }
}
