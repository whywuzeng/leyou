package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.repository
 */

public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {

}
