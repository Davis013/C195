package davis.c195;

import davis.c195.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application file
 * @author Brandon Davis
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScreen.fxml"));
        Parent root = loader.load();
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JDBC.openConnection();
launch (args);
        JDBC.closeConnection();
    }
}