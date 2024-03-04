
package yumxpress.pojo;


public class StaffPojo {
    private String staffId;
    private String staffName;
    private String pwd;
    private String companyId;
    private String email;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StaffPojo(String staffId, String staffName, String pwd, String companyId, String email) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.pwd = pwd;
        this.companyId = companyId;
        this.email = email;
    }

    public StaffPojo() {
    }
    
}
