package com.sadatmalik.fusion.model;

public class WeeklyExpense extends Expense {

    protected int timesPerWeek;
    protected int weeklyInterval;

    public WeeklyExpense(int id, String name, double amount, int timesPerWeek, int weeklyInterval) {
        super(id, name, amount);
        this.timesPerWeek = timesPerWeek;
        this.weeklyInterval = weeklyInterval;
    }

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public int getWeeklyInterval() {
        return weeklyInterval;
    }

    @Override
    public String toString() {
        return "WeeklyExpense{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", timesPerWeek=" + timesPerWeek +
                ", weeklyInterval=" + weeklyInterval +
                '}';
    }
}
