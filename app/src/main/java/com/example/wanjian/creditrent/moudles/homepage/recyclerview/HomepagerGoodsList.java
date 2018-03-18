package com.example.wanjian.creditrent.moudles.homepage.recyclerview;

/**
 * Created by wanjian on 2017/11/30.
 */

public class HomepagerGoodsList {


    private int avatar;
    private String name;
    private String price;
    private String description;

    public HomepagerGoodsList(int avatar,String name,String price,String description){
        this.avatar=avatar;
        this.name=name;
        this.price=price;
        this.description=description;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
