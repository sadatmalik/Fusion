package com.sadatmalik.fusion.controllers;

import com.sadatmalik.fusion.data.DBWriter;
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

    private int getNextInt() {
        while (true) {
            try {
                return Integer.parseInt(commandLine.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number value");
            }
        }
    }

    public User selectUser(ArrayList<User> users) {
        User activeUser = null;

        System.out.println();
        for (int i = 1; i <= users.size(); i++) {
            User u = users.get(i-1);
            System.out.println(i + ". " + u.getFirstName() + " " + u.getLastName());
        }
        System.out.println(users.size()+1 + ". Create new user");

        int selection = getNextInt();

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
        // @todo surround with try/catch to check it's an int!
        int selection = getNextInt();

        // validate selection
        while (!(selection > 0 && selection <= MainMenuItem.values().length)) {
            System.out.println("\nPlease select from main menu options [1 - " + MainMenuItem.values().length + "]");
            showMainMenu();
            selection = getNextInt();
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
        int selection = getNextInt();

        // validate selection
        while (!(selection > 0 && selection <= IncomeExpenseMenuItem.values().length)) {
            System.out.println("\nPlease select from income/expense menu options [1 - " +
                    IncomeExpenseMenuItem.values().length + "]");
            showIncomeExpenseMenu();
            selection = getNextInt();
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

        int selection = getNextInt();
        while (!(selection > 0 && selection <= incomeByMenuNumber.size())) {
            System.out.println("Please select an income item from the list [1-" + incomeByMenuNumber.size() + "]");
            selection = getNextInt();
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
        String newSource = null;
        double newAmount = -1;
        int newWeeklyInterval = -1;

        // display the selected item
        System.out.print("Type 'return' to keep current Income Source '" + income.getSource() + "'" +
                " or enter new value: ");
        String input = commandLine.nextLine();
        if (!"".equals(input)) {
            newSource = input;
        }

        System.out.print("Type 'return' to keep current Amount '" + income.getAmount() + "'" +
                " or enter new value: ");
        input = commandLine.nextLine();
        while (!"".equals(input)) {
            try {
                newAmount = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for new amount: ");
                input = commandLine.nextLine();
            }
        }

        System.out.print("Type 'return' to keep current Weekly Interval '" + income.getWeeklyInterval() + "'" +
                " or enter new value: ");
        input = commandLine.nextLine();
        while (!"".equals(input)) {
            try {
                newWeeklyInterval = Integer.parseInt(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for new weekly interval: ");
                input = commandLine.nextLine();
            }
        }

        int rowsAffected = DBWriter.updateIncome(income, newSource, newAmount, newWeeklyInterval);

        if (rowsAffected == 1) {
            System.out.println("\nItem updated");
        } else {
            System.out.println("\nNo item was updated");
        }
    }

    private void editMonthlyIncomeItem(MonthlyIncome income) {
        // display the selected item with edit options
        String newSource = null;
        double newAmount = -1;
        int dayOfMonth = -1;

        // display the selected item
        System.out.print("Type 'return' to keep current Income Source '" + income.getSource() + "'" +
                " or enter new value: ");
        String input = commandLine.nextLine();
        if (!"".equals(input)) {
            newSource = input;
        }

        System.out.print("Type 'return' to keep current Amount '" + income.getAmount() + "'" +
                " or enter new value: ");
        input = commandLine.nextLine();
        while (!"".equals(input)) {
            try {
                // @todo validate for positive amount
                newAmount = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for new amount: ");
                input = commandLine.nextLine();
            }
        }

        System.out.print("Type 'return' to keep current Day of Month received '" + income.getDayOfMonthReceived() + "'" +
                " or enter new value: ");
        input = commandLine.nextLine();
        while (!"".equals(input)) {
            try {
                // @todo check for valid date range
                dayOfMonth = Integer.parseInt(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for Day of Month interval: ");
                input = commandLine.nextLine();
            }
        }

        int rowsAffected = DBWriter.updateMonthlyIncome(income, newSource, newAmount, dayOfMonth);

        if (rowsAffected == 1) {
            System.out.println("\nItem updated");
        } else {
            System.out.println("\nNo item was updated");
        }

    }

    public boolean editAnotherItem() {
        System.out.println("\nEdit another item (yes/no) or type 'return' to return to home menu.");
        String another = commandLine.nextLine();
        if (another.equalsIgnoreCase("YES") || another.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void addNewIncomeItem(User user) {
        String source = null;
        double amount = -1;
        int accountId = -1;

        System.out.println("\nEnter new income item details:");

        // source
        System.out.print("Income source: ");
        String input = commandLine.nextLine();
        if (!"".equals(input)) {
            source = input;
        }

        // amount
        System.out.print("Amount: ");
        input = commandLine.nextLine();
        while (!"".equals(input)) {
            try {
                amount = Double.parseDouble(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for amount: ");
                input = commandLine.nextLine();
            }
        }

        accountId = selectAccount(user);

        if (isWeeklyIncome()) {
            // weekly interval for weekly incomes
            int weeklyInterval = getWeeklyInterval();
            DBWriter.insertIncomeForAccount(source, amount, weeklyInterval, accountId, user.getUserId());
            System.out.println("\nSuccess");
        } else {
            // day of month received for monthly incomes
            int dayOfMonth = getDayOfMonthReceived();
            DBWriter.insertMonthlyIncomeForAccount(source, amount, dayOfMonth, accountId, user.getUserId());
            System.out.println("\nSuccess");
        }

    }

    private boolean isWeeklyIncome() {
        System.out.println("Is this weekly or monthly?  ('w' for weekly, 'm' for monthly) ");
        String weeklyMonthly = commandLine.nextLine();
        if (!(weeklyMonthly.equalsIgnoreCase("w") || weeklyMonthly.equalsIgnoreCase("m"))) {
            return isWeeklyIncome();
        } else {
            return weeklyMonthly.equalsIgnoreCase("w");
        }
    }

    private int getWeeklyInterval() {
        System.out.print("Weekly interval ('0' for one off income): ");
        String input = commandLine.nextLine();
        int interval = -1;
        while (!"".equals(input)) {
            try {
                // make sure it's a positive integer
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.print("Please enter a positive numerical value (or 0) for weekly interval: ");
                    input = commandLine.nextLine();
                    continue;
                } else {
                    interval = value;
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for  weekly interval: ");
                input = commandLine.nextLine();
            }
        }
        return interval;
    }

    private int getDayOfMonthReceived() {
        System.out.print("Day of Month received: ");
        String input = commandLine.nextLine();
        int dom = -1;
        while (!"".equals(input)) {
            try {
                // make sure it's a positive integer
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.print("Please enter a positive numerical value for day of month received: ");
                    input = commandLine.nextLine();
                    continue;
                } else {
                    dom = value;
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a numerical value for day of month received: ");
                input = commandLine.nextLine();
            }
        }
        return dom;
    }

    private int selectAccount(User user) {
        System.out.println("\nPlease select the account this be paid in to: ");

        // @todo flow issue - accounts may not yet have been loaded -- currently via call to Controller.QuickStats()
        ArrayList<Account> accounts = user.getAccounts();
        Account account = null;

        for (int i = 1; i <= accounts.size(); i++) {
            Account a = accounts.get(i-1);
            System.out.println(i + ". " + a.getName().toUpperCase() + " " + a.getType());
        }

        int selection = getNextInt();

        if (selection > 0 && selection <= accounts.size()) {
            // select user from users
            account = accounts.get(selection-1);

        } else {
            // invalid selection, try again
            System.out.println("\nPlease select user from options [1 - " + (accounts.size()+1) + "]");
            selectAccount(user);
        }

        return account.getAccountId();

    }

    public void close() {
        commandLine.close();
    }

}
