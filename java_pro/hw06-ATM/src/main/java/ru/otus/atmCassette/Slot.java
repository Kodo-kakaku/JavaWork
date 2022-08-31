package ru.otus.atmCassette;

import ru.otus.enums.Denomination;
import java.util.Objects;

public class Slot implements Comparable<Slot> {
    private Denomination denomination;
    private long quantity;

    public Slot(Denomination denomination, long quantity) {
        this.denomination = denomination;
        this.quantity = quantity;
    }

    public int getDenominationInt() {
        return denomination.numericalRepresentation;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    @Override
    public int compareTo(Slot slot) {
        return this.denomination.numericalRepresentation < slot.denomination.numericalRepresentation ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return quantity == slot.quantity &&
                denomination.numericalRepresentation == slot.denomination.numericalRepresentation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, quantity);
    }
}
