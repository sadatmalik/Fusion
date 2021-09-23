package com.sadatmalik.fusion.model;

import java.util.Date;

public class Debt {
    private int id;
    private String lender;
    private double totalOwed;
    private double totalBorrowed;
    private int dayOfMonthPaid;
    private double interestRate;
    private Date dateBorrowed;
    private int initialTerm;
    private double monthly;

    public Debt(int id, String lender, double totalOwed, double totalBorrowed,
                int dayOfMonthPaid, double interestRate, Date dateBorrowed, int initialTerm, double monthly) {
        this.id = id;
        this.lender = lender;
        this.totalOwed = totalOwed;
        this.totalBorrowed = totalBorrowed;
        this.dayOfMonthPaid = dayOfMonthPaid;
        this.interestRate = interestRate;
        this.dateBorrowed = dateBorrowed;
        this.initialTerm = initialTerm;
        this.monthly = monthly;
    }

    public int getId() {
        return id;
    }

    public String getLender() {
        return lender;
    }

    public double getTotalOwed() {
        return totalOwed;
    }

    public double getTotalBorrowed() {
        return totalBorrowed;
    }

    public int getDayOfMonthPaid() {
        return dayOfMonthPaid;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getInitialTerm() {
        return initialTerm;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", lender='" + lender + '\'' +
                ", totalOwed=" + totalOwed +
                ", totalBorrowed=" + totalBorrowed +
                ", dayOfMonthPaid=" + dayOfMonthPaid +
                ", interestRate=" + interestRate +
                ", dateBorrowed=" + dateBorrowed +
                ", initialTerm=" + initialTerm +
                ", monthly=" + monthly +
                '}';
    }
}
