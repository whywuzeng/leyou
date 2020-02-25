package com.leyou.item.pojo;

import java.math.BigInteger;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.pojo
 */
@Table(name = "tb_sku")
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger spuId;
    private String title; //商品标题
    private String images; //商品的图片，多个图片以‘,’分割
    private BigInteger price; // 销售价格，单位为分
    private String indexes; // 特有规格属性在spu属性模板中的对应下标组合 [0,0,0]
    private String ownSpec; //sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序 实际数值
    private BigInteger enable; //是否有效，0无效，1有效
    private java.sql.Timestamp createTime; //添加时间
    private java.sql.Timestamp lastUpdateTime; //最后修改时间


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }


    public BigInteger getSpuId() {
        return spuId;
    }

    public void setSpuId(BigInteger spuId) {
        this.spuId = spuId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }


    public String getIndexes() {
        return indexes;
    }

    public void setIndexes(String indexes) {
        this.indexes = indexes;
    }


    public String getOwnSpec() {
        return ownSpec;
    }

    public void setOwnSpec(String ownSpec) {
        this.ownSpec = ownSpec;
    }


    public BigInteger getEnable() {
        return enable;
    }

    public void setEnable(BigInteger enable) {
        this.enable = enable;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public java.sql.Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(java.sql.Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
