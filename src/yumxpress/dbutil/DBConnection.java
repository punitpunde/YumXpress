
package yumxpress.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class DBConnection {
    private static Connection conn;
    static{
        try{
        conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","yumXpress","punit");
            
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Problem in connectingto the DataBase","DB Problem",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    public static boolean closeConnection(){
        
          try{
              conn.close();
              
          }
          catch(SQLException ex){
              JOptionPane.showMessageDialog(null,"Problem in closing the DataBase connection","DB Problem",JOptionPane.INFORMATION_MESSAGE);
              return false;
          }
          return true;
      
    }
    public static void main(String[] args) {
        System.out.println(DBConnection.getConnection());
    }
}
