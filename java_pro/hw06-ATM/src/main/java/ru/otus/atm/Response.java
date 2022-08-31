package ru.otus.atm;

import ru.otus.enums.Denomination;
import java.util.EnumMap;

public class Response {
    private final EnumMap<Denomination, Long> responseMap;

    public Response() {
        this.responseMap = new EnumMap<>(Denomination.class);
    }

    public long getResponseMoney() {
        return responseMap.entrySet()
                .stream()
                .map(x -> x.getKey().numericalRepresentation * x.getValue())
                .mapToLong(val -> val)
                .sum();
    }

    public void addResponse(Denomination denomination, Long count) {
        this.responseMap.put(denomination, count);
    }
}
