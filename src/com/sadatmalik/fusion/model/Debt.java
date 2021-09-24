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

    private Debt(Builder builder) {
        this.id = builder.id;
        this.lender = builder.lender;
        this.totalOwed = builder.totalOwed;
        this.totalBorrowed = builder.totalBorrowed;
        this.dayOfMonthPaid = builder.dayOfMonthPaid;
        this.interestRate = builder.interestRate;
        this.dateBorrowed = builder.dateBorrowed;
        this.initialTerm = builder.initialTerm;
        this.monthly = builder.monthly;
    }

    // use a builder to construct Debt
    public static class Builder {
        private int id;
        private String lender;
        private double totalOwed;
        private double totalBorrowed;
        private int dayOfMonthPaid;
        private double interestRate;
        private Date dateBorrowed;
        private int initialTerm;
        private double monthly;

        public Debt build() {
            return new Debt(this);
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder lender(String lender) {
            this.lender = lender;
            return this;
        }

        public Builder totalOwed(double totalOwed) {
            this.totalOwed = totalOwed;
            return this;
        }

        public Builder totalBorrowed(double totalBorrowed) {
            this.totalBorrowed = totalBorrowed;
            return this;
        }

        public Builder dayOfMonthPaid(int dayOfMonthPaid) {
            this.dayOfMonthPaid = dayOfMonthPaid;
            return this;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder dateBorrowed(Date dateBorrowed) {
            this.dateBorrowed = dateBorrowed;
            return this;
        }

        public Builder initialTerm(int initialTerm) {
            this.initialTerm = initialTerm;
            return this;
        }

        public Builder monthly(double monthly) {
            this.monthly = monthly;
            return this;
        }

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

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public double getMonthly() {
        return monthly;
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
