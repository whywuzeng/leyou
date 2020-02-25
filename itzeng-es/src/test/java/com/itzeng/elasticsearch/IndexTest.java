package com.itzeng.elasticsearch;

import com.itzeng.elasticsearch.mapper.Item;
import com.itzeng.elasticsearch.mapper.ItemRepository;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/2/17.
 * <p>
 * by author wz
 * <p>
 * com.itzeng.elasticsearch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexTest {

    @Autowired(required=false)
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreate(){
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Item.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testsave(){
        Item item = new Item(1L, "小米手机7", "手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void testSaveList(){
        List<Item> items = new ArrayList<>();
        items.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        items.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        items.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        items.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        itemRepository.saveAll(items);
    }

    @Test
    public void testFind(){
        Iterable<Item> items = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        items.forEach(item -> System.out.println(item));
    }

    @Test
    public void testQuery(){
        // 词条查询
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "小米大苹果");
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
        items.forEach(item -> System.out.println(item));
    }

//    "category"," 手机"
    @Test
    public void testNativeQuery(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.boolQuery().should(QueryBuilders.termsQuery("category","手机"," 手机")));

        queryBuilder.withPageable(PageRequest.of(0,3));

        Page<Item> items = this.itemRepository.search(queryBuilder.build());

        System.out.println(items.getTotalPages());

        System.out.println(items.getTotalElements());

        System.out.println(items.getSize());

        System.out.println(items.getNumber());

        items.forEach(item -> System.out.println(item));
    }

    @Test
    public void testAggs(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));

        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand").subAggregation(AggregationBuilders.avg("avg_price").field("price")));

        AggregatedPage<Item> searchs = (AggregatedPage<Item>)this.itemRepository.search(nativeSearchQueryBuilder.build());

        StringTerms brands = (StringTerms)searchs.getAggregation("brands");

        List<StringTerms.Bucket> buckets = brands.getBuckets();

        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());

            System.out.println(bucket.getDocCount());

            InternalAvg avg_price = (InternalAvg)bucket.getAggregations().asMap().get("avg_price");

            System.out.println(avg_price.getValue());
        });
    }

}
