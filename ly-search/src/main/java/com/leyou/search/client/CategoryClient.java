package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.client
 */
@FeignClient("item-service")
public interface CategoryClient  extends CategoryApi {
}
