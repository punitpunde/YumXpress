
package yumxpress.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import yumxpress.dbutil.DBConnection;
import yumxpress.pojo.CompanyPojo;
import yumxpress.pojo.CustomerPojo;
import yumxpress.util.PasswordEncryption;


public class CustomerDao {
 public static String getNewId() throws SQLException{
        
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(CUSTOMER_ID) from CUSTOMERS");
        rs.next();
        String strid=rs.getString(1);
        if(strid==null){
            return "COS-101";
        }
        strid=strid.substring(4);
        int id=Integer.parseInt(strid);
        id=id+1;
        return "COS-"+id;
    }   
 public static boolean addCustomer(CustomerPojo cp) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("insert into customers values(?,?,?,?,?,?)");
         
         cp.setCustomerId(getNewId());
         ps.setString(2,cp.getCustomerId());
         ps.setString(1,cp.getCustomerName());
         ps.setString(3,cp.getMobileNo());
         System.err.println("inside"+cp.getMobileNo());
         ps.setString(4,cp.getEmailId());
         ps.setString(5,cp.getAddress());
         ps.setString(6,cp.getPassword());
         return ps.executeUpdate()==1;
    }
 public static CustomerPojo validLoginDetail(String emailId,String pwd) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from customers where emailid=? and password=? ");
         ps.setString(1,emailId);
         ps.setString(2,pwd);
         ResultSet rs=ps.executeQuery();
         CustomerPojo cp=null;
         if(rs.next()){
             cp=new CustomerPojo();
             cp.setCustomerId(rs.getString(2));
             cp.setCustomerName(rs.getString(1));
             cp.setEmailId(rs.getString(4));
         }
         return cp;
    } 
   public static boolean updateCustomer(CustomerPojo customer)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("update customers set customername=?,mobile_no=?,address=?,password=? where customer_id=?");
       ps.setString(1,customer.getCustomerName());
       ps.setString(2,customer.getMobileNo());
       ps.setString(3,customer.getAddress());
       String pass=customer.getPassword();
       System.out.println(pass);
       ps.setString(4,pass);
       ps.setString(5,customer.getCustomerId());
       
       return ps.executeUpdate()==1;
   }
    public static String getCustomerAddress(String id)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select ADDRESS from customers where customer_id=?");
       ps.setString(1,id);
       ResultSet rs=ps.executeQuery();
       String add=null;
       while(rs.next()){
           add=rs.getString(1);
       }
       return add;
   }
    public static CustomerPojo getCustmoerDetailById(String id)throws  SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select * from customers where CUSTOMER_ID=?");
       ps.setString(1, id);
       ResultSet rs=ps.executeQuery();
       CustomerPojo cp=new CustomerPojo();
       while(rs.next()){
           cp.setCustomerId(id);
           cp.setCustomerName(rs.getString("CUSTOMERNAME"));
           cp.setEmailId(rs.getString("EMAILID"));
           cp.setPassword((rs.getString("PASSWORD")));
           cp.setAddress(rs.getString("ADDRESS"));
           cp.setMobileNo(rs.getString("MOBILE_NO"));
       }
       return cp;
    }
   
}
