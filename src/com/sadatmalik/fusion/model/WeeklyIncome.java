package com.sadatmalik.fusion.model;

public class WeeklyIncome extends Income {

    private int weeklyInterval;

    public WeeklyIncome(int id, double amount, String source, int weeklyInterval) {
        super(id, amount, source);
        this.weeklyInterval = weeklyInterval;
    }

    public int getWeeklyInterval() {
        return weeklyInterval;
    }
}
