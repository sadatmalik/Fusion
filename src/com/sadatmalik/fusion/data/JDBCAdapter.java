package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.*;

import java.sql.*;
import java.util.ArrayList;

public class JDBCAdapter {

    private final static String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    private final static String HOSTNAME = "localhost";
    private final static String PORT = "3306";
    private final static String DATABASE = "fusion";
    private final static String USER = "sadat";
    private final static String PASSWORD = "pa55word";

    public JDBCAdapter() {
        // 1. Load and initialise the JDBC driver
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);

            // 2. Open a connection
            connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" + PORT +
                    "/" + DATABASE, USER, PASSWORD);

            System.out.println("Connected to Database: " + HOSTNAME + ":" + PORT + "/" + DATABASE);

        } catch (ClassNotFoundException exception) {
            System.out.println("Unable to load JDBC driver");
            exception.printStackTrace();
        } catch (SQLException exception) {
            System.out.println("Unable to open a connection to " + "jdbc:mysql://" + HOSTNAME + ":" + PORT +
            "/" + DATABASE);
            exception.printStackTrace();
        }

    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.out.println("Exception while closing DB connection");
            exception.printStackTrace();
        }
    }

    public double getTotalMonthlyIncomeFor(int userId) {
        Double income = 0.0;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_TOTAL_MONTHLY_INCOME_FOR_USER);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                income = rs.getDouble(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get total monthly income for user: "
                    + userId + " while executing: " + SQLScripts.QUERY_TOTAL_MONTHLY_INCOME_FOR_USER);
            exception.printStackTrace();
        }

        return income;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = null;

        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(SQLScripts.QUERY_ALL_USERS);

            users = ResultSetProcessor.parseUsers(rs);

            rs.close();
            s.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get users from " + DATABASE);
        }

        return users;
    }

    public ArrayList<Account> getAccountsFor(int userId) {
        ArrayList<Account> accounts = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_ACCOUNTS_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            accounts = ResultSetProcessor.parseAccounts(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get accounts for user " + userId + " from " + DATABASE);
        }

        return accounts;
    }

    public ArrayList<Debt> getDebtsFor(int userId) {
        ArrayList<Debt> debts = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_DEBTS_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            debts = ResultSetProcessor.parseDebts(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get debts for user " + userId + " from " + DATABASE);
        }

        return debts;
    }

    public ArrayList<Income> getIncomesFor(int userId) {
        ArrayList<Income> incomes = getMonthlyIncomesFor(userId);
        incomes.addAll(getWeeklyIncomesFor(userId));
        return incomes;
    }

    private ArrayList<Income> getMonthlyIncomesFor(int userId) {
        ArrayList<Income> incomes = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_MONTHLY_INCOME_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            incomes = ResultSetProcessor.parseMonthlyIncomes(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get monthly incomes for user " + userId + " from " + DATABASE);
        }

        return incomes;
    }

    private ArrayList<Income> getWeeklyIncomesFor(int userId) {
        ArrayList<Income> incomes = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_WEEKLY_INCOME_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            incomes = ResultSetProcessor.parseWeeklyIncomes(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get weekly incomes for user " + userId + " from " + DATABASE);
        }

        return incomes;
    }

    public ArrayList<Expense> getExpensesFor(int userId) {
        ArrayList<Expense> expenses = getMonthlyExpensesFor(userId);
        expenses.addAll(getWeeklyExpensesFor(userId));
        return expenses;
    }

    private ArrayList<Expense> getMonthlyExpensesFor(int userId) {
        ArrayList<Expense> expenses = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_MONTHLY_EXPENSES_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            expenses = ResultSetProcessor.parseMonthlyExpenses(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get monthly expenses for user " + userId + " from " + DATABASE);
        }

        return expenses;
    }

    private ArrayList<Expense> getWeeklyExpensesFor(int userId) {
        ArrayList<Expense> expenses = null;

        try {
            PreparedStatement ps = connection.prepareStatement(SQLScripts.QUERY_WEEKLY_EXPENSES_FOR_USER);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            expenses = ResultSetProcessor.parseWeeklyExpenses(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get weekly expenses for user " + userId + " from " + DATABASE);
        }

        return expenses;
    }
}
