package com.sadatmalik.fusion.controllers;

import com.sadatmalik.fusion.model.Account;
import com.sadatmalik.fusion.model.AccountType;
import com.sadatmalik.fusion.model.Debt;
import com.sadatmalik.fusion.model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLine {

    private Scanner commandLine;

    public CommandLine() {
        // initialise command line
        commandLine = new Scanner(System.in);
    }

    public User selectUser(ArrayList<User> users) {
        User activeUser = null;

        System.out.println();
        for (int i = 1; i <= users.size(); i++) {
            User u = users.get(i-1);
            System.out.println(i + ". " + u.getFirstName() + " " + u.getLastName());
        }
        System.out.println(users.size()+1 + ". Create new user");

        int selection = commandLine.nextInt();

        if (selection > 0 && selection <= users.size()) {
            // select user from users
            activeUser = users.get(selection-1);
        } else if (selection > 0 && selection == users.size()+1) {
            // @todo create a new user

        } else {
            // invalid selection, try again
            System.out.println("\nPlease select user from options [1 - " + (users.size()+1) + "]");
            selectUser(users);
        }

        return activeUser;
    }

    public void showMainMenu() {
        for (MainMenuItem item : MainMenuItem.values()) {
            System.out.println(item.getItemNumber() + ") " + item.getMenuText());
        }
    }

    public MainMenuItem getMainMenuSelection() {
        int selection = commandLine.nextInt();

        // validate selection
        while (!(selection > 0 && selection <= MainMenuItem.values().length)) {
            System.out.println("\nPlease select from main menu options [1 - " + MainMenuItem.values().length + "]");
            showMainMenu();
            selection = commandLine.nextInt();
        }

        // return selected main menu item
        return MainMenuItem.getMenuItemById(selection);
    }

    public void displayQuickStats(User user) {
        System.out.println();
        if (user.getAccounts() != null) {
            displayAccountBalances(user.getAccountsByType(AccountType.CURRENT));
            displayAccountBalances(user.getAccountsByType(AccountType.SAVINGS));
            displayAccountBalances(user.getAccountsByType(AccountType.CASH));
        }

        System.out.println();
        if (user.getDebts() != null) {
            displayDebts(user.getDebts());
        }
    }

    private void displayAccountBalances(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.printf("Your %s %s account balance is: £%,.2f\n", account.getName().toUpperCase(),
                    account.getType(),account.getBalance());
        }
    }

    private void displayDebts(ArrayList<Debt> debts) {
        for (Debt debt : debts) {
            System.out.printf("Your %s debt is: %,.2f\n",debt.getLender().toUpperCase(),
                    debt.getTotalOwed());
        }
    }

    public void displayIncomeDebtRatio(String incomeDebtRatio) {
        System.out.println("\nYour income to debt ratio is currently " + incomeDebtRatio + " to 1 ");
    }

    public void displaySavingsForMonth(double savingsForMonth) {
        System.out.printf("You are currently saving £%,.2f per month\n", savingsForMonth);
    }

    public void close() {
        commandLine.close();
    }

}
