package com.example.wanjian.creditrent.moudles.user;

/**
 * Created by wanjian on 2018/3/26.
 */

public class UserBean {


//    private String userId;
//    private String nickName;          //昵称，即用户学号
//    private String password;          //密码

    //已使用
    private String phone;              //用户名，即用户手机号 userId
    private String birthday;            //生日
    private String sex;               //性别
    private Integer crent;             //信用度
    private String area;               //学校
    private String qianming;           //个人宣言
    private String img;             //头像
    private String renzheng;           //认证

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCrent() {
        return crent;
    }

    public void setCrent(Integer crent) {
        this.crent = crent;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRenzheng() {
        return renzheng;
    }

    public void setRenzheng(String renzheng) {
        this.renzheng = renzheng;
    }
}
