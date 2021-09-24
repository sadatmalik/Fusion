package com.sadatmalik.fusion.model;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int userId;

    private ArrayList<Account> accounts;
    private Map<AccountType, ArrayList<Account>> accountByType;

    private ArrayList<Debt> debts;
    private ArrayList<Income> incomes;
    private ArrayList<Expense> expenses;

    public User(String firstName, String lastName, String email, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;

        // lazy initialise accounts map
        if (accountByType == null) {
            accountByType = new HashMap<>();
        }

        // add accounts by account type
        for (Account account : accounts) {
            if (accountByType.get(account.getType()) == null) {
                ArrayList<Account> accountList = new ArrayList<>();
                accountList.add(account);
                accountByType.put(account.getType(), accountList);
            } else {
                accountByType.get(account.getType()).add(account);
            }
        }
    }

    public ArrayList<Account> getAccountsByType(AccountType accountType) {
        return accountByType.get(accountType);
    }

    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
    }

    public ArrayList<Debt> getDebts() {
        return debts;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                ", accounts=" + accounts +
                ", accountByType=" + accountByType +
                ", debts=" + debts +
                ", incomes=" + incomes +
                ", expenses=" + expenses +
                '}';
    }
}
