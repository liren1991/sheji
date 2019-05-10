package info.biyesheji.sheji.entity;
import java.io.Serializable;
import java.util.Date;
/**
* OrderItem.java
* @version 1.0.0
*/
public class OrderItem implements Serializable {

    private Integer id;  
    private Integer productId;  
    private Integer productType;  
    private Integer pirce;  
    private Integer finalPirce;  
    private Date createTime;    

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Integer getProductId() { return this.productId; }
    public void setProductType(Integer productType) { this.productType = productType; }
    public Integer getProductType() { return this.productType; }
    public void setPirce(Integer pirce) { this.pirce = pirce; }
    public Integer getPirce() { return this.pirce; }
    public void setFinalPirce(Integer finalPirce) { this.finalPirce = finalPirce; }
    public Integer getFinalPirce() { return this.finalPirce; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getCreateTime() { return this.createTime; }
}
