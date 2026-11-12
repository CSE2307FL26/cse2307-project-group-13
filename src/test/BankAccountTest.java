package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testWithdrawReducesBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawFullBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(100);
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidOverdraftWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(150);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testInvalidNegativeWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(-50);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testInvalidWithdrawZero() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        try {
            testAccount.withdraw(0);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testTransactionHistoryInOrder() {
        BankAccount account = new BankAccount();

        account.deposit(50);
        account.withdraw(12.50);

        assertEquals(List.of("Deposit: +$50.00", "Withdrawal: -$12.50"),
                account.getTransactionHistory());
    }

    @Test
    public void testRejectedWithdrawalNotInHistory() {
        BankAccount account = new BankAccount();
        account.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(60));

        assertEquals(List.of("Deposit: +$50.00"), account.getTransactionHistory());
    }

    @Test
    public void testRejectedDepositNotInHistory() {
        BankAccount account = new BankAccount();

        assertThrows(IllegalArgumentException.class, () -> account.deposit(-20));

        assertTrue(account.getTransactionHistory().isEmpty());
    }

}
