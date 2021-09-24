package com.sadatmalik.fusion.model;

public class MonthlyExpense extends Expense {

    public MonthlyExpense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MonthlyExpense{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
