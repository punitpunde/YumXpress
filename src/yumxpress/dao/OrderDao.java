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
import yumxpress.pojo.CartPojo;
import yumxpress.pojo.CustomerPojo;
import yumxpress.pojo.OrderPojo;
import yumxpress.pojo.PlaceOrderPojo;
import yumxpress.pojo.ProductPojo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrderDao {

    public static String getNewId() throws SQLException {

        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(ORDER_ID) from orders");
        rs.next();
        String strid = rs.getString(1);
        if (strid == null) {
            return "ORD-101";
        }
        strid = strid.substring(4);
        int id = Integer.parseInt(strid);
        id = id + 1;
        return "ORD-" + id;
    }
    public static String getNewCartId()throws SQLException{
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(ORDER_ID) from orders where STATUS='CART'");
        rs.next();
        String strid = rs.getString(1);
        if (strid == null) {
            return "CART-101";
        }
        strid = strid.substring(5);
        int id = Integer.parseInt(strid);
        id = id + 1;
        return "CART-" + id;
    }

    public static boolean placeOrder(PlaceOrderPojo placeOrderPojo) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?)");
        placeOrderPojo.setOrderId(getNewId());
        System.out.print(placeOrderPojo.getOrderId());
        ps.setString(1, placeOrderPojo.getOrderId());
        ps.setString(2, placeOrderPojo.getProductId());
        ps.setString(3, placeOrderPojo.getCustomerId());
        ps.setString(4, placeOrderPojo.getStaffId());
        ps.setString(5, "");
        ps.setString(6, "ORDERED");
        Random random = new Random();
        int otp = random.nextInt(10000);
        ps.setInt(7, otp);
        ps.setString(8, placeOrderPojo.getCompanyId());
        return ps.executeUpdate() == 1;
    }
    public static boolean daleteCart(String id)throws SQLException{
         Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from orders where order_id=?");
        ps.setString(1, id);
       return 1 == ps.executeUpdate();
    }

    public static OrderPojo getOrderDetailByOrderId(String orderid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT C.ADDRESS,S.STAFF_NAME,O.OTP,COM.COMPANY_NAME,COM.EMAILID,\n"
                + "                P.PRODUCT_NAME,P.PRODUCT_PRICE from ORDERS O \n"
                + "                join CUSTOMERS C on C.CUSTOMER_ID=O.CUSTOMER_ID\n"
                + "                join PRODUCTS P on P.PRODUCT_ID=O.PRODUCT_ID\n"
                + "                join STAFF S on O.STAFF_ID=S.STAFF_ID\n"
                + "                JOIN COMPANIES COM on  O.COMPANY_ID=COM.COMPANY_ID where order_id=?");
        ps.setString(1, orderid);
        ResultSet rs = ps.executeQuery();
        OrderPojo orderPojo = null;
        if (rs.next()) {
            orderPojo=new OrderPojo();
            orderPojo.setCompanyName(rs.getString("company_name"));
            orderPojo.setCompanyEmailId(rs.getString(5));
            orderPojo.setCustomerAddress(rs.getString(1));
            orderPojo.setDeliveryStaffName(rs.getString(2));
            orderPojo.setOtp(rs.getInt(3));
            orderPojo.setProductPrice(rs.getDouble(7));
            orderPojo.setProductName(rs.getString(6));

        }
        return orderPojo;
    }
    

    public static List<OrderPojo> getAllOrderedPojoByStaffId(String staffid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select O.ORDER_ID,P.PRODUCT_NAME,P.PRODUCT_PRICE,C.CUSTOMERNAME,\n"
                + "C.ADDRESS,C.MOBILE_NO,O.OTP from\n"
                + " ORDERS O JOIN PRODUCTS P ON O.PRODUCT_ID=P.PRODUCT_ID join \n"
                + "CUSTOMERS C on C.CUSTOMER_ID=O.CUSTOMER_ID where staff_id=? and status='ORDERED' ");
        ps.setString(1, staffid);
        ResultSet rs = ps.executeQuery();
        List<OrderPojo> list =new ArrayList<>();
        
        while (rs.next()) {
            OrderPojo order = new OrderPojo();
            order.setCustomerName(rs.getString(4));
            order.setCustomerAddress(rs.getString(5));
            order.setCustomerPhoneNo(rs.getString(6));
            order.setOtp(rs.getInt(7));
            order.setProductName(rs.getString(2));
            order.setProductPrice(rs.getDouble(3));
            order.setOrderId(rs.getString(1));
            list.add(order);
        }
        return list;
    }
   public static boolean updateOrderStatus(String orderId)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("update ORDERS set STATUS='DELIVERED'where order_id=?");
       ps.setString(1, orderId);
      return ps.executeUpdate()==1;
   } 
   
   public static Map<String,ProductPojo>getCartProductPojos(String custId)throws SQLException,IOException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select product_id,order_id from orders where status='CART' and customer_id=?");
       ps.setString(1, custId);
       ResultSet rs=ps.executeQuery();
       Map<String,ProductPojo>list=new HashMap<>();
       while(rs.next()){
             list.put(rs.getString(2),ProductDao.getProductdetailByProductId(rs.getString(1)));
//           
       }
       return list;
   }
   public static String addToCart(CartPojo pojo)throws SQLException{
       String message=null;
       if(checkCart(pojo.getProductId(),pojo.getCustomerId())){
          message="Already present in the cart";
       }
       else{
           message="Added to the carts";
       }
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?)");
      pojo.setCartId(getNewCartId());
      ps.setString(1,pojo.getCartId());
      ps.setString(8,pojo.getCompanyId());
      ps.setString(3,pojo.getCustomerId());
      ps.setString(2,pojo.getProductId());
      ps.setString(4,"");
      ps.setString(5,"");
      ps.setString(6,"CART");
      ps.setInt(7,0);
      ps.executeUpdate();
      return message;
   }
   public static boolean checkCart(String pId,String custId)throws SQLException{
         Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("delete from orders where customer_id=? and product_id=? and status='CART'");
      ps.setString(2, pId);
      ps.setString(1,custId);
      int rs=ps.executeUpdate();
       System.out.println(pId+" "+custId);
//      while(rs.next()){
//          return true;
//      }
       
      return rs==1;
    }
   public static List<OrderPojo>getOrderHistoryDetail(String custId)throws SQLException,IOException{
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("select order_id,product_id,company_id ,review from orders where customer_id=? and status='DELIVERED'");
      ps.setString(1, custId);
      ResultSet rs=ps.executeQuery();
      List<OrderPojo>list=new ArrayList<>();
      while(rs.next()){
          OrderPojo pojo=new OrderPojo();
          pojo.setOrderId(rs.getString("order_id"));
          ProductPojo productPojo=ProductDao.getProductdetailByProductId(rs.getString("product_id"));
          pojo.setProductPrice(productPojo.getProductPrice());
          pojo.setProductName(productPojo.getProductName());
          pojo.setReview(rs.getString("review"));
          pojo.setCompanyName(CompanyDao.getCompanyNameById(rs.getString("company_id")));
          list.add(pojo);
      }
      return list;
   }
   public static List<OrderPojo>getOrderHistory(String id)throws SQLException,IOException{
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("select customer_id,STATUS,order_id,product_id,review from orders where company_id=? and status='DELIVERED'");
      ps.setString(1, id);
      ResultSet rs=ps.executeQuery();
      List<OrderPojo>list=new ArrayList<>();
      while(rs.next()){
          OrderPojo pojo=new OrderPojo();
          pojo.setStatus(rs.getString("STATUS"));
          pojo.setOrderId(rs.getString("order_id"));
          ProductPojo productPojo=ProductDao.getProductdetailByProductId(rs.getString("product_id"));
          pojo.setProductName(productPojo.getProductName());
          pojo.setReview(rs.getString("review"));
          CustomerPojo cust=CustomerDao.getCustmoerDetailById(rs.getString("customer_id"));
          pojo.setCustomerName(cust.getCustomerName());
          pojo.setCustomerPhoneNo(cust.getMobileNo());
          list.add(pojo);
      }
      return list;
   }
   public static List<OrderPojo>getOrderHistoryForStaff(String staffId)throws SQLException,IOException{
      Connection conn=DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("select order_id,product_id,customer_id ,review from orders where staff_id=? and status='DELIVERED'");
      ps.setString(1, staffId);
      ResultSet rs=ps.executeQuery();
      List<OrderPojo>list=new ArrayList<>();
      while(rs.next()){
          OrderPojo pojo=new OrderPojo();
          pojo.setOrderId(rs.getString("order_id"));
          ProductPojo productPojo=ProductDao.getProductdetailByProductId(rs.getString("product_id"));
          pojo.setProductPrice(productPojo.getProductPrice());
          pojo.setProductName(productPojo.getProductName());
          pojo.setReview(rs.getString("review"));
          pojo.setCustomerAddress(CustomerDao.getCustomerAddress(rs.getString("customer_id")));
          list.add(pojo);
      }
      return list;
   }
   public static Map<String,String> getDetailToCancelOrder(String id)throws SQLException,IOException{
       Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("select order_id,product_id  from orders where customer_id=? and status='ORDERED'");
       Map<String,String>map=new HashMap<>();
       ps.setString(1, id);
       ResultSet rs=ps.executeQuery();
       while(rs.next()){
           ProductPojo pojo=ProductDao.getProductdetailByProductId(rs.getString("product_id"));
           map.put(rs.getString(1),pojo.getProductName());
       }
       return map;
   }
   public static boolean cancelOrder(String id)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("update orders set status='CANCELED'where order_id=?");
       ps.setString(1, id);
       return ps.executeUpdate()==1;
       
   }
   public static boolean addReview(String review,String orderId)throws SQLException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("update orders set review= ? where order_id=?");
       ps.setString(1, review);
       ps.setString(2, orderId);
       return ps.executeUpdate()==1;
   }
   
    
    
    
}
