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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Customer Updatescreen
 * @author Brandon Davis
 */

public class Customerupdate implements Initializable {

    @FXML
    private TextField AddressText;

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
    private TextField CustomerIDText;


    public void setCustomerInformation (int CustomerID, String CustomerName, String Phone, String Address, String CityID, String PostalCode, String CountryID){

        /*
          Lambda function 2, all data is encapsulated in Runnable lambda function. This lambda function will capture the necessary values and perform the UI updates.
         */
            Runnable setCustomerID = () ->CustomerIDText.setText(Integer.toString(CustomerID));
            Runnable setNameText = () -> NameText.setText(CustomerName);
            Runnable setPhoneText = () -> PhoneText.setText(Phone);
            Runnable setAddressText = () -> AddressText.setText(Address);
            Runnable setCityValue = () -> CityBox.setValue(CityID);
            Runnable setPostalText = () -> PostalText.setText(PostalCode);
            Runnable setCountryValue = () -> CountryBox.setValue(CountryID);

        /*
          Executing the Lambda function
         */
            setCustomerID.run();
            setNameText.run();
            setPhoneText.run();
            setAddressText.run();
            setCityValue.run();
            setPostalText.run();
            setCountryValue.run();

    }
    private ObservableList<String> UKCities = FXCollections.observableArrayList( "Northern Ireland", "Scotland", "Wales", "England");
    private ObservableList<String> CanadaCities= FXCollections.observableArrayList("Newfoundland and Labrador", "Yukon", "Nunavut", "Saskatchewan", "Ontario",
            "Quebec", "Prince Edward Island", "Nova Scotia", "New Brunswick", "Manitoba", "British Columbia", "Alberta", "Northwest Territories");
    private ObservableList<String> USCities= FXCollections.observableArrayList("Alabama", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia",
            "Florida", "Georgia", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
            "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgina", "Washington", "West Virgina", "Wisconsin", "Wyoming", "Hawaii", "Alaska");

    String[] CountryOptions = {"United States", "Canada", "United Kingdom"};

    @FXML
    void cancelButtonAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader (getClass().getResource("/Customermain.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void setCountry(ActionEvent event) {
        ComboBox<String> CountryBox = new ComboBox<>(FXCollections.observableArrayList(CountryOptions));
        String selectedCountry = CountryBox.getValue();
        updateCityOptions(selectedCountry,CityBox);
    }


    @FXML
    public boolean saveButtonAction(ActionEvent event)  {
        String customerID = CustomerIDText.getText();
        String CustomerName = NameText.getText();
        String Address = AddressText.getText();
        String Phone = PhoneText.getText();
        String CityID = (String) CityBox.getValue();
        String PostalCode = PostalText.getText();

        if (customerID.isEmpty() || CustomerName.isEmpty() || Address.isEmpty() || Phone.isEmpty()  || CityID == null || PostalCode.isEmpty()) {

            return false;
        }

        else {
            try {
                    int CustomerID = Integer.parseInt(customerID);

                return CustomerDB.updateCustomer(CustomerID, CustomerName, Address, Phone, CityID, PostalCode);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    void setCity(ActionEvent event) { ComboBox<String> CityBox = new ComboBox<>();

    }
    private void updateCityOptions(String selectedCountry,ComboBox<String> CityBox) {
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
                break;}
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CityBox.setItems(UKCities);
        CityBox.setItems(CanadaCities);
        CityBox.setItems(USCities);
    }
}


