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
        String sql = "SELECT a.* , a_t.description\n" +
                "FROM accounts a\n" +
                "JOIN account_types a_t\n" +
                "ON a.account_type = a_t.account_types_id\n" +
                "JOIN users_accounts ua\n" +
                "ON a.account_id = ua.account_id\n" +
                "JOIN users u\n" +
                "ON ua.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;\n";

        ArrayList<Account> accounts = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            accounts = parseAccounts(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get accounts for user " + userId + " from " + DATABASE);
        }

        return accounts;
    }

    private ArrayList<Account> parseAccounts(ResultSet rs) throws SQLException {
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

    public ArrayList<Debt> getDebtsFor(int userId) {
        String sql = "SELECT d.*, (d.total_borrowed * d.interest_rate / 100 / 12) AS monthly\n" +
                "FROM debt d\n" +
                "JOIN users_debt ud\n" +
                "ON d.debt_id = ud.debt_id\n" +
                "JOIN users u\n" +
                "ON ud.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        ArrayList<Debt> debts = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            debts = parseDebts(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get debts for user " + userId + " from " + DATABASE);
        }

        return debts;
    }

    private ArrayList<Debt> parseDebts(ResultSet rs) throws SQLException {
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

    public ArrayList<Income> getIncomesFor(int userId) {
        ArrayList<Income> incomes = getMonthlyIncomesFor(userId);
        incomes.addAll(getWeeklyIncomesFor(userId));
        return incomes;
    }

    private ArrayList<Income> getMonthlyIncomesFor(int userId) {
        String sql = "SELECT mi.*\n" +
                "FROM monthly_income mi\n" +
                "JOIN users_monthly_income umi\n" +
                "ON mi.monthly_income_id = umi.monthly_income_id\n" +
                "JOIN users u\n" +
                "ON umi.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        ArrayList<Income> incomes = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            incomes = parseMonthlyIncomes(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get monthly incomes for user " + userId + " from " + DATABASE);
        }

        return incomes;
    }

    private ArrayList<Income> getWeeklyIncomesFor(int userId) {
        String sql = "SELECT wi.*\n" +
                "FROM income wi\n" +
                "JOIN users_income uwi\n" +
                "ON wi.income_id = uwi.income_id\n" +
                "JOIN users u\n" +
                "ON uwi.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        ArrayList<Income> incomes = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            incomes = parseWeeklyIncomes(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get weekly incomes for user " + userId + " from " + DATABASE);
        }

        return incomes;
    }

    private ArrayList<Income> parseMonthlyIncomes(ResultSet rs) throws SQLException {
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

    private ArrayList<Income> parseWeeklyIncomes(ResultSet rs) throws SQLException {
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

    public ArrayList<Expense> getExpensesFor(int userId) {
        ArrayList<Expense> expenses = getMonthlyExpensesFor(userId);
        expenses.addAll(getWeeklyExpensesFor(userId));
        return expenses;
    }

    private ArrayList<Expense> getMonthlyExpensesFor(int userId) {
        String sql = "SELECT mex.*\n" +
                "FROM monthly_expenses mex\n" +
                "JOIN users_monthly_expenses umex\n" +
                "ON mex.monthly_expense_id = umex.monthly_expense_id\n" +
                "JOIN users u\n" +
                "ON umex.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        ArrayList<Expense> expenses = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            expenses = parseMonthlyExpenses(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get monthly expenses for user " + userId + " from " + DATABASE);
        }

        return expenses;
    }

    private ArrayList<Expense> parseMonthlyExpenses(ResultSet rs) throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<>();

        while(rs.next()) {
            String name = rs.getString(2);
            double amount = rs.getDouble(3);

            Expense expense = new MonthlyExpense(name, amount);

            expenses.add(expense);
        }

        return expenses;
    }

    private ArrayList<Expense> getWeeklyExpensesFor(int userId) {
        String sql = "SELECT wex.*\n" +
                "FROM weekly_expenses wex\n" +
                "JOIN users_weekly_expenses uwex\n" +
                "ON wex.weekly_expense_id = uwex.weekly_expense_id\n" +
                "JOIN users u\n" +
                "ON uwex.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        ArrayList<Expense> expenses = null;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            expenses = parseWeeklyExpenses(rs);

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get weekly expenses for user " + userId + " from " + DATABASE);
        }

        return expenses;
    }

    private ArrayList<Expense> parseWeeklyExpenses(ResultSet rs) throws SQLException {
        ArrayList<Expense> expenses = new ArrayList<>();

        while(rs.next()) {
            String name = rs.getString(2);
            double amount = rs.getDouble(3);
            int timesPerWeek = rs.getInt(4);
            int weeklyInterval = rs.getInt(5);

            Expense expense = new WeeklyExpense(name, amount, timesPerWeek, weeklyInterval);

            expenses.add(expense);
        }

        return expenses;
    }

}
