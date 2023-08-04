package davis.c195.Controllers;

import davis.c195.Models.CustomerDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Customer Add Screen
 * @author Brandon Davis
 */


public class Customeradd implements Initializable {

    @FXML
    private TextField AddressText;

    @FXML
    private Button Cancelbutton;

    @FXML
    private ComboBox CityBox;

    @FXML
    private ComboBox CountryBox;

    @FXML
    private TextField NameText;

    @FXML
    private TextField PhoneText;

    @FXML
    private TextField PostalText;

    @FXML
    private Button Savebutton;

    private ObservableList<String> UKCities = FXCollections.observableArrayList("Northern Ireland", "Scotland", "Wales", "England");
    private ObservableList<String> CanadaCities = FXCollections.observableArrayList("Newfoundland and Labrador", "Yukon", "Nunavut", "Saskatchewan", "Ontario",
            "Quebec", "Prince Edward Island", "Nova Scotia", "New Brunswick", "Manitoba", "British Columbia", "Alberta", "Northwest Territories");
    private ObservableList<String> USCities = FXCollections.observableArrayList("Alabama", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia",
            "Florida", "Georgia", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
            "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgina", "Washington", "West Virgina", "Wisconsin", "Wyoming", "Hawaii", "Alaska");
    String[] CountryOptions = {"United States", "Canada", "United Kingdom"};

    @FXML
    public void setCountry() {
        ComboBox<String> CountryBox = new ComboBox<>(FXCollections.observableArrayList(CountryOptions));
        String selectedCountry = CountryBox.getValue();
        updateCityOptions(selectedCountry, CityBox);
    }

    @FXML
    public void setCity() {
        ComboBox<String> CityBox = new ComboBox<>();
    }

    private void updateCityOptions(String selectedCountry, ComboBox<String> CityBox) {
        CityBox.getItems().clear();
        switch (selectedCountry) {
            case "United States":
                CityBox.getItems().addAll(USCities);
                break;
            case "Canada":
                CityBox.getItems().addAll(CanadaCities);
                break;
            case "United Kingdom":
                CityBox.getItems().addAll(UKCities);
                break;
            default:
                break;
        }
    }

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Customermain.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public boolean saveButtonAction(ActionEvent event) {
        String CustomerName = NameText.getText();
        String Address = AddressText.getText();
        String Phone = PhoneText.getText();
        String CityID = (String) CityBox.getValue();
        String PostalCode = PostalText.getText();

        if (CustomerName.isEmpty() || Address.isEmpty() || Phone.isEmpty() || CityID == null || PostalCode.isEmpty()) {
            // Display an error message for missing fields
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return false;
        }
        else {
            return CustomerDB.saveCustomer(CustomerName, Address, Phone, CityID, PostalCode);
        }

}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CityBox.setItems(UKCities);
        CityBox.setItems(CanadaCities);
        CityBox.setItems(USCities);
    }
}

















