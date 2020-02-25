package com.leyou.goods.service.impl;

import com.leyou.goods.client.BrandClient;
import com.leyou.goods.client.CategoryClient;
import com.leyou.goods.client.GoodsClient;
import com.leyou.goods.service.GoodsService;
import com.leyou.item.pojo.Spu;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2020/2/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.service.impl
 */

public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Override
    public Map<String, Object> loadGroup(Long spuId) {
        Spu spu = goodsClient.querySpuById(spuId);
        //三级分类
        goodsClient.

        //品牌名称

        //spu

        //sku

        return null;
    }
}
