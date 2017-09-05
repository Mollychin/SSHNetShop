/**
 * 
 */
package com.codeagles.shop.user.vo;

/**
 * <p>Title: User</p>
 * <p>@function:</p>
 * @author Codeagles   ,
 * @date ÏÂÎç3:09:37

CREATE TABLE `user` (
		  `uid` INT(11) NOT NULL AUTO_INCREMENT,
		  `username` VARCHAR(255) DEFAULT NULL,
		  `password` VARCHAR(255) DEFAULT NULL,
		  `name` VARCHAR(255) DEFAULT NULL,
		  `email` VARCHAR(255) DEFAULT NULL,
		  `phone` VARCHAR(255) DEFAULT NULL,
		  `addr` VARCHAR(255) DEFAULT NULL,
		  `state` INT(11) DEFAULT NULL,
		  `code` VARCHAR(64) DEFAULT NULL,
		  PRIMARY KEY (`uid`)
		) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
*/
public class User {
	private int uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String addr;
	private String code;
	private int state;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
