package com.smartparking;

// SlotType is an enum
// enum is a special class in Java that has fixed constant values
// I used enum here because slot type can only be SMALL, MEDIUM or LARGE
// using plain strings would be risky because typos won't give compile error
// but with enum if I type wrong name it will fail at compile time itself

public enum SlotType {
    SMALL,   // for motorcycles
    MEDIUM,  // for cars
    LARGE    // for trucks
}
