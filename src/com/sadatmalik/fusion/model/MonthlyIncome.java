package com.sadatmalik.fusion.model;

public class MonthlyIncome extends Income {

    private int dayOfMonthReceived;

    public MonthlyIncome(int id, double amount, String source, int dayOfMonthReceived) {
        this.id = id;
        this.amount = amount;
        this.source = source;
        this.dayOfMonthReceived = dayOfMonthReceived;

    }

    public int getDayOfMonthReceived() {
        return dayOfMonthReceived;
    }

    @Override
    public String toString() {
        return "MonthlyIncome{" +
                "id=" + id +
                ", amount=" + amount +
                ", source='" + source + '\'' +
                ", dayOfMonthReceived=" + dayOfMonthReceived +
                '}';
    }
}
