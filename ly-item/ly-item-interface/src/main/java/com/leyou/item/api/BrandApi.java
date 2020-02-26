package com.leyou.item.api;

import com.leyou.item.pojo.Brand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2020/2/20.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.api
 */
@RequestMapping("brand")
public interface BrandApi {

    //商品id查询商品
    @GetMapping("list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);

}
