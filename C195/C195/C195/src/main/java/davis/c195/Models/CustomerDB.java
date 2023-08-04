package davis.c195.Models;

import davis.c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDB {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Returns all customers in the Database
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        allCustomers.clear();
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT customer.CustomerId, customer.CustomerName, address.Address, address.Phone, address.PostalCode, city.City"
                    + " FROM customer INNER JOIN address ON customer.addressId = address.addressId "
                    + "INNER JOIN city ON address.cityId = city.cityId";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                Customer customer = new Customer(
                        results.getInt("CustomerId"),
                        results.getString("CustomerName"),
                        results.getString("Address"),
                        results.getString("City"),
                        results.getString("Phone"),
                        results.getString("PostalCode"));
                allCustomers.add(customer);
            }
            statement.close();
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves Customer to the Database
     */
    public static boolean saveCustomer(String CustomerName, String Address, String CityID, String PostalCode, String Phone) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String queryOne = "INSERT INTO Address SET Address='" + Address + "', Phone='" + Phone + "', PostalCode='" + PostalCode + "', CityID=" + CityID;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                int AddressID = allCustomers.size() + 1;
                String queryTwo = "INSERT INTO Customer SET CustomerName='" + CustomerName + "', AddressId=" + AddressID;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Update existing Customer in Database
    public static boolean updateCustomer(int CustomerID, String CustomerName, String Address, String CityID, String PostalCode, String Phone) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String queryOne = "UPDATE address SET address='" + Address + "', CityID=" + CityID + ", postalCode='" + PostalCode + "', phone='" + Phone + "' "
                    + "WHERE addressId=" + CustomerID;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "UPDATE customer SET customerName='" + CustomerName + "', addressId=" + CustomerID + " WHERE customerId=" + CustomerID;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    // Delete Customer from Database
    public static boolean deleteCustomer(int CustomerID) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String queryOne = "DELETE FROM address WHERE addressId=" + CustomerID;
            int updateOne = statement.executeUpdate(queryOne);
            if(updateOne == 1) {
                String queryTwo = "DELETE FROM customer WHERE customerId=" + CustomerID;
                int updateTwo = statement.executeUpdate(queryTwo);
                if(updateTwo == 1) {
                    return true;
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }


}
