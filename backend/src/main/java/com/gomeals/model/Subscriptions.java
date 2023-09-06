package com.gomeals.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "subscription")
public class Subscriptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sub_id;

	private int meals_remaining;

	private Date sub_date;

	@Column(name = "active_status")
	private int activeStatus;

	@Column(name = "cust_id")
	private int customerId;

	@Column(name = "sup_id")
	private int supplierId;

	private String status;

	@Transient
	private Customer customer;

	public int getSub_id() {
		return sub_id;
	}

	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}

	public int getMeals_remaining() {
		return meals_remaining;
	}

	public void setMeals_remaining(int meals_remaining) {
		this.meals_remaining = meals_remaining;
	}

	public Date getSub_date() {
		return sub_date;
	}

	public void setSub_date(Date sub_date) {
		this.sub_date = sub_date;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int active_status) {
		this.activeStatus = active_status;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int cust_id) {
		this.customerId = cust_id;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int sup_id) {
		this.supplierId = sup_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Subscriptions(int sub_id, int meals_remaining, Date sub_date, int activeStatus, int cust_id,
			int supplierId) {
		super();
		this.sub_id = sub_id;
		this.meals_remaining = meals_remaining;
		this.sub_date = sub_date;
		this.activeStatus = activeStatus;
		this.customerId = cust_id;
		this.supplierId = supplierId;
	}

	public Subscriptions() {
		super();
	}

}
