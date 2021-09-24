package com.sadatmalik.fusion.data;

import com.sadatmalik.fusion.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetProcessor {

    static ArrayList<User> parseUsers(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        while(rs.next()) {
            int userId = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String email = rs.getString(4);

            User user = new User(firstName, lastName, email, userId);

            users.add(user);
        }

        return users;
    }

}
