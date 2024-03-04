/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumxpress.pojo;

import java.sql.Connection;
import yumxpress.dbutil.DBConnection;

/**
 *
 * @author HP
 */
public class CompanyPojo {
    private String companyName;
    private String ownerName;
    private String companyId;
    private String password;
    private String emailId;
    private String securityKey;
    private String status;

    public CompanyPojo(String companyName, String ownerName, String password, String emailId, String securityKey) {
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.password = password;
        this.emailId = emailId;
        this.securityKey = securityKey;
    }

    public CompanyPojo() {
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }
    
    
}
