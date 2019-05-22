package info.biyesheji.sheji.entity;
import java.io.Serializable;
import java.util.Date;
/**
* SysLog.java
* @version 1.0.0
*/
public class SysLog implements Serializable {

    private Integer id;  
    private String action;  
    private String parameters;  
    private String res;  
    private String account;  
    private String ip;  
    private Date createTime;    

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }
    public void setAction(String action) { this.action = action; }
    public String getAction() { return this.action; }
    public void setParameters(String parameters) { this.parameters = parameters; }
    public String getParameters() { return this.parameters; }
    public void setRes(String res) { this.res = res; }
    public String getRes() { return this.res; }
    public void setAccount(String account) { this.account = account; }
    public String getAccount() { return this.account; }
    public void setIp(String ip) { this.ip = ip; }
    public String getIp() { return this.ip; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getCreateTime() { return this.createTime; }
}
