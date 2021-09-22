package com.sadatmalik.fusion.controller;

import com.sadatmalik.fusion.model.User;
import com.sadatmalik.fusion.services.Lookup;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    ArrayList<User> users;

    public Controller() {
        users = Lookup.getUsers();
        System.out.println("Loaded users from DB: " + Arrays.toString(users.toArray()));
    }

    public static void main(String[] args) {
        Controller fusion = new Controller();

        for (User user : fusion.users) {
            double income = Lookup.totalMonthlyIncomeFor(user);
            System.out.println("Total monthly income for " + user.getFirstName() + " " +
                    user.getLastName() + ": " + income);

        }
    }
}
