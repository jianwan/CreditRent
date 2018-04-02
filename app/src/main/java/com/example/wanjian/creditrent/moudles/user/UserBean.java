package com.example.wanjian.creditrent.moudles.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wanjian on 2018/3/26.
 */

public class UserBean implements Parcelable {



    private String userId;
    private String userName;          //用户名，即用户手机号 userId
    private String nickName;;         //昵称，即用户学号
    private String password;          //密码
    private Boolean sex;              //性别
    private Integer credit;           //信用度
    private String shool;             //学校
    private String declaration;       //个人宣言
    private String Avatar;            //头像




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getShool() {
        return shool;
    }

    public void setShool(String shool) {
        this.shool = shool;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public static Creator<UserBean> getCREATOR() {
        return CREATOR;
    }


    public UserBean(){

    }

    protected UserBean(Parcel in) {

        userId = in.readString();
        userName = in.readString();
        nickName = in.readString();
        password = in.readString();
        sex = in.readByte() != 0;
        credit = in.readInt();
        shool = in.readString();
        declaration = in.readString();
        Avatar = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 注意这里，并不是直接写入boolean值，而是写入整数值
        dest.writeByte((byte)(sex ? 1 : 0));

        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(nickName);
        dest.writeString(password);
        dest.writeInt(credit);
        dest.writeString(shool);
        dest.writeString(declaration);
        dest.writeString(Avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
