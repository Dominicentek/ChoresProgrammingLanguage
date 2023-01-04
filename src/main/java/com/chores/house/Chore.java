package com.chores.house;

public enum Chore {
    VACUUM("Vacuum the floor", false),
    GARBAGE("Take out the garbage", false),
    OUTSIDE("Go outside and touch some grass", true),
    DISHES("Do the dishes", false),
    HOMEWORK("Do your math homework", true);
    public final String command;
    public final boolean canDoWithoutAsking;
    Chore(String command, boolean canDoWithoutAsking) {
        this.command = command;
        this.canDoWithoutAsking = canDoWithoutAsking;
    }
}
