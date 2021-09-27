package com.sadatmalik.fusion.controllers;

import com.sadatmalik.fusion.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        // map int selection to main menu item
        return MainMenuItem.getMenuItemById(selection);
    }

    public void showIncomeExpenseMenu() {
        for (IncomeExpenseMenuItem menuItem : IncomeExpenseMenuItem.values()) {
            System.out.println(menuItem.getItemNumber() + ") " + menuItem.getMenuText());
        }
    }

    public IncomeExpenseMenuItem getIncomeExpenseMenuSelection() {
        int selection = commandLine.nextInt();

        // validate selection
        while (!(selection > 0 && selection <= IncomeExpenseMenuItem.values().length)) {
            System.out.println("\nPlease select from income/expense menu options [1 - " +
                    IncomeExpenseMenuItem.values().length + "]");
            showIncomeExpenseMenu();
            selection = commandLine.nextInt();
        }

        // map int selection to main menu item
        return IncomeExpenseMenuItem.getMenuItemById(selection);

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
            System.out.printf("Your %s debt is: £%,.2f\n",debt.getLender().toUpperCase(),
                    debt.getTotalOwed());
        }
    }

    public void displayIncomeDebtRatio(String incomeDebtRatio) {
        System.out.println("\nYour income to debt ratio is currently " + incomeDebtRatio + " to 1 ");
    }

    public void displaySavingsForMonth(double savingsForMonth) {
        System.out.printf("You are currently saving £%,.2f per month\n", savingsForMonth);
    }

    public void displayIncomeExpensesLineTotals(double totalIncomeForMonth, double totalExpensesForMonth) {
        System.out.printf("Total income for month: £%,.2f\n", totalIncomeForMonth);
        System.out.printf("Total expenses for month: £%,.2f\n", totalExpensesForMonth);
        System.out.println();
    }

    public Income getIncomeItemToEdit(User user) {
        Map<Integer, Income> incomeByMenuNumber = displayItemizedIncome(user);
        System.out.println("\nPlease select the income item you would like to edit.");

        int selection = commandLine.nextInt();
        while (!(selection > 0 && selection <= incomeByMenuNumber.size())) {
            System.out.println("Please select an income item from the list [1-" + incomeByMenuNumber.size() + "]");
            selection = commandLine.nextInt();
        }
        return incomeByMenuNumber.get(selection);
    }

    // Returns a map of all income for user by menu item number
    private Map<Integer, Income> displayItemizedIncome(User user) {
        Map<Integer, Income> incomeByMenuNumber = new HashMap<>();
        int menuNumber = 1;

        System.out.println("Monthly income:");
        System.out.println("----------------");
        for (Income income : user.getIncomes()) {
            if (income instanceof MonthlyIncome) {
                MonthlyIncome monthlyIncome = (MonthlyIncome) income;
                System.out.printf("%d) %s: £%,.2f    received on month day %d%n", menuNumber,
                        monthlyIncome.getSource(), monthlyIncome.getAmount(), monthlyIncome.getDayOfMonthReceived());
                incomeByMenuNumber.put(menuNumber, income);
                menuNumber++;
            }
        }
        System.out.println("\nWeekly income:");
        System.out.println("----------------");
        for (Income income : user.getIncomes()) {
            if (income instanceof WeeklyIncome) {
                WeeklyIncome weeklyIncome = (WeeklyIncome) income;
                System.out.printf("%d) %s: £%,.2f    received every %d week(s)%n", menuNumber,
                        weeklyIncome.getSource(), weeklyIncome.getAmount(), weeklyIncome.getWeeklyInterval());
                incomeByMenuNumber.put(menuNumber, income);
                menuNumber++;
            }
        }
        return incomeByMenuNumber;
    }

    public void editIncomeItem(Income item) {
        if (item instanceof WeeklyIncome) {
            editWeeklyIncomeItem((WeeklyIncome) item);
        } else if (item instanceof MonthlyIncome) {
            editMonthlyIncomeItem((MonthlyIncome) item);
        }
    }

    private void editWeeklyIncomeItem(WeeklyIncome income) {
        // display the selected item
        System.out.println("Income item: " + income.getSource());
        System.out.println("Amount: " + income.getAmount());
        System.out.println("Weekly interval: " + income.getWeeklyInterval());

        // display menu options for editing item
        System.out.println("Select 1 to edit item name, 2 to edit the amount, 3 to edit the weekly interval, 4 to delete." +
                " Type [0] to choose another item to edit.");

        int selection = commandLine.nextInt();
        while (!(selection >= 0 && selection <= 4)) {
            System.out.println("Sorry, that's not a valid selection. Please choose from [0-4]");
            selection = commandLine.nextInt();
        }

        // @todo pick up from here tomorrow 
    }

    private void editMonthlyIncomeItem(MonthlyIncome income) {
        // display the selected item

        // display menu options for editing item
    }

    public void close() {
        commandLine.close();
    }

}
