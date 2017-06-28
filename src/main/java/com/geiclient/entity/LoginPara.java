package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 登录获取token时，所需要的认证信息类
 *
 */
public class LoginPara {
    private String clientId;
    private String accid;
    private String password;
    private String captchaCode;
    private String captchaValue;

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getAccid() {
        return accid;
    }
    public void setAccid(String accid) {
        this.accid = accid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCaptchaCode() {
        return captchaCode;
    }
    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
    public String getCaptchaValue() {
        return captchaValue;
    }
    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }
}
