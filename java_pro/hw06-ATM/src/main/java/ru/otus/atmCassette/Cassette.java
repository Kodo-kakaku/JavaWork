package ru.otus.atmCassette;

import java.util.SortedSet;
import java.util.TreeSet;


public class Cassette {
    private final SortedSet<Slot> storage;

    public Cassette() {
        this.storage = new TreeSet<>();
    }

    public void putSlot(Slot slot) {
        this.storage.add(slot);
    }

    public SortedSet<Slot> getAllSlots() {
        return this.storage;
    }
}
