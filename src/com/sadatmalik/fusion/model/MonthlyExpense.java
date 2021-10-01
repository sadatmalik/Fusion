package com.sadatmalik.fusion.model;

public class MonthlyExpense extends Expense {

    public MonthlyExpense(int id, String name, double amount) {
        super(id, name, amount);
    }

    @Override
    public String toString() {
        return "MonthlyExpense{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
