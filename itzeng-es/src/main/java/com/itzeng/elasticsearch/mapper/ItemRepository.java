package com.itzeng.elasticsearch.mapper;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Administrator on 2020/2/17.
 * <p>
 * by author wz
 * <p>
 * com.itzeng.elasticsearch.mapper
 */

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

}
