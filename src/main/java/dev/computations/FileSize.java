package dev.computations;

public enum FileSize {
    SMALL(10),
    BIG(100),
    LARGE(1000);

    private final int value;

    FileSize(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
