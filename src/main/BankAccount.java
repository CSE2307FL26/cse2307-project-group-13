package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private final List<String> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            transactionHistory.add(String.format("Deposit: +$%.2f", amount));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void withdraw(double amount) {
        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new IllegalArgumentException("Enter a positive withdrawal amount.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        balance -= amount;
        transactionHistory.add(String.format("Withdrawal: -$%.2f", amount));
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
