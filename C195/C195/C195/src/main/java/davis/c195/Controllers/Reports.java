package davis.c195.Controllers;

import davis.c195.helper.JDBC;
import davis.c195.Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Controller for Reports
 * @author Brandon Davis
 */

public class Reports implements Initializable {
    @FXML
    private RadioButton AppointmentbyCountry;

    @FXML
    private RadioButton Contacts;

    @FXML
    private TableView<Appointment> Reportview;

    @FXML
    private TableColumn<Appointment, Integer> appointmentID;

    @FXML
    private TableColumn<Appointment, String> appointmentTitle;

    @FXML
    private TableColumn<Appointment, String> appointmentDescription;

    @FXML
    private TableColumn<Appointment, String> appointmentLocation;

    @FXML
    private TableColumn<Appointment, String> appointmentContact;

    @FXML
    private TableColumn<Appointment, String> appointmentType;

    @FXML
    private TableColumn<Appointment, String> appointmentStart;

    @FXML
    private TableColumn<Appointment, String> appointmentEnd;

    @FXML
    private RadioButton TotalbyMonth;

    @FXML
    private RadioButton TotalbyType;

    private ToggleGroup reportToggleGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create a toggle group for the radio buttons
        reportToggleGroup = new ToggleGroup();
        TotalbyType.setToggleGroup(reportToggleGroup);
        TotalbyMonth.setToggleGroup(reportToggleGroup);
        AppointmentbyCountry.setToggleGroup(reportToggleGroup);
        Contacts.setToggleGroup(reportToggleGroup);

        // Set the default selection
        TotalbyType.setSelected(true);

        // Add listener to the toggle group to handle radio button selection changes
        reportToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == TotalbyType) {
                displayTotalAppointmentsByType();
            } else if (newValue == TotalbyMonth) {
                displayTotalAppointmentsByMonth();
            } else if (newValue == AppointmentbyCountry) {
                displayAppointmentsByCountry();
            } else if (newValue == Contacts) {
                displayContactSchedule();
            }
        });

        // Display total appointments by type initially
        displayTotalAppointmentsByType();
    }

    private void displayTotalAppointmentsByType() {
        ObservableList<Appointment> appointments = getTotalAppointmentsByType();
        Reportview.setItems(appointments);
    }

    private void displayTotalAppointmentsByMonth() {
        ObservableList<Appointment> appointments = getTotalAppointmentsByMonth();
        Reportview.setItems(appointments);
    }

    private void displayAppointmentsByCountry() {
        ObservableList<Appointment> appointments = getAppointmentsByCountry();
        Reportview.setItems(appointments);
    }

    private void displayContactSchedule() {
        ObservableList<Appointment> appointments = getContactSchedule();
        Reportview.setItems(appointments);
    }

    private ObservableList<Appointment> getTotalAppointmentsByType() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT Type, COUNT(*) AS Total FROM Appointment GROUP BY Type";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                int total = resultSet.getInt("Total");
                Appointment appointment = new Appointment(0, 0, "", "", type, "", "", "");
                appointments.add(appointment);

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return appointments;
    }

    private ObservableList<Appointment> getTotalAppointmentsByMonth() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT MONTHNAME(start) AS Month, COUNT(*) AS Total FROM Appointment GROUP BY MONTH(start)";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String month = resultSet.getString("Month");
                int total = resultSet.getInt("Total");
                Appointment appointment = new Appointment(0, 0, month, "", "", "", "", "");
                appointments.add(appointment);

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return appointments;
    }

    private ObservableList<Appointment> getAppointmentsByCountry() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT c.Country, COUNT(*) AS Total FROM Appointment a " +
                    "JOIN Customer c ON a.CustomerID = c.CustomerID " +
                    "GROUP BY c.Country";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String country = resultSet.getString("Country");
                int total = resultSet.getInt("Total");
                Appointment appointment = new Appointment(0, 0, "", "", "", "", country, "");
                appointments.add(appointment);

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return appointments;
    }

    private ObservableList<Appointment> getContactSchedule() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT a.AppointmentID, a.Title, a.Type, a.Description, a.Start, a.End, a.CustomerID, c.ContactName " +
                    "FROM Appointment a " +
                    "JOIN Customer c ON a.CustomerID = c.CustomerID " +
                    "ORDER BY c.ContactName";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("AppointmentID");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
            // Get start and end dates/times as needed
                int CustomerID = resultSet.getInt("CustomerID");
                String contactName = resultSet.getString("ContactName");
                Appointment appointment = new Appointment(appointmentID, CustomerID, "", "", title, description, "", contactName);
                appointments.add(appointment);

            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return appointments;
    }
}


