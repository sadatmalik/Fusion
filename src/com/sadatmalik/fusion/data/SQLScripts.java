package com.sadatmalik.fusion.data;

public class SQLScripts {

    public static final String QUERY_TOTAL_MONTHLY_INCOME_FOR_USER = "SELECT sum(mi.monthly_income_amount)\n" +
            "FROM monthly_income mi\n" +
            "JOIN users_monthly_income umi\n" +
            "ON mi.monthly_income_id = umi.monthly_income_id\n" +
            "JOIN users u\n" +
            "ON umi.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_ALL_USERS = "SELECT * FROM users;";

    public static final String QUERY_ACCOUNTS_FOR_USER = "SELECT a.* , a_t.description\n" +
            "FROM accounts a\n" +
            "JOIN account_types a_t\n" +
            "ON a.account_type = a_t.account_types_id\n" +
            "JOIN users_accounts ua\n" +
            "ON a.account_id = ua.account_id\n" +
            "JOIN users u\n" +
            "ON ua.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;\n";

    public static final String QUERY_DEBTS_FOR_USER = "SELECT d.*, (d.total_borrowed * d.interest_rate / 100 / 12) AS monthly\n" +
            "FROM debt d\n" +
            "JOIN users_debt ud\n" +
            "ON d.debt_id = ud.debt_id\n" +
            "JOIN users u\n" +
            "ON ud.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_MONTHLY_INCOME_FOR_USER = "SELECT mi.*\n" +
            "FROM monthly_income mi\n" +
            "JOIN users_monthly_income umi\n" +
            "ON mi.monthly_income_id = umi.monthly_income_id\n" +
            "JOIN users u\n" +
            "ON umi.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_WEEKLY_INCOME_FOR_USER = "SELECT wi.*\n" +
            "FROM income wi\n" +
            "JOIN users_income uwi\n" +
            "ON wi.income_id = uwi.income_id\n" +
            "JOIN users u\n" +
            "ON uwi.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_MONTHLY_EXPENSES_FOR_USER = "SELECT mex.*\n" +
            "FROM monthly_expenses mex\n" +
            "JOIN users_monthly_expenses umex\n" +
            "ON mex.monthly_expense_id = umex.monthly_expense_id\n" +
            "JOIN users u\n" +
            "ON umex.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_WEEKLY_EXPENSES_FOR_USER = "SELECT wex.*\n" +
            "FROM weekly_expenses wex\n" +
            "JOIN users_weekly_expenses uwex\n" +
            "ON wex.weekly_expense_id = uwex.weekly_expense_id\n" +
            "JOIN users u\n" +
            "ON uwex.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";


    // Update Weekly Income
    public static final String UPDATE_WEEKLY_INCOME_AMOUNT = "UPDATE income\n" +
            "SET income_amount = ?\n" +
            "WHERE income_id = ?;";

    public static final String UPDATE_WEEKLY_INCOME_WEEKLY_INTERVAL = "UPDATE income\n" +
            "SET income_weekly_interval = ?\n" +
            "WHERE income_id = ?;";

    public static final String UPDATE_WEEKLY_INCOME_SOURCE = "UPDATE income\n" +
            "SET income_source = ?\n" +
            "WHERE income_id = ?;";

    public static final String UPDATE_WEEKLY_INCOME_ALL = "UPDATE income\n" +
            "SET income_source = ?, income_amount = ?, income_weekly_interval = ?\n" +
            "WHERE income_id = ?;";

}
