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

    @Test
    public void testBalanceOfEachAccount() {
        Bank testBank = new Bank();
        int firstAccount = testBank.openAccount();
        int secondAccount = testBank.openAccount();
        testBank.getAccount(firstAccount).deposit(50);
        testBank.getAccount(secondAccount).deposit(20);
        assertEquals(50, testBank.getBalance(firstAccount), 0.01);
        assertEquals(20, testBank.getBalance(secondAccount), 0.01);
    }

    @Test
    public void testBalanceOfNewAccount() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();
        assertEquals(0, testBank.getBalance(accountNumber), 0.01);
    }

    @Test
    public void testBalanceOfInvalidAccount() {
        Bank testBank = new Bank();
        try {
            testBank.getBalance(5);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }
@Test
    public void testCloseEmptyAccount() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();

        testBank.closeAccount(accountNumber);

        assertFalse(testBank.hasAccount(accountNumber));
        assertEquals(0, testBank.getAccountNumbers().size());
    }

@Test
    public void testCannotCloseAccountWithMoney() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();

        testBank.getAccount(accountNumber).deposit(100);

        try {
            testBank.closeAccount(accountNumber);
            fail();
        } catch (IllegalStateException e) {
            // test passes
        }

        assertTrue(testBank.hasAccount(accountNumber));
        assertEquals(100, testBank.getAccount(accountNumber).getBalance(), 0.01);
    }

@Test
    public void testCannotCloseInvalidAccount() {
        Bank testBank = new Bank();

        try {
            testBank.closeAccount(99);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testTransfer() {
        Bank testBank = new Bank();
        int fromAccount = testBank.openAccount();
        int toAccount = testBank.openAccount();
        testBank.getAccount(fromAccount).deposit(100);
        testBank.transfer(fromAccount, toAccount, 40);
        assertEquals(60, testBank.getBalance(fromAccount), 0.01);
        assertEquals(40, testBank.getBalance(toAccount), 0.01);
    }

    @Test
    public void testTransferMoreThanBalance() {
        Bank testBank = new Bank();
        int fromAccount = testBank.openAccount();
        int toAccount = testBank.openAccount();
        testBank.getAccount(fromAccount).deposit(50);
        try {
            testBank.transfer(fromAccount, toAccount, 80);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
        assertEquals(50, testBank.getBalance(fromAccount), 0.01);
        assertEquals(0, testBank.getBalance(toAccount), 0.01);
    }

    @Test
    public void testTransferNegativeAmount() {
        Bank testBank = new Bank();
        int fromAccount = testBank.openAccount();
        int toAccount = testBank.openAccount();
        testBank.getAccount(fromAccount).deposit(50);
        try {
            testBank.transfer(fromAccount, toAccount, -20);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTransferToSameAccount() {
        Bank testBank = new Bank();
        int accountNumber = testBank.openAccount();
        testBank.getAccount(accountNumber).deposit(50);
        try {
            testBank.transfer(accountNumber, accountNumber, 20);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testTransferToInvalidAccount() {
        Bank testBank = new Bank();
        int fromAccount = testBank.openAccount();
        testBank.getAccount(fromAccount).deposit(50);
        try {
            testBank.transfer(fromAccount, 99, 20);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
        assertEquals(50, testBank.getBalance(fromAccount), 0.01);
    }

}
