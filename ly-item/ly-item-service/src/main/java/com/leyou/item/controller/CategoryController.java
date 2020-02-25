package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2020/1/22.
 * <p>
 * by author wz
 * http://api.leyou.com/api/item/category/list?pid=0
 * <p>
 * com.leyou.item.controller
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        List<Category> categories = categoryService.queryListByParent(pid);
        if (categories == null || categories.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public void addCategoryByObject(Category category) {
        this.categoryService.addCategory(category);
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam(name = "ids") List<Long> ids) {
        List<String> category = this.categoryService.queryCategoryByIds(ids);
        if (category == null || category.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category);
    }

    //http://127.0.0.1:8086/category/all/level?id=76
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long cid3) {
        List<Category> categories = this.categoryService.queryAlllevel(cid3);
        if (categories == null || categories.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categories);
    }

}
