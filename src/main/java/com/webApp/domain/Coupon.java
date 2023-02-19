package com.webApp.domain;

import com.webApp.enums.Category;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "company_id")
    private long companyID;
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

    public Coupon(long id, long companyID, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        setStartDate(startDate);
        setEndDate(endDate);
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public Coupon(long companyID, Category category, String title, String description, String startDate, String endDate, int amount, double price, String image) {
        this.companyID = companyID;
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

    public long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
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

    @Override
    public String toString() {
        return "Beans.Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
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
}
