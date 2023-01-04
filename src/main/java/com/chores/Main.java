package com.chores;

import com.chores.house.Chore;
import com.chores.house.House;
import com.chores.house.Item;
import com.chores.instructions.Instruction;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static ArrayList<Variable> variables = new ArrayList<>();
    public static int programIndex = 0;
    public static HashMap<String, Variable> properties = new HashMap<>();
    public static void main(String[] args) throws IOException {
        properties.put("msg", new Variable("", true));
        properties.put("randseed", new Variable("", 69420));
        properties.put("vacuum", new Variable("", true));
        properties.put("homework", new Variable("", true));
        properties.put("dishes", new Variable("", true));
        properties.put("outside", new Variable("", true));
        properties.put("garbage", new Variable("", true));
        properties.put("instrlimit", new Variable("", 16));
        properties.put("bedlimit", new Variable("", 16));
        properties.put("chorelimit", new Variable("", 8));
        properties.put("memsize", new Variable("", 256));
        properties.put("restrict", new Variable("", true));
        properties.put("wallhacks", new Variable("", false));
        if (args.length < 1) {
            System.out.println("Please specify filename");
            System.exit(1);
        }
        StringBuilder filenameBuilder = new StringBuilder();
        for (String arg : args) {
            filenameBuilder.append(" ").append(arg);
        }
        String filename = filenameBuilder.substring(1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        String program = baos.toString().replaceAll("\r", "");
        String[] lines = program.split("\n");
        Instruction.registerInstructions();
        while (programIndex >= 0 && programIndex < lines.length) {
            if (lines[programIndex].startsWith("@")) {
                if (!lines[programIndex].contains(" ")) throw ChoresException.programError("Property doesn't contain a value");
                String propertyName = lines[programIndex].substring(1, lines[programIndex].indexOf(" "));
                String propertyValue = lines[programIndex].substring(lines[programIndex].indexOf(" ") + 1);
                Variable property = properties.get(propertyName);
                if (property == null) throw ChoresException.programError("Invalid property '" + propertyName + "'");
                Object value;
                if (propertyValue.equals("true")) value = true;
                else if (propertyValue.equals("false")) value = false;
                else {
                    try {
                        value = Integer.parseInt(propertyValue);
                    }
                    catch (Exception e) {
                        throw ChoresException.programError("Invalid property value");
                    }
                }
                setProperty(new Variable(propertyName, value));
                if (propertyName.equals("randseed")) House.random.setSeed(Integer.toUnsignedLong((int)value));
                if (propertyName.equals(House.currentChore == null ? "instrlimit" : "chorelimit")) House.instructionsRemaining = (int)value;
                if (propertyName.equals("bedlimit")) House.bedInstructionsRemaining = (int)value;
                programIndex++;
                continue;
            }
            boolean successfullyExecuted = Instruction.execute(lines[programIndex]);
            if (calculateVariableSize() > getProperty("memsize", VariableType.NUMBER) && getProperty("memsize", VariableType.NUMBER) != 0) throw ChoresException.choresError("You tried remembering too much data");
            if ((!House.heldItems.contains(Item.VACUUM_CLEANER) || !House.vacuumRunning) && successfullyExecuted) House.executedInstruction();
            if (House.containsDust()) House.remainingDust[House.x][House.y] = false;
            programIndex++;
        }
    }
    public static Variable getVariable(String name) {
        for (Variable variable : variables) {
            if (variable.name.equals(name)) return variable;
        }
        return null;
    }
    public static void setVariable(Variable variable) {
        for (Variable var : variables) {
            if (var.name.equals(variable.name)) {
                if (var.type != variable.type) throw ChoresException.programError("Variable '" + var.name + "' of a " + var.type.name().toLowerCase() + " type is being assigned to a value of a " + variable.type.name().toLowerCase() + " type");
                else var.value = variable.value;
                return;
            }
        }
        variables.add(variable);
    }
    public static void setProperty(Variable variable) {
        Variable property = properties.get(variable.name);
        if (property == null) throw ChoresException.programError("Invalid property");
        if (property.type != variable.type) throw ChoresException.programError("Property '" + variable.name + "' of a " + property.type.name().toLowerCase() + " type is being assigned to a value of a " + variable.type.name().toLowerCase() + " type");
        else property.value = variable.value;
    }
    public static <T> T getProperty(String name, VariableType<T> type) {
        Variable property = properties.get(name);
        if (property == null) throw ChoresException.programError("Invalid property");
        return property.get(type);
    }
    public static int calculateVariableSize() {
        int size = 0;
        for (Variable variable : variables) {
            size += variable.getSize();
        }
        return size;
    }
}
