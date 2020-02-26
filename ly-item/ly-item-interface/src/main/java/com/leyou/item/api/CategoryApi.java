package com.leyou.item.api;

import com.leyou.item.pojo.Category;

import org.springframework.http.ResponseEntity;
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
    //只查询name String类型
    @GetMapping("names")
    List<String> queryNameByIds(@RequestParam(name = "ids") List<Long> ids);

    //返回List分类
    @GetMapping("all/level")
     ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long cid3);
}
