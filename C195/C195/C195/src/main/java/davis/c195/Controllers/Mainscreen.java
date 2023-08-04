package davis.c195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Mainscreen
 * @author Brandon Davis
 */

public class Mainscreen implements Initializable {

    @FXML
    void Appointmentmanagerbutton(ActionEvent event) throws IOException{

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Appointmentmain.fxml"));
        Parent root = loader.load();
        stage.setTitle("Appointments Mainscreen");
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    void CustomermanagerButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Customermain.fxml"));
        Parent root = loader.load();
        stage.setTitle("Customer Mainscreen");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void Exitbutton(ActionEvent event) {

        System.exit(0);

    }

    @FXML
    void loginManagerButton (ActionEvent event){
        File file = new File("log.txt");
        try {
            if (file.exists()) {
                Runtime.getRuntime().exec("notepad " + "log.txt");
            } else {
                System.out.println("File not found.");}

        } catch (IOException e) {

            System.out.println(" Error Opening Log File: " + e.getMessage());
        }
            }




    @FXML
    void reportsManagerButton (ActionEvent event) throws IOException{

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reports.fxml"));
        Parent root = loader.load();
        stage.setTitle("Customer Reports");
        stage.setScene(new Scene(root));
        stage.show();



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        }
    }
