package main;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Bank {

    private Map<Integer, BankAccount> accounts;
    private int nextAccountNumber;

    public Bank() {
        this.accounts = new LinkedHashMap<>();
        this.nextAccountNumber = 1;
    }

    public int openAccount() {
        int accountNumber = nextAccountNumber;
        accounts.put(accountNumber, new BankAccount());
        nextAccountNumber++;
        return accountNumber;
    }

    public boolean hasAccount(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public BankAccount getAccount(int accountNumber) {
        if(!hasAccount(accountNumber)) {
            throw new IllegalArgumentException();
        }
        return accounts.get(accountNumber);
    }

    public double getBalance(int accountNumber) {
        return getAccount(accountNumber).getBalance();
    }

    public Set<Integer> getAccountNumbers() {
        return accounts.keySet();
    }

    public void closeAccount(int accountNumber) {
        BankAccount account = getAccount(accountNumber);

        if (account.getBalance() != 0) {
            throw new IllegalStateException(
                "Account still has money. Please withdraw or transfer it first."
            );
        }

        accounts.remove(accountNumber);

    }

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) {
        if (fromAccountNumber == toAccountNumber) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        BankAccount fromAccount = getAccount(fromAccountNumber);
        BankAccount toAccount = getAccount(toAccountNumber);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

}
