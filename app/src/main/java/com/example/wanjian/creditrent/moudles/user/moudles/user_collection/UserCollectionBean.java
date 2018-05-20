package com.example.wanjian.creditrent.moudles.user.moudles.user_collection;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 17631 on 2018/5/21.
 */

public class UserCollectionBean {
    /**
     * 1 : {"goodsid":"3","goodsname":"果壳中的宇宙","goodsststus":"已上架"}
     * 2 : {"goodsid":"5","goodsname":"java并发编程实战","goodsststus":"上架审核中"}
     */

    @SerializedName("1")
    private _$1Bean _$1;
    @SerializedName("2")
    private _$2Bean _$2;

    public _$1Bean get_$1() {
        return _$1;
    }

    public void set_$1(_$1Bean _$1) {
        this._$1 = _$1;
    }

    public _$2Bean get_$2() {
        return _$2;
    }

    public void set_$2(_$2Bean _$2) {
        this._$2 = _$2;
    }

    public static class _$1Bean {
        /**
         * goodsid : 3
         * goodsname : 果壳中的宇宙
         * goodsststus : 已上架
         */

        private String goodsid;
        private String goodsname;
        private String goodsststus;

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

        public String getGoodsststus() {
            return goodsststus;
        }

        public void setGoodsststus(String goodsststus) {
            this.goodsststus = goodsststus;
        }
    }

    public static class _$2Bean {
        /**
         * goodsid : 5
         * goodsname : java并发编程实战
         * goodsststus : 上架审核中
         */

        private String goodsid;
        private String goodsname;
        private String goodsststus;

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

        public String getGoodsststus() {
            return goodsststus;
        }

        public void setGoodsststus(String goodsststus) {
            this.goodsststus = goodsststus;
        }
    }
}
