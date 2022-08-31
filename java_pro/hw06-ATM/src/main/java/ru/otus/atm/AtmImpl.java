package ru.otus.atm;

import ru.otus.atmCassette.Cassette;
import ru.otus.enums.Denomination;
import ru.otus.atmCassette.Slot;

public class AtmImpl implements Atm {
    private final Cassette cassette;

    public AtmImpl() {
        this.cassette = new Cassette();
    }

    public AtmImpl(Cassette cassette) {
        this.cassette = cassette;
    }

    @Override
    public void putMoney(Denomination denomination, Long count) {
        cassette.putSlot(new Slot(denomination, count));
    }

    @Override
    public long getBalance() {
        return cassette.getAllSlots()
                .stream()
                .map(slot -> slot.getDenominationInt() * slot.getQuantity())
                .mapToLong(moneyInSlot -> moneyInSlot)
                .sum();
    }

    @Override
    public Response takeMoney(long customerRequest) throws AtmException {
        Response response = new Response();
        for(Slot slot : cassette.getAllSlots()) {
            if(customerRequest >= slot.getDenominationInt() && customerRequest != 0) {
                long numberOfBanknotes = Math.min(customerRequest / slot.getDenominationInt(), slot.getQuantity());
                customerRequest -= numberOfBanknotes * slot.getDenominationInt();
                response.addResponse(slot.getDenomination(), numberOfBanknotes);
                slot.setQuantity(slot.getQuantity() - numberOfBanknotes);
            }
        }
        if(customerRequest > 0) {
            throw new AtmException("Amount is not divided by banknotes available in the ATM");
        }
        return response;
    }
}