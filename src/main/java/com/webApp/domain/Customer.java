package com.webApp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
//    @JoinTable(name = "customers_coupons",
//            joinColumns = @JoinColumn(name = "customer_id"),
//            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

    public Customer() {}

    public Customer(long id, String firstName, String lastName, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }
    public Customer(String firstName, String lastName, String email, String password, ArrayList<Coupon> coupons) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if(coupons == null)
            coupons = new ArrayList<Coupon>();
        this.coupons = coupons;
    }
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<Coupon>();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
    public void addToCouponsList(Coupon coupon){
        this.coupons.add(coupon);
    }

    @Override
    public String toString() {
        return "Beans.Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
