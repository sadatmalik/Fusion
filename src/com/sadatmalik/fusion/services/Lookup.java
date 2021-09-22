package com.sadatmalik.fusion.services;

import com.sadatmalik.fusion.data.JDBCAdapter;
import com.sadatmalik.fusion.model.User;

import java.util.ArrayList;

// DB lookup utility methods
public class Lookup {

    private static JDBCAdapter db;

    static {
        db = new JDBCAdapter();
    }

    public static double totalMonthlyIncomeFor(User user) {
        return db.getTotalMonthlyIncomeFor(user.getUserId());
    }

    public static ArrayList<User> getUsers() {
        return db.getUsers();
    }

}