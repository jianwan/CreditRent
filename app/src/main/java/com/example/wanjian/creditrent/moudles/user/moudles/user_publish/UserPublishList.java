package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;


/**
 * Created by wanjian on 2017/11/15.
 */

public class UserPublishList {
    
    private int imageId;
    private String name;
    private String publishTime;
    private String number;

    public UserPublishList(int imageId, String name, String publishedTime, String number) {
        this.imageId=imageId;
        this.name=name;
        this.publishTime=publishedTime;
        this.number=number;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
