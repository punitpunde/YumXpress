
package yumxpress.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import yumxpress.dbutil.DBConnection;
import yumxpress.pojo.CompanyPojo;
import yumxpress.util.PasswordEncryption;

public class CompanyDao {
    
    public static String getNewId() throws SQLException{
        
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(COMPANY_ID) from COMPANIES");
        rs.next();
        String strid=rs.getString(1);
        if(strid==null){
            return "CMP-101";
        }
        strid=strid.substring(4);
        int id=Integer.parseInt(strid);
        id=id+1;
        return "CMP-"+id;
    }
    public static boolean addSeller(CompanyPojo cp) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("insert into companies values(?,?,?,?,?,?,?)");
         ps.setString(1,getNewId());
         ps.setString(2,cp.getCompanyName());
         ps.setString(3,cp.getOwnerName());
         String pwd=PasswordEncryption.getEncryptedPassword(cp.getPassword());
         ps.setString(4,pwd);
         ps.setString(5,"ACTIVE");
         ps.setString(6,cp.getEmailId());
         ps.setString(7,cp.getSecurityKey());
         return ps.executeUpdate()==1;
    }
    public static CompanyPojo validLoginDetail(String companyName,String pwd) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from companies where COMPANY_NAME=? and PASSWORD=? and STATUS='ACTIVE'");
         ps.setString(1,companyName);
         ps.setString(2,pwd);
         ResultSet rs=ps.executeQuery();
         CompanyPojo cp=null;
         if(rs.next()){
             cp=new CompanyPojo();
             cp.setCompanyId(rs.getString(1));
             cp.setCompanyName(rs.getString(2));
             cp.setOwnerName(rs.getString(3));
         }
         return cp;
    } 
    public static Map<String,String>getEmailCredintialByCompanyId(String companyId)throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select EMAILID ,SECURITY_KEY from companies where COMPANY_ID=? and STATUS='ACTIVE'");
         ps.setString(1, companyId);
         ResultSet rs=ps.executeQuery();
         Map<String,String>map=new HashMap<>();
         if(rs.next()){
             map.put("emailId",rs.getString(1));
             map.put("securityKey",rs.getString(2));
         }
         return map;
    }
    public static Map<String,String>getCompanyNameAndId()throws SQLException{
        Connection conn=DBConnection.getConnection();
         Statement st=conn.createStatement();
         ResultSet rs=st.executeQuery("select company_id ,company_name from companies where company_id in (select company_id from products) and status='ACTIVE'");
         Map<String,String>companies=new HashMap<>();
         while(rs.next()){
             companies.put(rs.getString(2),rs.getString(1));
         }
         return companies;
    }
    public static String getCompanyNameById(String id)throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement(" select company_name from companies where COMPANY_ID=? ");
         ps.setString(1,id);
         ResultSet rs=ps.executeQuery();
         String name=null;
         while(rs.next()){
             name=rs.getString(1);
         }
         return name;
    }
   
}

   

