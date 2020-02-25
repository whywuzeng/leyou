package com.leyou.goods.client;

import com.leyou.item.api.GoodsApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Administrator on 2020/2/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.client
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
