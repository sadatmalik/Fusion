package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBWriter {

    public static int updateIncome(WeeklyIncome income, String source,
                                   double amount, int weeklyInterval) {
        if (source == null && amount == -1 && weeklyInterval == -1) {
            return -1;
        }

        int rowsAffected = 0;
        try {

            PreparedStatement ps = null;

            if (source == null)
                source = income.getSource();

            if (amount == -1)
                amount = income.getAmount();

            if (weeklyInterval == -1)
                weeklyInterval = income.getWeeklyInterval();

            // update all
            ps = JDBCAdapter.getConnection().prepareStatement(SQLScripts.UPDATE_WEEKLY_INCOME_ALL);
            ps.setString(1, source);
            ps.setDouble(2, amount);
            ps.setInt(3, weeklyInterval);
            ps.setInt(4, income.getId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Error updating weekly income with sql - " + SQLScripts.UPDATE_WEEKLY_INCOME_ALL);
        }

        // return the number of rows affected by this update - should be 1
        return rowsAffected;
    }

    public static int updateMonthlyIncome(MonthlyIncome income, String source,
                                          double amount, int dayOfMonth) {
        if (source == null && amount == -1 && dayOfMonth == -1) {
            return -1;
        }

        int rowsAffected = 0;
        try {
            PreparedStatement ps = null;

            if (source == null)
                source = income.getSource();

            if (amount == -1)
                amount = income.getAmount();

            if (dayOfMonth == -1)
                dayOfMonth = income.getDayOfMonthReceived();

            // update all
            ps = JDBCAdapter.getConnection().prepareStatement(SQLScripts.UPDATE_MONTHLY_INCOME_ALL);
            ps.setString(1, source);
            ps.setDouble(2, amount);
            ps.setInt(3, dayOfMonth);
            ps.setInt(4, income.getId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Error updating monthly income with sql - " + SQLScripts.UPDATE_MONTHLY_INCOME_ALL);
        }

        // return the number of rows affected by this update - should be 1
        return rowsAffected;
    }

    public static void insertIncomeForAccount(String source, double amount, int weeklyInterval,
                                              int accountId, int userId) {
        try {
            CallableStatement cs = JDBCAdapter.getConnection().prepareCall(SQLScripts.SP_INSERT_WEEKLY_INCOME);

            cs.setString(1, source);
            cs.setDouble(2, amount);
            cs.setInt(3, weeklyInterval);
            cs.setInt(4, accountId);
            cs.setInt(5, userId);

            cs.execute();

        } catch (SQLException exception) {
            System.out.println("Error inserting weekly income with SP call - " + SQLScripts.SP_INSERT_WEEKLY_INCOME);
        }
    }

    public static void insertMonthlyIncomeForAccount(String source, double amount, int dayOfMonth,
                                                     int accountId, int userId) {
        try {
            CallableStatement cs = JDBCAdapter.getConnection().prepareCall(SQLScripts.SP_INSERT_MONTHLY_INCOME);

            cs.setString(1, source);
            cs.setDouble(2, amount);
            cs.setInt(3, dayOfMonth);
            cs.setInt(4, accountId);
            cs.setInt(5, userId);

            cs.execute();

        } catch (SQLException exception) {
            System.out.println("Error inserting monthly income with SP call - " + SQLScripts.SP_INSERT_MONTHLY_INCOME);
        }

    }

    public static int updateWeeklyExpense(WeeklyExpense expense, String name, double amount,
                                          int timesPerWeek, int weeklyInterval) {
        if (name == null && amount == -1 && timesPerWeek == -1 && weeklyInterval == -1) {
            return -1;
        }

        if (name == null)
            name = expense.getName();

        if (amount == -1)
            amount = expense.getAmount();

        if (timesPerWeek == -1)
            timesPerWeek = expense.getTimesPerWeek();

        if (weeklyInterval == -1)
            weeklyInterval = expense.getWeeklyInterval();


        int rowsAffected = 0;
        try {

            // update all
            PreparedStatement ps = JDBCAdapter.getConnection().prepareStatement(SQLScripts.UPDATE_WEEKLY_EXPENSE_ALL);
            ps.setString(1, name);
            ps.setDouble(2, amount);
            ps.setInt(3, timesPerWeek);
            ps.setInt(4, weeklyInterval);
            ps.setInt(5, expense.getId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Error updating weekly expense with sql - " + SQLScripts.UPDATE_WEEKLY_EXPENSE_ALL);
        }

        // return the number of rows affected by this update - should be 1
        return rowsAffected;
    }

    public static int updateMonthlyExpense(MonthlyExpense expense, String source,
                                           double amount, int dayOfMonth) {
        if (source == null && amount == -1 && dayOfMonth == -1) {
            return -1;
        }

        if (source == null)
            source = expense.getName();

        if (amount == -1)
            amount = expense.getAmount();

        /* if (dayOfMonth == -1)
            dayOfMonth = expense.getDayOfMonthPaid(); */


        int rowsAffected = 0;
        try {

            // update all
            PreparedStatement ps = JDBCAdapter.getConnection().prepareStatement(SQLScripts.UPDATE_MONTHLY_EXPENSE_ALL);
            ps.setString(1, source);
            ps.setDouble(2, amount);
            // ps.setInt(3, dayOfMonth);
            ps.setInt(3, expense.getId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Error updating monthly expense with sql - " + SQLScripts.UPDATE_MONTHLY_EXPENSE_ALL);
        }

        // return the number of rows affected by this update - should be 1
        return rowsAffected;
    }
}
