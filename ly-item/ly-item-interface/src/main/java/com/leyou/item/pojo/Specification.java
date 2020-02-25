package com.leyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2020/1/29.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.pojo
 */
@Table(name = "tb_specification")
public class Specification {
    @Id
    private Long categoryId;
    private String specifications;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
