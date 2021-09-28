package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.Income;
import com.sadatmalik.fusion.model.WeeklyIncome;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBWriter {

    public static int updateIncome(WeeklyIncome income, String source,
                                   double amount, int weeklyInterval) {
        if (source == null && amount == -1 && weeklyInterval == -1) {
            return -1;
        }

        int rowsAffected = 0;
        String sql = null;
        try {

            PreparedStatement ps = null;

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
            System.out.println("Error updating weekly income with sql - " + SQLScripts.UPDATE_WEEKLY_INCOME_AMOUNT);
        }

        // return the number of rows affected by this update - should be 1
        return rowsAffected;
    }
}
