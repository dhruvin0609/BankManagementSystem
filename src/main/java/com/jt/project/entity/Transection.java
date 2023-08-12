package com.jt.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transection_id;
	private String transection_type;
	private int amount;
	public int getTransection_id() {
		return transection_id;
	}
	public void setTransection_id(int transection_id) {
		this.transection_id = transection_id;
	}
	public String getTransection_type() {
		return transection_type;
	}
	public void setTransection_type(String transection_type) {
		this.transection_type = transection_type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Transection [transection_id=" + transection_id + ", transection_type=" + transection_type + ", amount="
				+ amount + "]";
	}
	
}
