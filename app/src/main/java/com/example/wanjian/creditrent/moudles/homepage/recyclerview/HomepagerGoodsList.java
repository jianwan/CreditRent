package com.example.wanjian.creditrent.moudles.homepage.recyclerview;

/**
 * Created by wanjian on 2017/11/30.
 */

public class HomepagerGoodsList {
    /**
     * goodsid : 2
     * goodsname : 时间简史
     * ershousell : 0
     * ershousellmoney : 50.00
     * description : 很好的一本书，放在寝室很久了
     * goodsimg1 : http://119.29.133.244/test/adminweb/photo/134525819232018-04-24-00-19-38.jpg
     * goodsimg2 : http://119.29.133.244/test/adminweb/photo/134525819232018-04-24-00-20-18.jpg
     * goodsimg3 : http://119.29.133.244/test/adminweb/photo/134525819232018-04-24-00-20-51.jpg
     * chuzumoney : 5.00
     */

    private String goodsid;                           //物品id
    private String goodsname;                         //物品id
    private String ershousell;                        //是否支持二手出售
    private String ershousellmoney;                   //二手出售价格
    private String description;                       //物品描述
    private String goodsimg1;                         //物品照片
    private String goodsimg2;
    private String goodsimg3;
    private String chuzumoney;                        //出租价格

    public HomepagerGoodsList(String goodsimg1,String goodsname,String chuzumoney,String description){
        this.goodsimg1=goodsimg1;
        this.goodsname=goodsname;
        this.chuzumoney=chuzumoney;
        this.description=description;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getErshousell() {
        return ershousell;
    }

    public void setErshousell(String ershousell) {
        this.ershousell = ershousell;
    }

    public String getErshousellmoney() {
        return ershousellmoney;
    }

    public void setErshousellmoney(String ershousellmoney) {
        this.ershousellmoney = ershousellmoney;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoodsimg1() {
        return goodsimg1;
    }

    public void setGoodsimg1(String goodsimg1) {
        this.goodsimg1 = goodsimg1;
    }

    public String getGoodsimg2() {
        return goodsimg2;
    }

    public void setGoodsimg2(String goodsimg2) {
        this.goodsimg2 = goodsimg2;
    }

    public String getGoodsimg3() {
        return goodsimg3;
    }

    public void setGoodsimg3(String goodsimg3) {
        this.goodsimg3 = goodsimg3;
    }

    public String getChuzumoney() {
        return chuzumoney;
    }

    public void setChuzumoney(String chuzumoney) {
        this.chuzumoney = chuzumoney;
    }


//    private int avatar;
//    private String name;
//    private String price;
//    private String description;
//
//    public HomepagerGoodsList(int avatar,String name,String price,String description){
//        this.avatar=avatar;
//        this.name=name;
//        this.price=price;
//        this.description=description;
//    }
//
//    public Integer getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(Integer avatar) {
//        this.avatar = avatar;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//
//

}
