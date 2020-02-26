package com.leyou.item.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2020/1/30.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.pojo
 */

public class SpuBo extends Spu {
    @Transient
    private String cname;// 商品分类名称

    @Transient
    private String bname;// 品牌名称

    @Transient
    private SpuDetail spuDetail;

    @Transient
    private List<Sku> skuList;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public SpuBo() {
    }

    public SpuBo(Long brandId, Long cid1, Long cid2, Long cid3, String title, String subTitle, Boolean saleable, Boolean valid, Date createTime, Date lastUpdateTime) {
        super(brandId, cid1, cid2, cid3, title, subTitle, saleable, valid, createTime, lastUpdateTime);
    }
}
