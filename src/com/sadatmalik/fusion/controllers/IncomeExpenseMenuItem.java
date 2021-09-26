package com.sadatmalik.fusion.controllers;

import java.util.HashMap;
import java.util.Map;

// 1) View/Edit Detailed Income
// 2) Add New Income Item
// 3) View/Edit Detailed Expenses
// 4) Add New Expense Item
public enum IncomeExpenseMenuItem {

    VIEW_EDIT_DETAILED_INCOME(1, "View/Edit Detailed Income"),
    ADD_NEW_INCOME_ITEM(2, "Add New Income Item"),
    VIEW_EDIT_DETAILED_EXPENSES(3, "View/Edit Detailed Expenses"),
    ADD_NEW_EXPENSE_ITEM(4, "Add New Expense Item");

    private final int itemNumber;
    private final String menuText;

    private static final Map<Integer, IncomeExpenseMenuItem> menuItemById;

    static {
        menuItemById = new HashMap<>();
        for (IncomeExpenseMenuItem menuItem : IncomeExpenseMenuItem.values()) {
            menuItemById.put(menuItem.itemNumber, menuItem);
        }
    }

    IncomeExpenseMenuItem(int itemNumber, String menuText) {
        this.itemNumber = itemNumber;
        this.menuText = menuText;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getMenuText() {
        return menuText;
    }

    public static IncomeExpenseMenuItem getMenuItemById(int id) {
        return menuItemById.get(id);
    }
}
