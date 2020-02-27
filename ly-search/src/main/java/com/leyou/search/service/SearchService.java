package com.leyou.search.service;

import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;

/**
 * Created by Administrator on 2020/2/19.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.service
 */

public interface SearchService {

     SearchResult search(SearchRequest searchRequest);

     void createIndex(Long id) throws Exception;

     void deleteIndex(Long id) throws Exception;
}
