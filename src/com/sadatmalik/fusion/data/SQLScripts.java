package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.User;

import java.sql.*;
import java.util.ArrayList;

public class SQLScripts {

    public static final String QUERY_TOTAL_MONTHLY_INCOME_FOR_USER = "SELECT sum(mi.monthly_income_amount)\n" +
            "FROM monthly_income mi\n" +
            "JOIN users_monthly_income umi\n" +
            "ON mi.monthly_income_id = umi.monthly_income_id\n" +
            "JOIN users u\n" +
            "ON umi.user_id = u.user_id\n" +
            "WHERE u.user_id = ?;";

    public static final String QUERY_ALL_USERS = "SELECT * FROM users;";


}
