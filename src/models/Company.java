package com.webApp.models;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "companies")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
//    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    //@JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Coupon> coupons;

    public Company(){}
    public Company(long id, String name, String email, String password, Set<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }
    public Company(String name, String email, String password, Set<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
//    @JsonIgnore
    public String getEmail() {
        return email;
    }
//    @JsonProperty
    public void setEmail(String email) {this.email = email;}
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        if(coupons == null){
            coupons = new HashSet<>();
            return;
        }
        this.coupons.forEach(this::removeCoupon);
        coupons.forEach(this::addCoupon);
    }
    public void addCoupon(Coupon coupon){
        this.coupons.add(coupon);
        coupon.setCompany(this);
    }
    public void removeCoupon(Coupon coupon){
        this.coupons.remove(coupon);
        coupon.setCompany(null);
    }

    @Override
    public String toString() {
        return "beans.Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                // ", coupons=" + coupons +
                '}';
    }
    public void encodePasswordBeforePersist(){
        if(!this.password.startsWith("{bcrypt}"))
            this.password = "{bcrypt}" + new BCryptPasswordEncoder(10).encode(this.password);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name);
    }
}
