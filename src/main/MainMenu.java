package main;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int DEPOSIT_SELECTION = 1;
    private static final int OPEN_ACCOUNT_SELECTION = 2;
    private static final int CHECK_BALANCE_SELECTION = 3;
    private static final int CLOSE_ACCOUNT_SELECTION = 4;
    private static final int WITHDRAW_SELECTION = 5;
    private static final int TRANSFER_SELECTION = 6;
    private static final int SHOW_HISTORY_SELECTION = 7;
    private static final int EXIT_SELECTION = 8;
    private static final int MAX_SELECTION = 8;

    private Bank bank;
    private Scanner keyboardInput;

    public MainMenu() {
        this.bank = new Bank();
        this.bank.openAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println(DEPOSIT_SELECTION + ". Make a deposit");
        System.out.println(OPEN_ACCOUNT_SELECTION + ". Open a new account");
        System.out.println(CHECK_BALANCE_SELECTION + ". Check an account balance");
        System.out.println(CLOSE_ACCOUNT_SELECTION + ". Close an account");
        System.out.println(WITHDRAW_SELECTION + ". Withdraw funds");
        System.out.println(TRANSFER_SELECTION + ". Transfer between accounts");
        System.out.println(SHOW_HISTORY_SELECTION + ". Show transaction history");
        System.out.println(EXIT_SELECTION + ". Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            try {
                selection = keyboardInput.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and " + max + ".");
                keyboardInput.next();
                continue;
            }
        }
        return selection;
    }

    public void processInput(int selection) {
        try {
            switch (selection) {
                case DEPOSIT_SELECTION:
                    performDeposit();
                    break;
                case OPEN_ACCOUNT_SELECTION:
                    performOpenAccount();
                    break;
                case CHECK_BALANCE_SELECTION:
                    performCheckBalance();
                    break;
                case CLOSE_ACCOUNT_SELECTION:
                    performCloseAccount();
                    break;
                case WITHDRAW_SELECTION:
                    performWithdraw();
                    break;
                case TRANSFER_SELECTION:
                    performTransfer();
                    break;
                case SHOW_HISTORY_SELECTION:
                    performShowHistory();
                    break;
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public int selectAccountNumber() {
        if (bank.getAccountNumbers().isEmpty()) {
            throw new IllegalStateException(
                    "No accounts available. Open a new account first."
            );
        }

        System.out.println("Your accounts: " + bank.getAccountNumbers());
        int accountNumber = -1;

        while (!bank.hasAccount(accountNumber)) {
            System.out.print("Enter an account number: ");
            accountNumber = keyboardInput.nextInt();
        }

        return accountNumber;
    }

    public void performDeposit() {
        BankAccount account = bank.getAccount(selectAccountNumber());
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }
        account.deposit(depositAmount);
    }

    public void performOpenAccount() {
        int accountNumber = bank.openAccount();
        System.out.println("Opened new account #" + accountNumber);
    }

    public void performCheckBalance() {
        int accountNumber = selectAccountNumber();
        System.out.printf("Account #%d balance: $%.2f%n", accountNumber, bank.getBalance(accountNumber));
    }

    public void performCloseAccount() {
        int accountNumber = selectAccountNumber();

        try {
            bank.closeAccount(accountNumber);
            System.out.println("Account #" + accountNumber + " has been closed successfully.");
        } catch (IllegalStateException e) {
            System.out.println("Cannot close account: " + e.getMessage());
        }
    }

    public void performWithdraw() {
        int accountNumber = selectAccountNumber();
        BankAccount account = bank.getAccount(accountNumber);
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextDouble();
        }
        try {
            account.withdraw(withdrawAmount);
            System.out.println("Successfully withdrew $" + withdrawAmount + " from account #" + accountNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    public void performTransfer() {
        System.out.println("Transfer from:");
        int fromAccountNumber = selectAccountNumber();
        System.out.println("Transfer to:");
        int toAccountNumber = selectAccountNumber();
        System.out.print("How much would you like to transfer: ");
        double transferAmount = keyboardInput.nextDouble();
        try {
            bank.transfer(fromAccountNumber, toAccountNumber, transferAmount);
            System.out.printf("Transferred $%.2f from account #%d to account #%d%n",
                    transferAmount, fromAccountNumber, toAccountNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    public void performShowHistory() {
        int accountNumber = selectAccountNumber();
        List<String> history = bank.getAccount(accountNumber).getTransactionHistory();
        System.out.println("Transaction history for account #" + accountNumber + ":");
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (String transaction : history) {
            System.out.println(transaction);
        }
    }

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
