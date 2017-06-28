package com.geiclient.entity;

/**
 * Created by cfzhu on 2017/2/23.
 *
 * 管理员实体类
 *
 */
public class Administrator {
    private Long id;
    private String accid;
    private String password;

    public Administrator() {
    }

    public Administrator(String accid, String password) {
        this.accid = accid;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
