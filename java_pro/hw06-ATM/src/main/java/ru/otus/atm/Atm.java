package ru.otus.atm;


import ru.otus.enums.Denomination;

public interface Atm {
    void putMoney(Denomination denomination, Long count);
    Response takeMoney(long customerRequest) throws AtmException;
    long getBalance();
}