package com.example.finanzas.domain;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String title;
    private String picURrl;
    private int review;
    private double score;
    private int numberInCart;
    private double price;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PopularDomain(String title, String picURrl, int review, double score, double price, String description) {
        this.title = title;
        this.picURrl = picURrl;
        this.review = review;
        this.score = score;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicURrl() {
        return picURrl;
    }

    public void setPicURrl(String picURrl) {
        this.picURrl = picURrl;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
