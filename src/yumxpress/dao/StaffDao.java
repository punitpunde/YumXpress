
package yumxpress.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import yumxpress.dbutil.DBConnection;
import yumxpress.pojo.CustomerPojo;
import yumxpress.pojo.StaffPojo;
import yumxpress.util.PasswordEncryption;


public class StaffDao {
    public static String getNewId() throws SQLException{
        
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(STAFF_ID) from STAFF");
        rs.next();
        String strid=rs.getString(1);
        if(strid==null){
            return "STF-101";
        }
        strid=strid.substring(4);
        int id=Integer.parseInt(strid);
        id=id+1;
        return "STF-"+id;
    }
     public static String addStaff(StaffPojo staff) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("insert into staff values(?,?,?,?,?)");
         staff.setStaffId(getNewId());
         ps.setString(1,staff.getStaffId());
         ps.setString(2,staff.getCompanyId());
         ps.setString(3,staff.getEmail());
         ps.setString(4,staff.getStaffName());
         ps.setString(5,staff.getPwd());
         return ps.executeUpdate()==1?staff.getStaffId():null;
    }
     public static List<String>getStaffIdByCompanyId(String companyId)throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select STAFF_ID from staff where COMPANY_ID=? order by Staff_ID");
         ps.setString(1, companyId);
         ResultSet rs=ps.executeQuery();
         List<String>staffId=new ArrayList<>();
         while(rs.next()){
             staffId.add(rs.getString(1));
         }
         return staffId;
     }
      public static String randomStaffIdByCompanyId(String companyId)throws SQLException{
         List<String>staff=getStaffIdByCompanyId(companyId);
         Random rand=new Random();
          System.out.println(staff.size());
         int id=rand.nextInt(staff.size());
         return staff.get(id);
     }
     public static StaffPojo getStaffDetailByStaffId(String staffId)throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from staff where STAFF_ID=?");
         ps.setString(1,staffId);
         ResultSet rs=ps.executeQuery();
         StaffPojo staff=new StaffPojo();
         while(rs.next()){
             staff.setEmail(rs.getString(3));
             staff.setStaffName(rs.getString(4));
             staff.setPwd(PasswordEncryption.getDecryptedPassword(rs.getString("PASSWORD")));
             staff.setStaffId(staffId);
         }
         return staff;
     }
     
     public static StaffPojo validLoginDetail(String emailId,String pwd) throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("select * from staff where email_id=? and password=? ");
         ps.setString(1,emailId);
         String epwd=PasswordEncryption.getEncryptedPassword(pwd);
         System.out.println(epwd);
         ps.setString(2,epwd);
         System.out.println("Password during login"+pwd);
         ResultSet rs=ps.executeQuery();
         StaffPojo staff=null;
         if(rs.next()){
             staff=new StaffPojo();
             staff.setStaffId(rs.getString(1));
             staff.setEmail(emailId);
             staff.setStaffName(rs.getString(4));
         }
         return staff;
    } 
     public static boolean updateStaffData(StaffPojo staff)throws SQLException{
         Connection conn=DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("update staff set email_id=?,staff_name=?,password=? where staff_id=?");
         ps.setString(1,staff.getEmail());
         System.err.println(staff.getPwd());
         ps.setString(2,staff.getStaffName());
         ps.setString(3,PasswordEncryption.getEncryptedPassword(staff.getPwd()));
         ps.setString(4,staff.getStaffId());
         System.err.println(staff.getStaffId());
         return ps.executeUpdate()==1;
     }
}
