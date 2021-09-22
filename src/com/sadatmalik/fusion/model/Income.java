package com.sadatmalik.fusion.model;

public class Income {

    private int id;
    private double amount;
    private String source;

    protected Income(int id, double amount, String source) {
        this.id = id;
        this.amount = amount;
        this.source = source;
    }

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
