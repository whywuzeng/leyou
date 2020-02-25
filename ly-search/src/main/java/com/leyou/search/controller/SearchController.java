package com.leyou.search.controller;

import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/2/19.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.controller
 */

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult> search(@RequestBody SearchRequest searchRequest)
    {
        SearchResult goodsPageResult = searchService.search(searchRequest);
        if (goodsPageResult.getItems()==null||goodsPageResult.getItems().size()<1)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(goodsPageResult);
    }
}
