package davis.c195.Models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model for Appointment
 * @author Brandon Davis
 */
public class Appointment {

    private int AppointmentID;
    private int CustomerID;
    private String Start;
    private String End;
    private String Title;
    private String Description;
    private String Location;
    private String Contact;

    /**
     * Constructor for Appointment Information
     * @param AppointmentID
     * @param CustomerID
     * @param Start
     * @param End
     * @param Title
     * @param Description
     * @param Location
     * @param Contact
     */
    public Appointment(int AppointmentID,int CustomerID, String Start, String End, String Title, String Description, String Location, String Contact){
        this.AppointmentID=AppointmentID;
        this.CustomerID=CustomerID;
        this.Start=Start;
        this.End=End;
        this.Title=Title;
        this.Description=Description;
        this.Location=Location;
        this.Contact=Contact;
    }

    /**
     * Getter for AppointmentID
     * @return
     */
    public int getAppointmentID() {
        return AppointmentID;
    }

    /**
     * Setter for AppointmentID
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    /**
     * Getter for CustomerID
     * @return
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * Setter for CustomerID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    /**
     * Getter for Start
     * @return
     */
    public String getStart() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime utcDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        String UTCstartDateTime = utcDateTime.format(formatter);
        return UTCstartDateTime;
    }

    /**
     * Setter for Start
     * @param start
     */
    public void setStart(String start) {
        Start = start;
    }

    /**
     * Getter for End
     * @return
     */
    public String getEnd() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime utcDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        String UTCendDateTime = utcDateTime.format(formatter);
        return UTCendDateTime;
    }

    /**
     * Setter for End
     * @param end
     */
    public void setEnd(String end) {
        End = end;
    }

    /**
     * Getter for Title
     * @return
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Setter for Title
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Getter for Description
     * @return
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Setter for Description
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Getter for Location
     * @return
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Setter for Location
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Getter for Contact
     * @return
     */
    public String getContact() {
        return Contact;
    }

    /**
     * Setter for Contact
     * @param contact
     */
    public void setContact(String contact) {
        Contact = contact;
    }
}
