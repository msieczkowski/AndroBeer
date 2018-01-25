package com.example.lp.beer;

/**
 * Created by lp on 25/01/2018.
 */

public class Beer {
    private String id;
    private String name;
    private String degree;
    private String description;
    private String origin;
    private String price;
    private String type;
    private String imgUrl;

    public Beer(String id, String name, String degree, String description, String origin, String price, String type, String imgUrl) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.description = description;
        this.origin = origin;
        this.price = price;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
