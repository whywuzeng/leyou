package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2020/1/23.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.controller
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    @RequestMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "rows",defaultValue = "5") int rows,
            @RequestParam(name = "sortBy",required = false) String sortBy,
            @RequestParam(name = "desc",defaultValue = "false") boolean isDesc,
            @RequestParam(name = "key",defaultValue = "",required = false) String key){

        PageResult<Brand> pageResults = this.brandService.queryBrandByPage(page, rows, sortBy, isDesc, key);
        if (pageResults == null || pageResults.getItems().size()<1)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pageResults);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam(name = "cids",required = false) List<Long> cids){
//        {"name":"tesla","image":"","cids":["1","74"],"letter":"T"}
        this.brandService.saveBrand(brand,cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> getBrandByCid(@PathVariable("cid")Long cid)
    {
        List<Brand> brandNames =  this.brandService.getBrandByCid(cid);

        if (brandNames == null ||brandNames.size()<1)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brandNames);
    }

    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids)
    {
        List<Brand> brandList = this.brandService.queryBrandByIds(ids);
        if (brandList == null || brandList.size()<1)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brandList);
    }
}
