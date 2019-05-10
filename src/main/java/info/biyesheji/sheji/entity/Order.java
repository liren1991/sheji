package info.biyesheji.sheji.entity;
import java.io.Serializable;
import java.util.Date;
/**
* Order.java
* @version 1.0.0
*/
public class Order implements Serializable {

    private Integer id;  
    private String reqIp;  
    private String macIp;  
    private String remark;  
    private String alipayAccount;  
    private String qqAccount;  
    private String weixinAccount;  
    private Date createTime;    

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }
    public void setReqIp(String reqIp) { this.reqIp = reqIp; }
    public String getReqIp() { return this.reqIp; }
    public void setMacIp(String macIp) { this.macIp = macIp; }
    public String getMacIp() { return this.macIp; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getRemark() { return this.remark; }
    public void setAlipayAccount(String alipayAccount) { this.alipayAccount = alipayAccount; }
    public String getAlipayAccount() { return this.alipayAccount; }
    public void setQqAccount(String qqAccount) { this.qqAccount = qqAccount; }
    public String getQqAccount() { return this.qqAccount; }
    public void setWeixinAccount(String weixinAccount) { this.weixinAccount = weixinAccount; }
    public String getWeixinAccount() { return this.weixinAccount; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getCreateTime() { return this.createTime; }
}
