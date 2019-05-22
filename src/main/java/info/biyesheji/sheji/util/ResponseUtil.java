package info.biyesheji.sheji.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static info.biyesheji.sheji.util.ErrorResultMessage.自定义异常提示code;

public class ResponseUtil implements Serializable {

    private static final int succStatus = 200;
    private static final int errorStatus = 500;
    private static final long serialVersionUID = -8090827123673073325L;


    private Object data;
    private Integer status;
    private String errorMessage;
    private Integer errorCode;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static final Object succ(Object data){
        ResponseUtil responseUtil =  new ResponseUtil();
        responseUtil.setData(data);
        responseUtil.setStatus(succStatus);
        return responseUtil;
    }
    public static final Object succ(){
        ResponseUtil responseUtil =  new ResponseUtil();
        responseUtil.setStatus(succStatus);
        return responseUtil;
    }

    public static final Object error(String errorMessage){
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatus(errorStatus);
        responseUtil.setErrorMessage(errorMessage);
        responseUtil.setErrorCode(自定义异常提示code);
        return responseUtil;
    }

    public static final Object error(ErrorResultMessage.ErrorMessage errorMessage){
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatus(errorStatus);
        responseUtil.setErrorMessage(errorMessage.getMessage());
        responseUtil.setErrorCode(errorMessage.getErrorCode());
        return responseUtil;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request){
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
                ip = request.getRemoteAddr();

        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
