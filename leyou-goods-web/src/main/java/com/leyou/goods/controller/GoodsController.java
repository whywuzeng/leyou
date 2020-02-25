package com.leyou.goods.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2020/2/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.controller
 */

@Controller
@RequestMapping("item")
public class GoodsController {

    @GetMapping("{id}.html")
    public String toImagePage(Model model, @PathVariable("id")Long id){
        // 数据sku

        return "item";
    }
}
