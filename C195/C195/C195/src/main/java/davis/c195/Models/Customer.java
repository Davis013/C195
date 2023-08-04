package davis.c195.Models;


/**
 * Model for Customers information
 *
 * @author Brandon Davis
 */
public class Customer {
    private int CustomerID;
    private String CustomerName;
    private String Address;
    private String CityID;
    private String Phone;
    private String PostalCode;


    /**
     * Constuctor for Customer Information
     * @param CustomerID
     * @param CustomerName
     * @param Address
     * @param CityID
     * @param Phone
     * @param PostalCode
     */
    public Customer(int CustomerID, String CustomerName, String Address, String CityID, String Phone, String PostalCode){
    this.CustomerID=CustomerID;
    this.CustomerName=CustomerName;
    this.Address=Address;
    this.CityID=CityID;
    this.Phone=Phone;
    this.PostalCode=PostalCode;
}


    /**
     * Getter for Customer ID
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
     * Getter for CustomerName
     * @return
     */
    public String getCustomerName() {
        return CustomerName;
    }

    /**
     * Setter for CustomerName
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    /**
     * Getter for Address
     * @return
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Setter for Address
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Getter for City
     * @return
     */
    public String getCityID() {
        return CityID;
    }

    /**
     * Setter for City
     * @param city
     */
    public void setCityID(String city) {
        CityID = city;
    }

    /**
     * Getter for Phone
     * @return
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Setter for Phone
     * @param phone
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * Getter for PostalCode
     * @return
     */

    public String getPostalCode() {
        return PostalCode;
    }

    /**
     * Setter for PostalCode
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }
}

