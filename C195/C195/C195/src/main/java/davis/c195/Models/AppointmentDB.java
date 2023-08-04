package davis.c195.Models;

import davis.c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {
    public static ObservableList<Appointment> getMonthlyAppointments (int CustomerID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate Start = LocalDate.now();
        LocalDate End = LocalDate.now().plusMonths(1);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM Appointment WHERE CustomerID = '" + CustomerID + "' AND " +
                    "Start >= '" + Start + "' AND Start <= '" + End + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointment(results.getInt("AppointmentID"), results.getInt("CustomerID"), results.getString("Start"),
                        results.getString("End"), results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    public static ObservableList<Appointment> getWeeklyAppointments(int CustomerID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate Start = LocalDate.now();
        LocalDate End = LocalDate.now().plusWeeks(1);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM Appointment WHERE CustomerID = '" + CustomerID + "' AND " +
                    "start >= '" + Start + "' AND start <= '" + End + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointment(results.getInt("AppointmentID"), results.getInt("CustomerID"), results.getString("Start"),
                        results.getString("End"), results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        Appointment appointment;
        try {
            String query = "SELECT * FROM Appointments";
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointment = new Appointment(results.getInt("AppointmentID"), results.getInt("CustomerID"), results.getString("Start"),
                        results.getString("End"), results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact"));
                appointmentsObservableList.add(appointment);
            }
            statement.close();
            return appointmentsObservableList;

        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }



    public static boolean saveAppointment(int CustomerID, String Type, String Contact, String Location, String start, String End, String title, LocalDate startDate, LocalDate endDate) {
        String Description = Type.split(":")[1];
        String tsStart = createTimeStamp(startDate, start, Location, true);
        String tsEnd = createTimeStamp(endDate, End, Location, false);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "INSERT INTO Appointment SET CustomerID='" + CustomerID + "', Title='" + title + "', Description='" +
                    Description + "', Contact='" + Contact + "', Location='" + Location + "', Start='" + tsStart + "', End='" +
                    tsEnd + "', url='', createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy=''";
            int update = statement.executeUpdate(query);
            if (update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateAppointment(int appointmentID, String type, String contact, String location, String start, String end, LocalDate startDate, LocalDate endDate) {
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String tsStart = createTimeStamp(startDate, start, location, true);
        String tsEnd = createTimeStamp(endDate, end, location, false);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "UPDATE Appointment SET Title='" + title + "', Description='" + description + "', Contact='" +
                    contact + "', Location='" + location + "', Start='" + tsStart + "', End='" + tsEnd + "' WHERE " +
                    "AppointmentID='" + appointmentID + "'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }


    public static boolean overlappingAppointment(int appointmentID, String location, LocalDate startDate, String start) {
        String Start = createTimeStamp(startDate, start, location, true);
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "SELECT * FROM Appointment WHERE Start = '" + Start + "' AND Location = '" + location + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                if(results.getInt("AppointmentID") == appointmentID) {
                    statement.close();
                    return false;
                }
                statement.close();
                return true;
            } else {
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return true;
        }
    }

    public static boolean deleteAppointment(int appointmentID) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "DELETE FROM Appointment WHERE AppointmentID = " + appointmentID;
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    public static String createTimeStamp(LocalDate date, String time, String location, boolean startMode) {
        String h = time.split(":")[0];
        int rawH = Integer.parseInt(h);
        if(rawH < 9) {
            rawH += 12;
        }
        if(!startMode) {
            rawH += 1;
        }
        String rawD = String.format("%s %02d:%s", date, rawH, "00");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(rawD, df);
        ZoneId zid;
        if(location.equals("New Jersey")) {
            zid = ZoneId.of("America/New_Jersey");
        } else if(location.equals("Scotland")) {
            zid = ZoneId.of("United Kingdom/Scotland");
        } else {
            zid = ZoneId.of("Canada/Northwest Territories");
        }
        ZonedDateTime zdt = ldt.atZone(zid);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt);
        return ts.toString();
    }


}
