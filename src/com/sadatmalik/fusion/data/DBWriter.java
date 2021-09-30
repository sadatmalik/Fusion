package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.Income;
import com.sadatmalik.fusion.model.MonthlyIncome;
import com.sadatmalik.fusion.model.WeeklyIncome;

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

            // @todo refactor these -- if the values is null, pull it from income
            // update source only
            if (amount == -1 && weeklyInterval == -1) {
                amount = income.getAmount();
                weeklyInterval = income.getWeeklyInterval();
            }
            // update amount only
            else if (source == null && weeklyInterval == -1) {
                source = income.getSource();
                weeklyInterval = income.getWeeklyInterval();
            }
            // update weeklyInterval only
            else if (source == null && amount == -1) {
                source = income.getSource();
                amount = income.getAmount();
            }
            // update source & amount
            else if (weeklyInterval == -1) {
                weeklyInterval = income.getWeeklyInterval();
            }
            // update source & weeklyInterval
            else if (amount == -1) {
                amount = income.getAmount();
            }
            // update amount & weeklyInterval
            else if (source == null) {
                source = income.getSource();
            }

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
}
