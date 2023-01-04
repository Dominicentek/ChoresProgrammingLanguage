package com.chores.instructions;

import com.chores.instructions.arguments.Argument;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Instruction {
    public static HashMap<String, Instruction> instructions = new HashMap<>();
    public static boolean execute(String line) {
        if (line.contains("#")) line = line.substring(0, line.indexOf('#'));
        if (line.isEmpty()) return false;
        String[] args = line.split(" ");
        Instruction instruction = instructions.get(args[0]);
        if (instruction == null) throw new InstructionNotFoundException("Instruction " + args[0] + " doesn't exist");
        String rawArgs = "";
        for (int i = 1; i < args.length; i++) {
            if (i > 1) rawArgs += " ";
            rawArgs += args[i];
        }
        instruction.execute(Argument.parseArgs(rawArgs));
        return true;
    }
    public abstract void execute(Argument<?>[] arguments);
    public static void registerInstructions() {
        instructions.put("move", new MoveInstruction());
        instructions.put("dishes", new DishesInstruction());
        instructions.put("hand", new HandInstruction());
        instructions.put("vacuum", new VacuumInstruction());
        instructions.put("emptytrash", new EmptyTrashInstruction());
        instructions.put("talk", new TalkInstruction());
        instructions.put("read", new ReadInstruction());
        instructions.put("bed", new BedInstruction());
        instructions.put("remember", new RememberInstruction());
        instructions.put("forget", new ForgetInstruction());
        instructions.put("add", new AddInstruction());
        instructions.put("subtract", new SubtractInstruction());
        instructions.put("multiply", new MultiplyInstruction());
        instructions.put("divide", new DivideInstruction());
        instructions.put("tonum", new ToNumberInstruction());
        instructions.put("tochar", new ToCharacterInstruction());
        instructions.put("if", new IfInstruction());
        instructions.put("goto", new GotoInstruction());
        instructions.put("noop", new NoOperationInstruction());
    }
}
