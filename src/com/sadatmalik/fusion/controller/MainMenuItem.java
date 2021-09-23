package com.sadatmalik.fusion.controller;

import java.util.HashMap;
import java.util.Map;

public enum MainMenuItem {

    QUICK_STATS(1, "View Quick Stats"),
    ADD_EDIT_INCOME_EXPENSES(2, "Add/Edit Income & Expenses"),
    RUN_REPORTS(3, "Run Reports"),
    CREATE_VIEW_CUSTOM_GOALS(4, "Create/View Custom Goals"),
    EXIT(5, "Exit");

    private final int itemNumber;
    private final String menuText;

    private static final Map<Integer, MainMenuItem> menuItemById;

    static {
        menuItemById = new HashMap<>();
        for (MainMenuItem menuItem : MainMenuItem.values()) {
            menuItemById.put(menuItem.itemNumber, menuItem);
        }
    }

    MainMenuItem(int itemNumber, String menuText) {
        this.itemNumber = itemNumber;
        this.menuText = menuText;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getMenuText() {
        return menuText;
    }

    public static MainMenuItem getMenuItemById(int id) {
        return menuItemById.get(id);
    }
}
