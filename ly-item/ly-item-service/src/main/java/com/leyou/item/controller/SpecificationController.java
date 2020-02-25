package com.leyou.item.controller;

import com.leyou.item.pojo.Specification;
import com.leyou.item.service.SpecificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2020/1/29.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.controller
 */

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @RequestMapping(method = RequestMethod.GET,value = "{nodeId}")
    public ResponseEntity<String> specificationById(@PathVariable("nodeId") Long id){
        Specification specification = this.specificationService.queryById(id);
        if (specification==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(specification.getSpecifications());
    }

    //http://127.0.0.1:8081/spec/categoryId?categoryId=76
    @RequestMapping(method = RequestMethod.GET,value = "categoryId")
    public ResponseEntity<List<Specification>> getSpecificationByCategordId(@RequestParam(name = "categoryId") Long categoryId){

        List<Specification> specification = this.specificationService.queryByCategoryId(categoryId);
        if (specification == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(specification);
    }
    //http://127.0.0.1:8081/spec/searchable?categoryId=76
    @RequestMapping(method = RequestMethod.GET,value = "searchable")
    public ResponseEntity<List<Specification>> getSpecificationByCategordMultiId(@RequestParam(name = "categoryId") Long categoryId,Boolean searchable){
        List<Specification> specification = null;
        try {
            specification = this.specificationService.queryByCategoryId(categoryId,searchable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (specification == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(specification);
    }
}
