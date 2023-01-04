package com.chores.instructions.arguments;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.variable.Variable;
import com.chores.variable.VariableType;

import java.util.ArrayList;

public abstract class Argument<T> {
    public T value;
    public Argument(T value) {
        this.value = value;
    }
    public static Argument<?>[] parseArgs(String arguments) {
        boolean stringLiteral = false;
        boolean shouldDoneParsing = false;
        boolean backslash = false;
        String string = "";
        ArrayList<Argument<?>> args = new ArrayList<>();
        for (int i = 0; i < arguments.length(); i++) {
            char character = arguments.charAt(i);
            if (stringLiteral) {
                if (backslash) {
                    if (character == 'n') string += "\n";
                    else if (character == 't') string += "\t";
                    else if (character == 'r') string += "\r";
                    else if (character == 'b') string += "\b";
                    else if (character == 'f') string += "\f";
                    else if (character == 's') string += " ";
                    else if (character == '\'') string += "'";
                    else if (character == '\"') string += "\"";
                    else throw ChoresException.programError("Invalid escape code");
                    backslash = false;
                    continue;
                }
                if (character == '\\') backslash = true;
                else if (character == '\"') {
                    args.add(new StringLiteralArgument(string));
                    string = "";
                    stringLiteral = false;
                    shouldDoneParsing = true;
                }
                else string += character;
                continue;
            }
            if (character != ' ' && shouldDoneParsing) throw ChoresException.programError("' ' expected");
            else if (character == ' ') {
                shouldDoneParsing = false;
                try {
                    args.add(new NumberLiteralArgument(Integer.parseInt(string)));
                }
                catch (NumberFormatException e) {
                    args.add(new WordArgument(string));
                }
                string = "";
            }
            else if (character == '\"') stringLiteral = true;
            else if (character > 32 && character <= 126) string += character;
            else throw ChoresException.programError("Illegal character (U+" + String.format("%1$4s", Integer.toHexString(character).toUpperCase()).replaceAll(" ", "0") + ")");
        }
        if (!string.isEmpty()) {
            try {
                args.add(new NumberLiteralArgument(Integer.parseInt(string)));
            }
            catch (NumberFormatException e) {
                args.add(new WordArgument(string));
            }
        }
        return args.toArray(new Argument<?>[0]);
    }
    @SafeVarargs
    public static void expecting(Argument<?>[] args, Class<? extends Argument<?>>... types) {
        if (args.length < types.length) throw ChoresException.programError("Not enough arguments");
        if (args.length > types.length) throw ChoresException.programError("Too much arguments");
        for (int i = 0; i < types.length; i++) {
            if (types[i] != AnyArgument.class && !types[i].isInstance(args[i])) throw ChoresException.programError("Expected " + types[i].getSimpleName() + ", found " + args[i].getClass().getSimpleName());
        }
    }
    public static void expectingNone(Argument<?>[] args) {
        if (args.length > 0) throw ChoresException.programError("Expected no arguments");
    }
    public static <T> T getValue(Argument<?> argument, VariableType<T> type) {
        Object value;
        if (argument instanceof NumberLiteralArgument) value = argument.value;
        else if (argument instanceof StringLiteralArgument) value = argument.value;
        else {
            Variable variable = Main.getVariable((String)argument.value);
            if (variable == null) throw ChoresException.programError("Variable " + argument.value + " doesn't exist");
            value = variable.value;
        }
        if (type == null) return (T)value;
        Variable variable = new Variable("", value);
        return variable.get(type);
    }
    public String toString() {
        return "ArgType=" + getClass().getSimpleName() + ";Val='" + value + "'";
    }
}
