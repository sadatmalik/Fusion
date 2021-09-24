package com.sadatmalik.fusion.model;

public abstract class Income {

    protected int id;
    protected double amount;
    protected String source;

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

}
