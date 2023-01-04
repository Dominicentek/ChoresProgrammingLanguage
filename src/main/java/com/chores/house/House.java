package com.chores.house;

import com.chores.ChoresException;
import com.chores.Main;
import com.chores.variable.VariableType;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class House {
    public static Point[] dustParticleLocations = {
        new Point(2, 2),
        new Point(4, 3),
        new Point(7, 3),
        new Point(3, 6),
        new Point(6, 6),
        new Point(13, 12),
        new Point(12, 6),
        new Point(19, 2),
        new Point(17, 4),
        new Point(21, 5),
        new Point(19, 7),
        new Point(18, 9),
        new Point(3, 10),
        new Point(13, 12),
        new Point(8, 13),
        new Point(3, 15),
        new Point(12, 16),
        new Point(18, 14)
    };
    public static int[][] layout = {
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, },
        { 0,3,3,3,3,3,3,3,3,3,0,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,1,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,1,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,1,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,0,1,1,1,1,1,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,0,1,1,1,1,1,4,4,4,4,4,4,4,0, },
        { 0,3,3,3,3,3,3,3,3,3,0,1,1,1,1,1,4,4,4,4,4,4,4,0, },
        { 0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,4,4,4,4,4,4,4,0, },
        { 0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,2,2,2,2,2,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,1,0, },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0, },
        { 0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0, },
        { 0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0, },
        { 0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0, },
        { 0,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,0, },
        { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, },
    };
    public static int x = 12;
    public static int y = 9;
    public static boolean isInBed = false;
    public static boolean vacuumRunning = false;
    public static Chore currentChore = null;
    public static int instructionsRemaining = 16;
    public static int bedInstructionsRemaining = 16;
    public static ArrayList<Item> heldItems = new ArrayList<>();
    public static boolean[][] remainingDust = new boolean[24][24];
    public static Random random = new Random(69420); // im so funny xd
    public static boolean standingNear(Item item) {
        return new Rectangle(item.x - 1, item.y - 1, item.width + 2, item.height + 2).contains(x, y);
    }
    public static void executedInstruction() {
        if (isInBed) {
            bedInstructionsRemaining--;
            if (bedInstructionsRemaining == 0) throw ChoresException.choresError("Stop being so lazy and actually do something!");
        }
        instructionsRemaining--;
        if (instructionsRemaining == 0) {
            if (currentChore == null) {
                currentChore = getRandomChore();
                if (currentChore == null) {
                    instructionsRemaining = Main.getProperty("instrlimit", VariableType.NUMBER);
                    return;
                }
                System.out.println(currentChore.command + "!");
                instructionsRemaining = Main.getProperty("chorelimit", VariableType.NUMBER);
            }
            else throw ChoresException.choresError("I SAID " + currentChore.command.toUpperCase() + "!!! You're grounded for not listening to me!");
        }
    }
    public static void doChore(Chore chore) {
        if (currentChore != chore && !chore.canDoWithoutAsking && !Main.getProperty("restrict", VariableType.BOOLEAN)) throw ChoresException.choresError("I didn't ask you to do that yet!");
        if (currentChore == chore) {
            currentChore = null;
            instructionsRemaining = Main.getProperty("instrlimit", VariableType.NUMBER);
        }
    }
    public static Chore getRandomChore() {
        ArrayList<Chore> availableChores = new ArrayList<>();
        for (Chore chore : Chore.values()) {
            if (Main.getProperty(chore.name().toLowerCase(), VariableType.BOOLEAN)) availableChores.add(chore);
        }
        if (availableChores.size() == 0) return null;
        return availableChores.get(random.nextInt(availableChores.size()));
    }
    public static void startVacuuming() {
        if (containsDust()) return;
        doChore(Chore.VACUUM);
        for (Point dustParticleLocation : dustParticleLocations) {
            remainingDust[dustParticleLocation.x][dustParticleLocation.y] = true;
        }
    }
    public static boolean containsDust() {
        for (int x = 0; x < 24; x++) {
            for (int y = 0; y < 24; y++) {
                if (remainingDust[x][y]) return true;
            }
        }
        return false;
    }
}
