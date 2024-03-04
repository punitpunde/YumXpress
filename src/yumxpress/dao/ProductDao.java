
package yumxpress.dao;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import yumxpress.dbutil.DBConnection;
import yumxpress.pojo.ProductPojo;


public class ProductDao {
    public static String getNewId() throws SQLException,IOException{
        
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select max(PRODUCT_ID) from PRODUCTS");
        rs.next();
        String strid=rs.getString(1);
        if(strid==null){
            return "PD-101";
        }
        strid=strid.substring(3);
        int id=Integer.parseInt(strid);
        id=id+1;
        return "PD-"+id;
    }
    public static boolean addProduct(ProductPojo product ) throws SQLException,IOException{
        BufferedImage bufferImage=new BufferedImage(product.getProductimage().getWidth(null),product.getProductimage().getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics gr=bufferImage.getGraphics();
        gr.drawImage(product.getProductimage(),0,0,null);  
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(bufferImage,product.getProductImageformat(),baos);
        byte[]imageData=baos.toByteArray();
        ByteArrayInputStream bais=new ByteArrayInputStream(imageData);
        
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into PRODUCTS values(?,?,?,?,?)");
        ps.setString(1,getNewId());
        ps.setString(2,product.getCompanyId());
        ps.setString(3,product.getProductName());
        ps.setDouble(4,product.getProductPrice());
        ps.setBinaryStream(5,bais,imageData.length);
        System.err.println("hw;;"); 
        return ps.executeUpdate()==1;
    }
    public static Map<String,ProductPojo>getProductDetailsByCompanyId(String companyId)throws IOException,SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from products where COMPANY_ID=?");
        ps.setString(1, companyId);
        ResultSet rs=ps.executeQuery();
        Map<String,ProductPojo>productDetails=new HashMap<>();
        while(rs.next()){
            ProductPojo product=new ProductPojo();
            product.setProductName(rs.getString(3));
            product.setProductPrice(rs.getDouble(4));
            InputStream inputStream=rs.getBinaryStream("product_image");
            BufferedImage bufferedImage=ImageIO.read(inputStream);
            Image image=bufferedImage;
            product.setProductimage(image);
            productDetails.put(product.getProductName(),product);
        }
        return productDetails;
    }
    public static List<ProductPojo>getProductsDetail(String id)throws SQLException,IOException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null; 
        boolean result=id.equalsIgnoreCase("ALL");
        if(result){
            ps=conn.prepareStatement("select * from products order by product_id");
            rs=ps.executeQuery();
        }
        else{
            ps=conn.prepareStatement("select * from products  where company_id=? and company_id in (select company_id from companies where status='ACTIVE')order by product_id");
            ps.setString(1,id);
            rs=ps.executeQuery();
        }
        List<ProductPojo>productPojos=new ArrayList<>();
       while(rs.next()){
            ProductPojo product=new ProductPojo();
            product.setProductName(rs.getString(3));
            product.setProductPrice(rs.getDouble(4));
            InputStream inputStream=rs.getBinaryStream("product_image");
            BufferedImage bufferedImage=ImageIO.read(inputStream);
            Image image=bufferedImage;
            product.setProductimage(image);
            product.setCompanyId(rs.getString("COMPANY_ID"));
            product.setProductId(rs.getString("product_id"));
            productPojos.add(product);
       }
       return productPojos;
    }
    public static ProductPojo getProductdetailByProductId(String id)throws SQLException,IOException{
       Connection conn=DBConnection.getConnection();
       PreparedStatement ps=conn.prepareStatement("select product_name,product_price,PRODUCT_IMAGE from products where product_id=?");
       ps.setString(1, id);
       ResultSet rs=ps.executeQuery();
       ProductPojo pojo=new ProductPojo();
       while(rs.next()){
           pojo.setProductName(rs.getString(1));
           pojo.setProductPrice(rs.getDouble(2));
           InputStream inputStream=rs.getBinaryStream("product_image");
            BufferedImage bufferedImage=ImageIO.read(inputStream);
            Image image=bufferedImage;
            pojo.setProductimage(image);
           pojo.setProductId(id);
       }
       return pojo;
    }
}
