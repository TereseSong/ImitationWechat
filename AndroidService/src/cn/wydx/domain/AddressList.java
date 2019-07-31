package cn.wydx.domain;

import java.io.Serializable;
/**
 * 映射多表查询的结果，和注册的表不一样
 * @author newuser
 *
 */
public class AddressList implements Serializable{
    private String id;
    private String username;
    private String adname;
    private String phone;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdname() {
        return adname;
    }

    public void setAdname(String adname) {
        this.adname = adname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AddressList{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", adname='" + adname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
