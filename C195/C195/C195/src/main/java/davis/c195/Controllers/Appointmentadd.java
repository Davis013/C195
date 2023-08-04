package davis.c195.Controllers;

import davis.c195.Models.AppointmentDB;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static davis.c195.Models.AppointmentDB.overlappingAppointment;


/**
 * Controller for Appointment Add Screen
 * @author Brandon Davis
 */

public class Appointmentadd implements Initializable {

    @FXML
    private Button AddButton;

    @FXML
    private ComboBox AppointmentType;

    @FXML
    private Button CancelButton;

    @FXML
    private ComboBox ContactBox;

    @FXML
    private TextField CustomerID;

    @FXML
    private TextField Description;

    @FXML
    private ComboBox EndTime;

    @FXML
    private TextField Location;

    @FXML
    private ComboBox StartTime;

    @FXML
    private TextField Title;

    @FXML
    private TextField UserID;

    @FXML
    private DatePicker StartDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private TextField AppointmentID;

    private final ObservableList<String> times = FXCollections.observableArrayList("8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM",
            "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM");

    private final ObservableList<String> type = FXCollections.observableArrayList("De-Briefing", "Planning Session", "After Action Review", "Consultation");

    private final ObservableList<String> contact = FXCollections.observableArrayList("Anika", "Daniel", "Li");

    @FXML
    void addButton(ActionEvent event) {
        int customerID = Integer.parseInt(CustomerID.getText());
        String description = Description.getText();
        String location = Location.getText();
        String contact = (String) ContactBox.getValue();
        String start = (String) StartTime.getValue();
        String end = (String) EndTime.getValue();
        String title = Title.getText();
        String type = (String) AppointmentType.getValue();
        LocalDate startDate = StartDate.getValue();
        LocalDate endDate = EndDate.getValue();

        // Check if appointment falls outside of company hours (8am - 10pm) or on weekends
        LocalTime startTime = LocalTime.parse(start);
        LocalTime endTime = LocalTime.parse(end);
        boolean isWeekend = startDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || startDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isOutsideCompanyHours = startTime.isBefore(LocalTime.of(8, 0)) || endTime.isAfter(LocalTime.of(22, 0));

        if (description.isEmpty() || location.isEmpty() || contact == null || start == null || end == null || type == null || title.isEmpty() || startDate == null || endDate == null) {
            // Display an error message indicating missing fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
        } else if (overlappingAppointment(-1, location, startDate, start)) {
            // Display an error message for overlapping appointments
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Overlapping Appointments");
            alert.setContentText("There is an overlapping appointment at the selected time and location.");
            alert.showAndWait();
        } else if (isWeekend || isOutsideCompanyHours) {
            // Display an error message for appointments outside of company hours or on weekends
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Appointment Time");
            alert.setContentText("Appointments can only be scheduled during company hours (8am - 10pm) on weekdays.");
            alert.showAndWait();
        } else {
            boolean success = AppointmentDB.saveAppointment(customerID, type, contact, location, start, end, title, startDate, endDate);
            if (success) {
                // Display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Appointment added successfully.");
                alert.showAndWait();
            }
        }
    }





    @FXML
    void cancelButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Appointmentmain.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactBox.setItems(contact);
        StartTime.setItems(times);
        EndTime.setItems(times);
        AppointmentType.setItems(type);
    }
}
