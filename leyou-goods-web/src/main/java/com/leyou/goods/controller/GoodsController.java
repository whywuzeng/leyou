package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    @Autowired
    private GoodsService goodsService;

    @GetMapping("{id}.html")
    public String toImagePage(Model model, @PathVariable("id")Long id){
        // 数据sku
        Map<String, Object> map = null;
        try {
             map = goodsService.loadGroup(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model.addAllAttributes(map);
        return "item";
    }
}
