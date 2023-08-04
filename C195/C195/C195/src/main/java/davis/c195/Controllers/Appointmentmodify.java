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
 * Controller for Appointment Modify Screen
 * @author Brandon Davis
 */

public class Appointmentmodify implements Initializable {

    @FXML
    private ComboBox AppointmentType;

    @FXML
    private TextField AppointmentID;

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

    private int appointmentID;

    private final ObservableList<String> times = FXCollections.observableArrayList("8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM",
            "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM");

    private final ObservableList<String> type = FXCollections.observableArrayList("De-Briefing", "Planning Session", "After Action Review", "Consultation");

    private final ObservableList<String> contact = FXCollections.observableArrayList("Anika", "Daniel", "Li");

    public void setAppointmentInformation( int appointmentID, int customerID, int userID, String title, String description, String Type, String Contact, String location, String Start, String End, LocalDate startDate, LocalDate endDate) {

        /*
         Set appointment information
         */
        this.appointmentID=appointmentID;
        AppointmentID.setText(Integer.toString(appointmentID));
        CustomerID.setText(Integer.toString(customerID));
        UserID.setText(Integer.toString(userID));
        Title.setText(title);
        Description.setText(description);
        Location.setText(location);

        /*
         Set contact combo box
         */
        ContactBox.setValue(Contact);

        /*
         Set start time combo box
         */
        StartTime.setValue(Start);

        /*
         Set end time combo box
         */
        EndTime.setValue(End);

        /*
         Set appointment type combo box
         */
        AppointmentType.setValue(Type);

        /*
        Set appointment start date
         */
        StartDate.setValue(startDate);

        /*
         Set appointment end date
         */

        EndDate.setValue(endDate);
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



    @FXML
    public boolean updateButton(ActionEvent event) {

        String description = Description.getText();
        String location = Location.getText();
        String contact = (String) ContactBox.getValue();
        String start = (String) StartTime.getValue();
        String End = (String) EndTime.getValue();
        String title = Title.getText();
        String Type = (String) AppointmentType.getValue();
        LocalDate startDate = StartDate.getValue();
        LocalDate endDate = EndDate.getValue();

        if (description.isEmpty() || location.isEmpty() || contact == null || start == null || End == null || Type == null || title.isEmpty() || startDate == null || endDate == null) {

            return false;
        } else {
            LocalTime startTime = LocalTime.parse(start);
            LocalTime endTime = LocalTime.parse(End);

            if (startTime.isBefore(LocalTime.of(8,0)) || endTime.isAfter(LocalTime.of(22,0))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Appointment Time");
                alert.setContentText("Appointments can only be scheduled between 8 am and 10 pm.");
                alert.showAndWait();
                return false;
            }

            DayOfWeek startDayofWeek = startDate.getDayOfWeek();
            DayOfWeek endDayofWeek = endDate.getDayOfWeek();

            if (startDayofWeek == DayOfWeek.SATURDAY || startDayofWeek == DayOfWeek.SUNDAY || endDayofWeek == DayOfWeek.SATURDAY || endDayofWeek == DayOfWeek.SUNDAY) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText("Invalid Appointment Date");
              alert.setContentText("Appointments cannot be scheduled on weekends.");
              alert.showAndWait();
              return false;
            }

            if (overlappingAppointment(appointmentID, location, startDate, start)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Overlapping Appointments");
                alert.setContentText("There is an overlapping appointment at the selected time and location.");
                alert.showAndWait();
                return false;
            } else {

                return AppointmentDB.updateAppointment(appointmentID, Type, location, contact, start, End, startDate, endDate);
            }


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactBox.setItems(contact);
        StartTime.setItems(times);
        EndTime.setItems(times);
        AppointmentType.setItems(type);
    }
}

