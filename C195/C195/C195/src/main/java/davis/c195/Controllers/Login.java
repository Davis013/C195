package davis.c195.Controllers;


import davis.c195.Models.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for login
 * @author Brandon Davis
 */

public class Login implements Initializable {

    @FXML
    private Button ExitButton;

    @FXML
    private TextArea Locationbox;

    @FXML
    private Button LoginButton;

    @FXML
    private Text PasswordLabel;

    @FXML
    private PasswordField PasswordText;

    @FXML
    private Text UsernameLabel;

    @FXML
    private TextField UsernameText;

    @FXML
    void ExitButton(ActionEvent event) {

        System.exit(0);

    }

    @FXML
    void LoginButton(ActionEvent event) throws IOException {

        String Username = UsernameText.getText();
        String Password = PasswordText.getText();
        boolean ValidUser= UserDB.login(Username, Password);
        if (ValidUser) {
            ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Mainscreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Incorrect Username or Password");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        ZoneId Zone = ZoneId.systemDefault();
        Locationbox.setText(locale.getDisplayCountry());
        Locationbox.setText(String.valueOf(Zone));
        resourceBundle = ResourceBundle.getBundle("languages/login", locale);
        UsernameLabel.setText(resourceBundle.getString("Username"));
        PasswordLabel.setText(resourceBundle.getString("Password"));
        LoginButton.setText(resourceBundle.getString("Login"));
        ExitButton.setText(resourceBundle.getString("Exit"));
        Locationbox.setText(resourceBundle.getString("Location"));
    }
}

