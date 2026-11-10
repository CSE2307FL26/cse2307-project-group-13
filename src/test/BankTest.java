package test;

import main.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void testOpenAccountGivesNewNumbers() {
        Bank testBank = new Bank();
        assertEquals(1, testBank.openAccount());
        assertEquals(2, testBank.openAccount());
    }

    @Test
    public void testOpenAccountStartsEmpty() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();
        assertEquals(0, testBank.getAccount(accountNumber).getBalance(), 0.01);
    }

    @Test
    public void testOpenAccountAddsToAccountList() {
        Bank testBank = new Bank();
        testBank.openAccount();
        testBank.openAccount();
        assertEquals(2, testBank.getAccountNumbers().size());
    }

    @Test
    public void testHasAccount() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();
        assertTrue(testBank.hasAccount(accountNumber));
        assertFalse(testBank.hasAccount(accountNumber + 1));
    }

    @Test
    public void testGetInvalidAccount() {
        Bank testBank = new Bank();
        try {
            testBank.getAccount(5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }
}
