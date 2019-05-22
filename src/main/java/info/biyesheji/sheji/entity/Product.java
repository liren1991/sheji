package info.biyesheji.sheji.entity;
import java.io.Serializable;
import java.util.Date;
/**
* Product.java
* @version 1.0.0
*/
public class Product implements Serializable {

    private Integer id;  
    private String name;  
    private String remark;  
    private Integer downloadNum;  
    private Integer startNum;  
    private Date createTime;    
    private String url;  
    private String sourceUrl;  
    private Integer price;
    private Double size;  
    private Integer type;  //  type 1 java项目 2 为PDF文件
    private Integer status;  //  0 已下架 1 上架

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getRemark() { return this.remark; }
    public void setDownloadNum(Integer downloadNum) { this.downloadNum = downloadNum; }
    public Integer getDownloadNum() { return this.downloadNum; }
    public void setStartNum(Integer startNum) { this.startNum = startNum; }
    public Integer getStartNum() { return this.startNum; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getCreateTime() { return this.createTime; }
    public void setUrl(String url) { this.url = url; }
    public String getUrl() { return this.url; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    public String getSourceUrl() { return this.sourceUrl; }
    public void setPrice(Integer price) { this.price = price; }
    public Integer getPrice() { return this.price; }
    public void setSize(Double size) { this.size = size; }
    public Double getSize() { return this.size; }
    public void setType(Integer type) { this.type = type; }
    public Integer getType() { return this.type; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getStatus() { return this.status; }
}
