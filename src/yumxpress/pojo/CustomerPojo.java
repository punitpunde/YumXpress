
package yumxpress.pojo;


public class CustomerPojo {

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    private String customerId;
    private String customerName;
    private String password;
    private String emailId;
    private String mobileNo;
    private String address;

    public CustomerPojo(String customerId, String customerName, String password, String emailId, String mobileNo, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.address = address;
    }

    public CustomerPojo() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
