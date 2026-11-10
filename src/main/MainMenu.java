package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 4;
	private static final int MAX_SELECTION = 4;

	private Bank bank;
    private Scanner keyboardInput;

    public MainMenu() {
        this.bank = new Bank();
        this.bank.openAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Make a deposit");
        System.out.println("2. Open a new account");
        System.out.println("3. Check an account balance");
        System.out.println("4. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                performOpenAccount();
                break;
            case 3:
                performCheckBalance();
                break;
        }
    }

    public int selectAccountNumber() {
        System.out.println("Your accounts: " + bank.getAccountNumbers());
        int accountNumber = -1;
        while(!bank.hasAccount(accountNumber)) {
            System.out.print("Enter an account number: ");
            accountNumber = keyboardInput.nextInt();
        }
        return accountNumber;
    }

    public void performDeposit() {
        BankAccount account = bank.getAccount(selectAccountNumber());
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
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

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
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
