package com.leyou.goods.service.impl;

import com.leyou.goods.client.BrandClient;
import com.leyou.goods.client.CategoryClient;
import com.leyou.goods.client.GoodsClient;
import com.leyou.goods.service.GoodsService;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpuBo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2020/2/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.service.impl
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Override
    public Map<String, Object> loadGroup(Long spuId) throws ExecutionException,InterruptedException{
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch1 = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Map<String,Object> maps=new HashMap<>();
        //spu
        SpuBo spuBo = executorService.submit(() -> {
            countDownLatch.countDown();
            return goodsClient.querySpuById(spuId);
        }).get();
        countDownLatch.await();
        //三级分类
        List<Category> categories = executorService.submit(() -> {
            countDownLatch1.countDown();
            return categoryClient.queryAllByCid3(spuBo.getCid3()).getBody();
        }).get();

        //品牌名称
        List<Brand> brandList = executorService.submit(() -> {
            countDownLatch1.countDown();
            return brandClient.queryBrandByIds(Arrays.asList(spuBo.getBrandId()));
        }).get();
        countDownLatch1.await();

        String specifications = spuBo.getSpuDetail().getSpecifications();
//        JsonUtils.parseList(specifications,List<Map<String,Object>>)
        maps.put("categories",categories);
        maps.put("brandList",brandList);
        maps.put("spuBo",spuBo);
//        maps.put("specifications",specifications);
        return maps;
    }
}
