package com.sadatmalik.fusion.controller;

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

    public void close() {
        commandLine.close();
    }

}
