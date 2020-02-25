package com.leyou.item.api;

import com.leyou.item.pojo.Specification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.api
 */
@RequestMapping("spec")
public interface SpecApi {

    @RequestMapping(method = RequestMethod.GET,value = "{nodeId}")
    ResponseEntity<String> specificationById(@PathVariable("nodeId") Long id);

    @RequestMapping(method = RequestMethod.GET,value = "categoryId")
     ResponseEntity<List<Specification>> getSpecificationByCategordId(@RequestParam(name = "categoryId") Long categoryId);

}
