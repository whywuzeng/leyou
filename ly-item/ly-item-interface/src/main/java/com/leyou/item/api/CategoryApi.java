package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("names")
    List<String> queryNameByIds(@RequestParam(name = "ids") List<Long> ids);
}
