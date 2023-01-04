package com.chores.instructions;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.arguments.Argument;
import com.chores.instructions.arguments.WordArgument;
import com.chores.variable.VariableType;

public class HandInstruction extends Instruction {
    public void execute(Argument<?>[] arguments) {
        Argument.expecting(arguments, WordArgument.class);
        String itemName = (String)arguments[0].value;
        Item selectedItem = null;
        for (Item item : Item.values()) {
            if (item.name().replaceAll("_", "").toLowerCase().equals(itemName)) selectedItem = item;
        }
        if (selectedItem == null || !selectedItem.pickupable) throw ChoresException.programError("Item '" + itemName + "' doesn't exist");
        if (House.heldItems.contains(selectedItem)) {
            House.heldItems.remove(selectedItem);
            selectedItem.x = House.x;
            selectedItem.y = House.y;
            if (House.containsDust() && Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("You aren't done vacuuming yet! There's still dust left");
        }
        else {
            House.heldItems.add(selectedItem);
            selectedItem.x = -1000;
            selectedItem.y = -1000;
        }
        if (House.heldItems.contains(Item.VACUUM_CLEANER) && House.vacuumRunning) House.startVacuuming();
    }
}
