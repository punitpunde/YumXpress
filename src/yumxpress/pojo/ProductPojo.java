
package yumxpress.pojo;

import java.awt.Image;


public class ProductPojo {
    private String productId;
    private String productName;
    private Image productimage;
    private String productImageformat;
    private String companyId;
    private  double productPrice;

    public ProductPojo(String nextProductId, String productName, Image productimage, String productImageformat, String companyId, double productPrice) {
        this.productId = nextProductId;
        this.productName = productName;
        this.productimage = productimage;
        this.productImageformat = productImageformat;
        this.companyId = companyId;
        this.productPrice = productPrice;
    }

    public ProductPojo() {
    }
    

    public String getProductId() {
        return productId;
    }

    public void setProductId(String nextProductId) {
        this.productId = nextProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Image getProductimage() {
        return productimage;
    }

    public void setProductimage(Image productimage) {
        this.productimage = productimage;
    }

    public String getProductImageformat() {
        return productImageformat;
    }

    public void setProductImageformat(String productImageformat) {
        this.productImageformat = productImageformat;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return this.productName+this.companyId;
    }
    
    
    
    
    
}
