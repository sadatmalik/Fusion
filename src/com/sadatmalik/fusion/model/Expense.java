package com.sadatmalik.fusion.model;

public abstract class Expense {

    protected int id;
    protected String name;
    protected double amount;

    public Expense(int id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
