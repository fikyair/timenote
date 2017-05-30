package edu.ztone.timenote.po;

import java.io.Serializable;

public class User implements Serializable {
	private int userid;
	private String account, password,sex, province, city, photo;
	private String nickname;
	public User(int userid, String account, String password, String sex,
			String province, String city, String photo, String nickname) {
		super();
		this.userid = userid;
		this.account = account;
		this.password = password;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.photo = photo;
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", account=" + account
				+ ", password=" + password + ", sex=" + sex + ", province="
				+ province + ", city=" + city + ", photo=" + photo
				+ ", nickname=" + nickname + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}