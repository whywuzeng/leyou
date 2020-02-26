package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * Created by Administrator on 2020/1/30.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */

public interface GoodsService {

    PageResult<SpuBo> querySpuByPageAndSort(Integer page, Integer rows, Boolean saleable, String key);


    SpuDetail querySpuDetailById(Long id);

    List<Sku> querySkuBySpuId(Long id);

    SpuBo querySpuById(Long id);
}
