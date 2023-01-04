package com.chores.house;

public enum Item {
    DESK(4, 1, 3, 1, false),
    BED(1, 7, 3, 1, false),
    SINK(22, 6, 1, 1, false),
    GARBAGE_CAN(21, 20, 2, 2, false),
    TRASH_CAN(22, 16, 1, 1, true),
    VACUUM_CLEANER(22, 12, 1, 1, true);
    public final boolean pickupable;
    public int x;
    public int y;
    public final int width;
    public final int height;
    Item(int x, int y, int width, int height, boolean pickupable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pickupable = pickupable;
    }
}
