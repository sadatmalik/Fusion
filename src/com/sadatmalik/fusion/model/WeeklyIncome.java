package com.sadatmalik.fusion.model;

public class WeeklyIncome extends Income {

    private int weeklyInterval;

    public WeeklyIncome(int id, double amount, String source, int weeklyInterval) {
        this.id = id;
        this.amount = amount;
        this.source = source;
        this.weeklyInterval = weeklyInterval;
    }

    public int getWeeklyInterval() {
        return weeklyInterval;
    }

    @Override
    public String toString() {
        return "WeeklyIncome{" +
                "id=" + id +
                ", amount=" + amount +
                ", source='" + source + '\'' +
                ", weeklyInterval=" + weeklyInterval +
                '}';
    }
}
