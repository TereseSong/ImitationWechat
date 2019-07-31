package cn.wydx.domain;

import java.io.Serializable;

//javabean类
public class User implements Serializable{
	
	private String id;

	
	//不规范的命名方式：user_name,Username,uSername
	//规范：usernmae,userName
	private String username;
	private String password;
	private String number;
	private String classinfo;

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getClassinfo() {
		return classinfo;
	}
	public void setClassinfo(String classinfo) {
		this.classinfo = classinfo;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", number=" + number
				+ ", classinfo=" + classinfo + "]";
	}

	
	


}
