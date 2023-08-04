package davis.c195.Controllers;

import davis.c195.Models.Customer;
import davis.c195.Models.CustomerDB;
import davis.c195.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller for Customer Mainscreen
 * @author Brandon Davis
 */

public class Customermain implements Initializable {


    @FXML
    private TableColumn<Customer, Integer> CustomerID;

    @FXML
    private TableView<Customer> CustomerMainTable;

    @FXML
    private TableColumn<Customer, String> CustomerName;

    private Customer selectedCustomer;

    @FXML
    void addButtonAction(ActionEvent event) throws IOException {

        /*
          Opens the Customer Add screen so user can add their information to the database
         */

        FXMLLoader loader = new FXMLLoader (getClass().getResource("/CustomerAddscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException{

        /*
          This brings user back to the Main screen, so they can either choose a different option from the menu or exit back to the login screen from here.
         */


        FXMLLoader loader = new FXMLLoader (getClass().getResource("/Mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        if (CustomerMainTable.getSelectionModel().getSelectedItem() != null) {

            selectedCustomer = CustomerMainTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you would like to delete customer: " + selectedCustomer.getCustomerName() + "?");
        Optional<ButtonType> result = alert.showAndWait();


        /*
         * Lambda Function 1 used to simplify deletion process
         */
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());

                CustomerMainTable.setItems(CustomerDB.getAllCustomers());

            }


        });
    }


    @FXML
    void modifyButtonAction(ActionEvent event) throws IOException {

        /*
          Retrieve the selected Customers ID
          */
        int selectedCustomerId = selectedCustomer.getCustomerID();

        try {
            /*
              Establishes database connection
             */
            Connection connection = JDBC.getConnection();

            /*
              Creates SQL query to gather Customer Data
             */
            String query = "SELECT * FROM Customers WHERE Customer_ID = ?";

            /*
              Preparing statement
             */
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, selectedCustomerId);

            /*
              Executing the query
             */
            ResultSet resultSet = statement.executeQuery();

            /*
              Checks if there is a result
             */
            if (resultSet.next()) {
                /*
                  Retrieves customer information from the resultset
                 */
                int CustomerID = resultSet.getInt("Customer_ID");
                String CustomerName = resultSet.getString("Customer_Name");
                String Address = resultSet.getString("Address");
                String Phone = resultSet.getString("Phone");
                String PostalCode = resultSet.getString("Postal_Code");
                String CityID = resultSet.getString("Division");
                String CountryID = resultSet.getString("Country");

                /*
                  Passes the Customer information to the Customer Update screen
                 */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerUpdatescreen.fxml"));
                Parent parent = loader.load();
                Customerupdate controller = loader.getController();
                controller.setCustomerInformation(CustomerID, CustomerName, Address, Phone, PostalCode, CityID, CountryID);

                /*
                  Creates new scene
                 */
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        CustomerMainTable.setItems(CustomerDB.getAllCustomers());
    }
}

