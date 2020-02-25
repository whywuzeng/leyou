package com.leyou.search.client;

import com.leyou.item.api.BrandApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Administrator on 2020/2/20.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.client
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
