package com.jt.project.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	private String name;
	private String email;
	private long mobile_no;
	private String password;
	@OneToOne(cascade = {CascadeType.ALL})
	private Account account_no;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Transection> t;

	public List<Transection> getT() {
		return t;
	}

	public void setT(List<Transection> t) {
		this.t = t;
	}

	public Account getAccount_no() {
		return account_no;
	}

	public void setAccount_no(Account account_no) {
		this.account_no = account_no;
	}

	public User() {
		super();
	}

	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
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
	
	public long getMobile_no() {
		return mobile_no;
	}
	
	public void setMobile_no(long mobile_no) {
		this.mobile_no = mobile_no;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", email=" + email + ", mobile_no=" + mobile_no
				+ ", password=" + password + "]";
	}
	
	
}
