package davis.c195.Controllers;

import davis.c195.Models.Appointment;
import davis.c195.Models.AppointmentDB;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller for Appointment Mainscreen
 * @author Brandon Davis
 */
public class Appointmentmain implements Initializable {



    @FXML
    private TableView<Appointment> AppointmentTable;

    @FXML
    private TableColumn<Appointment, String> Contact;

    @FXML
    private TableColumn<Customer, Integer> CustomerID;

    @FXML
    private TableColumn<Customer, String> CustomerName;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<Appointment, String> Description;

    @FXML
    private TableColumn<Appointment, String> End;

    @FXML
    private TableColumn<Appointment, String> Location;


    @FXML
    private TableColumn<Appointment, String> Start;

    private Customer selectedCustomer;

    private Appointment selectedAppointment;

    @FXML
    void addAppointmentButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AppointmentAdd.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void appointmentMonthlyButton(ActionEvent event) {

        AppointmentTable.setItems(AppointmentDB.getMonthlyAppointments(selectedCustomer.getCustomerID()));

    }

    @FXML
    void appointmentWeeklyButton(ActionEvent event) {

        AppointmentTable.setItems(AppointmentDB.getWeeklyAppointments(selectedCustomer.getCustomerID()));
    }

    @FXML
    void cancelButton(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mainscreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAppointmentButton(ActionEvent event) {

        if (AppointmentTable.getSelectionModel().getSelectedItem() != null) {

            selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you would like to delete this appointment: " + selectedAppointment.getAppointmentID() + "?");
        Optional<ButtonType> result = alert.showAndWait();


        /*
         * Lambda Function 1 used to simplify deletion process
         */
        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                AppointmentDB.deleteAppointment(selectedAppointment.getCustomerID());

                try {
                    AppointmentTable.setItems(AppointmentDB.getAllAppointments());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }


        });

    }

    @FXML
    void modifyAppointmentButton(ActionEvent event) throws IOException{

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
            String query = "SELECT * FROM Appointments WHERE Customer_ID = ?";

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
                  Retrieves Appointment information from the resultset
                 */
                int appointmentID = resultSet.getInt("Appointment_ID");
                int customerID = resultSet.getInt("Customer_ID");
                int userID  = resultSet.getInt("User_ID");
                String description = resultSet.getString("Description");
                String Type = resultSet.getString("Type");
                String title = resultSet.getString("Title");
                String Contact = resultSet.getString("Contact");
                String location = resultSet.getString("Location");
                String Start = resultSet.getString("Start");
                String End = resultSet.getString("End");

                LocalDate startDate = resultSet.getDate("Start").toLocalDate();
                LocalDate endDate = resultSet.getDate("End").toLocalDate();

                /*
                  Passes the Customer information to the Customer Update screen
                 */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AppointmentModify.fxml"));
                Parent parent = loader.load();
                Appointmentmodify controller = loader.getController();
                controller.setAppointmentInformation(appointmentID,customerID, userID, title, description, Type, Contact, location, Start, End, startDate, endDate);

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
        CustomerTable.setItems(CustomerDB.getAllCustomers());

        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<>("End"));
        try {
            AppointmentTable.setItems(AppointmentDB.getAllAppointments());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


