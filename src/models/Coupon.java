package com.webApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webApp.models.enums.Category;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "coupons")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    //@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    @Column(name = "category_id")
    private Category category;
    private String title;
    private String description;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private int amount;
    private double price;
    private String image;
    public Coupon(){}

    public Coupon(long id, Company company, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.id = id;
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public Coupon(Company company, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {this.description = description;}

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = java.sql.Date.valueOf(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = java.sql.Date.valueOf(endDate);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void reduceAmountByOne() {
        this.amount--;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "beans.Coupon{" +
                "id=" + id +
                ", company=" + company +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && company.equals(coupon.company) && category == coupon.category && title.equals(coupon.title) && description.equals(coupon.description) && startDate.equals(coupon.startDate) && endDate.equals(coupon.endDate) && image.equals(coupon.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, category, title, description, startDate, endDate, amount, price, image);
    }
}
