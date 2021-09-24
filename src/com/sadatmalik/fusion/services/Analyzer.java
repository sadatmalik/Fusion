package com.sadatmalik.fusion.services;

import com.sadatmalik.fusion.model.*;

// Utility methods for crunching the numbers
public class Analyzer {

    public static String getIncomeDebtRatioFor(User user) {
        // calculate the total monthly income
        // calculate the total weekly month, pro-rata to month
        double monthlyIncome = 0;
        for (Income income : user.getIncomes()) {
            if (income instanceof MonthlyIncome) {
                monthlyIncome += income.getAmount();
            } else if (income instanceof WeeklyIncome) {
                double weeklyIncome = income.getAmount();
                double weeklyInterval = ((WeeklyIncome) income).getWeeklyInterval();

                monthlyIncome +=
                        weeklyInterval == 0 ?
                                weeklyIncome :
                                weeklyIncome * (52 / weeklyInterval) / 12;
            }
        }

        // calculate the total debt
        double monthlyDebt = 0;
        for (Debt debt : user.getDebts()) {
            monthlyDebt += debt.getMonthly();
        }

        // calculate the total monthly expenses
        // calculate the total weekly expenses, pro-rata to month
        double monthlyExpense = 0;
        for (Expense expense : user.getExpenses()) {
            if (expense instanceof MonthlyExpense) {
                monthlyExpense += expense.getAmount();
            } else if (expense instanceof WeeklyExpense) {
                int timesPerWeek = ((WeeklyExpense) expense).getTimesPerWeek();
                int weeklyInterval = ((WeeklyExpense) expense).getWeeklyInterval();

                monthlyExpense +=
                        weeklyInterval == 0 ?
                                expense.getAmount() * timesPerWeek :
                                expense.getAmount() * timesPerWeek * (52 / weeklyInterval) / 12;
            }
        }

        // calculate the ratio of total income to total debt+expenses
        double monthlyIncomeToDebtRatio = monthlyIncome / (monthlyDebt + monthlyExpense);

        // return as a formatted String
        return String.format("%.2f", monthlyIncomeToDebtRatio);
    }

}
