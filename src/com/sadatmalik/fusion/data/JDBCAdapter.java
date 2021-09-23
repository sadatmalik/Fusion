package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.Account;
import com.sadatmalik.fusion.model.AccountType;
import com.sadatmalik.fusion.model.User;

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

        String sql = "SELECT sum(mi.monthly_income_amount)\n" +
                "FROM monthly_income mi\n" +
                "JOIN users_monthly_income umi\n" +
                "ON mi.monthly_income_id = umi.monthly_income_id\n" +
                "JOIN users u\n" +
                "ON umi.user_id = u.user_id\n" +
                "WHERE u.user_id = ?;";

        Double income = 0.0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                income = rs.getDouble(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get total monthly income for user: "
                    + userId + " while executing: " + sql);
            exception.printStackTrace();
        }

        return income;
    }

    public ArrayList<User> getUsers() {
        String sql = "SELECT * FROM users;";

        ArrayList<User> users = null;

        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);

            users = parseUsers(rs);

            rs.close();
            s.close();

        } catch (SQLException exception) {
            System.out.println("Unable to get users from " + DATABASE);
        }

        return users;
    }

    private ArrayList<User> parseUsers(ResultSet rs) throws SQLException {
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
            System.out.println("Unable to get users from " + DATABASE);
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


}
