package com.hellcrafters.damage;

public enum ArmorValue {
    UNARMORED(0),
    LIGHT(1),
    MEDIUM(2),
    HEAVY(3),
    TANK(4),
    BUILDING(5);

    public final int value;

    ArmorValue(int value) { this.value = value; }

}
