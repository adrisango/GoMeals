package com.gomeals.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cust_id;

    private String cust_fname;
    private String cust_lname;
    private String cust_address;
    private String cust_email;
    private String cust_card_details;
    private String cust_contact_number;

    private String cust_password;
    @Transient
    private List<Supplier> suppliers = new ArrayList<>();
    @Transient
    private List<Subscriptions> subscriptions = new ArrayList<>();


    public Customer() {

    }

    public Customer(int cust_id, String cust_fname, String cust_lname, String cust_address, String cust_email, String cust_card_details, String cust_contact_number, String cust_password) {
        this.cust_id = cust_id;
        this.cust_fname = cust_fname;
        this.cust_lname = cust_lname;
        this.cust_address = cust_address;
        this.cust_email = cust_email;
        this.cust_card_details = cust_card_details;
        this.cust_contact_number = cust_contact_number;
        this.cust_password = cust_password;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_fname() {
        return cust_fname;
    }

    public void setCust_fname(String cust_fname) {
        this.cust_fname = cust_fname;
    }

    public String getCust_lname() {
        return cust_lname;
    }

    public void setCust_lname(String cust_lname) {
        this.cust_lname = cust_lname;
    }

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public String getCust_card_details() {
        return cust_card_details;
    }

    public void setCust_card_details(String cust_card_details) {
        this.cust_card_details = cust_card_details;
    }

    public String getCust_contact_number() {
        return cust_contact_number;
    }

    public void setCust_contact_number(String cust_contact_number) {
        this.cust_contact_number = cust_contact_number;
    }

    public String getCust_password() {
        return cust_password;
    }

    public void setCust_password(String cust_password) {
        this.cust_password = cust_password;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Subscriptions> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscriptions> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
