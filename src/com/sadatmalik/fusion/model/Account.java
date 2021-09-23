package com.sadatmalik.fusion.model;

public class Account {

    private int accountId;
    private String name;
    private AccountType type;
    private double balance;

    public Account(int accountId, String name,
                   AccountType type, double balance) {

        this.accountId = accountId;
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", balance=" + balance +
                '}';
    }
}
