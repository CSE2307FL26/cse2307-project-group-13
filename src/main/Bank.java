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

    public Set<Integer> getAccountNumbers() {
        return accounts.keySet();
    }
}
