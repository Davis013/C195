package davis.c195.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import davis.c195.helper.JDBC;
import davis.c195.helper.Log;


public class UserDB {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    // Attempt Login
    public static Boolean login(String username, String password) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM user WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new User();
                currentUser.setUsername(results.getString("User_Name"));
                statement.close();
                Log.log(username, true);
                return true;
            } else {
                Log.log(username, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
