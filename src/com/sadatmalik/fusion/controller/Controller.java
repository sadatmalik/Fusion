package com.sadatmalik.fusion.controller;

import com.sadatmalik.fusion.model.Account;
import com.sadatmalik.fusion.model.Debt;
import com.sadatmalik.fusion.model.User;
import com.sadatmalik.fusion.services.Lookup;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    private ArrayList<User> users;
    private CommandLine cli;

    private User activeUser;

    public Controller() {
        users = Lookup.getUsers();
        System.out.println("Loaded users from DB: " + Arrays.toString(users.toArray()));

        cli = new CommandLine();
    }

    public static void main(String[] args) {
        Controller fusion = new Controller();

        System.out.println("Welcome to Fusion. Please select from users below:");

        // Select the user for this session
        fusion.selectUser();

        // enter the main application loop
        fusion.mainLoop();

        // exit the application closing any resources
        fusion.exit();
    }

    private void selectUser() {
        activeUser = cli.selectUser(users);
        System.out.println("Hello " + activeUser.getFirstName() + "!");
    }

    private void mainLoop() {

        while(true) {
            MainMenuItem selection = getMainMenuOption();

            switch(selection) {
                case QUICK_STATS:
                    quickStats();
                    break;

                case ADD_EDIT_INCOME_EXPENSES:

                    break;

                case RUN_REPORTS:

                    break;

                case CREATE_VIEW_CUSTOM_GOALS:

                    break;

                case EXIT:
                    System.out.println("Thank you for using Fusion today, " + activeUser.getFirstName());
                    return;
            }

        }
    }

    private MainMenuItem getMainMenuOption() {
        System.out.println("\nFusion Main Menu. What would you like to do?\n");
        cli.showMainMenu();
        return cli.getMainMenuSelection();
    }

    private void quickStats() {
        // get stats

        // account balances
        ArrayList<Account> accounts = Lookup.accountsFor(activeUser);
        activeUser.setAccounts(accounts);

        // debts
        ArrayList<Debt> debts = Lookup.debtsFor(activeUser);
        activeUser.setDebts(debts);

        // income

        // expenses


        // display stats
        cli.displayQuickStats(activeUser);
    }


    private void exit() {
        System.out.println("\nGoodbye!");
        cli.close();
        Lookup.close();
    }
}
