import org.junit.jupiter.api.*;
import ru.otus.atm.Atm;
import ru.otus.atm.AtmException;
import ru.otus.atm.AtmImpl;
import ru.otus.atm.Response;
import ru.otus.atmCassette.Cassette;
import ru.otus.atmCassette.Slot;
import ru.otus.enums.Denomination;

import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmTest {
    private Cassette cassette;

    @BeforeEach
    void init() {
        this.cassette = new Cassette();
        cassette.putSlot(new Slot(Denomination.TEN, 5L));
        cassette.putSlot(new Slot(Denomination.HUNDRED, 1L));
    }

    @Test
    @DisplayName("Number of ATM, in which one hundred and five, balance = 150")
    void AtmInitTest() {
        Atm atm = new AtmImpl(cassette);
        Assertions.assertEquals(150, atm.getBalance());
    }

    @Test
    @DisplayName("One hundred and fifty were put on an empty account, balance was checked = 150")
    void acceptMoneyTest() {
        AtmImpl atm = new AtmImpl();
        atm.putMoney(Denomination.TEN, 5L);
        atm.putMoney(Denomination.HUNDRED, 1L);
        Assertions.assertEquals(150, atm.getBalance());
    }

    @Test
    @DisplayName("310 is withdrawn from account 150, an error is generated")
    void issueMoneyTest1() {
        Atm atm = new AtmImpl(cassette);
        assertThrows(AtmException.class, () -> atm.takeMoney(310));
    }

    @Test
    @DisplayName("Withdraw 30 from account 150, balance = 120")
    void issueMoneyTest2() throws AtmException {
        Atm atm = new AtmImpl(cassette);
        Response response = atm.takeMoney(30);

        Assertions.assertEquals(30, response.getResponseMoney());
        Assertions.assertEquals(120, atm.getBalance());
    }

    @Test
    @DisplayName("Withdraw 500 from account 500, balance = 0")
    void issueMoneyTest3() throws AtmException {
        cassette.putSlot(new Slot(Denomination.FIFTY, 3L));
        cassette.putSlot(new Slot(Denomination.TWO_HUNDRED, 1L));

        AtmImpl atm = new AtmImpl(cassette);
        Response response = atm.takeMoney(500);
        Assertions.assertEquals(500, response.getResponseMoney());
        Assertions.assertEquals(0, atm.getBalance());
    }
}
