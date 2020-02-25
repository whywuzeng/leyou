package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * Created by Administrator on 2020/1/23.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */

public interface BrandService {
    PageResult<Brand> queryBrandByPage(int page, int rows, String sortBy, boolean isDesc, String key);

    void saveBrand(Brand brand, List<Long> cids);

    List<Brand> getBrandByCid(Long cid);

    List<Brand> queryBrandByIds(List<Long> ids);
}
