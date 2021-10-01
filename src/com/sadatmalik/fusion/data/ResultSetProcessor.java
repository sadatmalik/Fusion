package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ResultSetProcessor {

    static ArrayList<User> parseUsers(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        while(rs.next()) {
            int userId = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String email = rs.getString(4);

            User user = new User(firstName, lastName, email, userId);

            users.add(user);
        }

        return users;
    }

    static ArrayList<Account> parseAccounts(ResultSet rs) throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            int typeId = rs.getInt(3);
            double balance = rs.getDouble(4);

            Account account = new Account(id, name, AccountType.from(typeId), balance);

            accounts.add(account);
        }

        return accounts;
    }

    static ArrayList<Debt> parseDebts(ResultSet rs) throws SQLException {
        ArrayList<Debt> debts = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            String lender = rs.getString(2);
            double totalOwed = rs.getDouble(3);
            double totalBorrowed = rs.getDouble(4);
            int dayOfMonthPaid = rs.getInt(5);
            double interestRate = rs.getDouble(6);
            Timestamp dateBorrowed = rs.getTimestamp(7);
            int initialTerm = rs.getInt(8);
            double monthly = rs.getDouble(10);

            Debt debt = new Debt.Builder()
                    .id(id)
                    .lender(lender)
                    .totalOwed(totalOwed)
                    .totalBorrowed(totalBorrowed)
                    .dayOfMonthPaid(dayOfMonthPaid)
                    .interestRate(interestRate)
                    .dateBorrowed(new Date(dateBorrowed.getTime()))
                    .initialTerm(initialTerm)
                    .monthly(monthly)
                    .build();

            debts.add(debt);
        }

        return debts;
    }

    static ArrayList<Income> parseMonthlyIncomes(ResultSet rs) throws SQLException {
        ArrayList<Income> incomes = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            double amount = rs.getDouble(2);
            String source = rs.getString(3);
            int dayOfMonthReceived = rs.getInt(4);

            Income income = new MonthlyIncome(id, amount, source, dayOfMonthReceived);

            incomes.add(income);
        }

        return incomes;
    }

    static ArrayList<Income> parseWeeklyIncomes(ResultSet rs) throws SQLException {
        ArrayList<Income> incomes = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            double amount = rs.getDouble(2);
            String source = rs.getString(3);
            int weeklyInterval = rs.getInt(4);

            Income income = new WeeklyIncome(id, amount, source, weeklyInterval);

            incomes.add(income);
        }

        return incomes;
    }

    static ArrayList<Expense> parseMonthlyExpenses(ResultSet rs) throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double amount = rs.getDouble(3);

            Expense expense = new MonthlyExpense(id, name, amount);

            expenses.add(expense);
        }

        return expenses;
    }

    static ArrayList<Expense> parseWeeklyExpenses(ResultSet rs) throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double amount = rs.getDouble(3);
            int timesPerWeek = rs.getInt(4);
            int weeklyInterval = rs.getInt(5);

            Expense expense = new WeeklyExpense(id, name, amount, timesPerWeek, weeklyInterval);

            expenses.add(expense);
        }

        return expenses;
    }

}
