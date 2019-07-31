package cn.wydx.domain;

import java.io.Serializable;

/**
 * 通讯录的javabean类
 * 
 * @author newuser
 *
 */
public class Address implements Serializable {

	private String adname;
	private String phone;
	private String email;
	private String adnumber;

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

	public String getAdnumber() {
		return adnumber;
	}

	public void setAdnumber(String adnumber) {
		this.adnumber = adnumber;
	}

	@Override
	public String toString() {
		return "Address [adname=" + adname + ", phone=" + phone + ", email=" + email + ", adnumber=" + adnumber + "]";
	}

}
