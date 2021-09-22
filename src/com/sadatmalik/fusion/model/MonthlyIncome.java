package com.sadatmalik.fusion.model;

public class MonthlyIncome extends Income {

    private int dayOfMonthReceived;

    public MonthlyIncome(int id, double amount, String source, int dayOfMonthReceived) {
        super(id, amount, source);
        this.dayOfMonthReceived = dayOfMonthReceived;
    }

    public int getDayOfMonthReceived() {
        return dayOfMonthReceived;
    }
}
