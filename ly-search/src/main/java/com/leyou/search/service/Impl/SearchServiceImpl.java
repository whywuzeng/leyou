package com.leyou.search.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Specification;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import com.leyou.utils.NumberUtils;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.stats.InternalStats;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/2/19.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.service.Impl
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecClient specClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        //开始搜索
        String key = searchRequest.getKey();
        if (StringUtils.isBlank(key))
        {
            return null;
        }

        //elasticSearch 分页查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //要key 进行all 全文检索查询
        QueryBuilder builder = buildBasicQueryWithFilter(searchRequest);
        queryBuilder.withQuery(builder);

        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","price","subTitle","specs"},null));

        //分页
        searchWithPageAndSort(searchRequest, queryBuilder);

        //聚合
        String categoryAggName = "category";
        String brandAggName = "brand";

        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        NativeSearchQuery searchQuery = queryBuilder.build();

        //查询 -- 获取结果
        AggregatedPage<Goods> goods = (AggregatedPage<Goods>)this.goodsRepository.search(searchQuery);

        long totalElements = goods.getTotalElements();
        long totalPages = goods.getTotalPages();
        List<Goods> goodsList = goods.getContent();

        List<Category> categoryAggResult = getCategoryAggResult(categoryAggName, goods);

        List<Map<String,Object>> specs = null;
        if (categoryAggResult.size() == 1)
        {
            //可以查找规格，并来聚合规格
             specs = getSpec(categoryAggResult.get(0).getId(),goods, searchQuery.getQuery());
        }

        List<Brand> brandAggResult = getBrandAggResult(brandAggName,goods);

        return new SearchResult(totalElements,totalPages,goodsList,categoryAggResult,brandAggResult, specs);
    }

    private QueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
//        {
//            query:{
//                bool:{
//                 must:{match: { "title": "小米手机",operator:"and"}}
//                 filter:{
//                     "range":{"price":{"gt":2000.00,"lt":3800.00}}
//                 }
//                }
//            }
//        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        Map<String, Object> filter = searchRequest.getFilter();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String regex = "^(\\d+\\.?\\d*)-(\\d+\\.?\\d*)$";

            if (!"price".equalsIgnoreCase(key)){
                if (value.toString().matches(regex))
                {
                    Double[] nums = NumberUtils.searchNumber((String) value, regex);
                    //数值类型进行范围查询   lt:小于  gte:大于等于
                    filterQueryBuilder.must(QueryBuilders.rangeQuery("specs." + key).gte(nums[0]).lt(nums[1]));
                }else {
                    //商品分类和品牌要特殊处理
                    if (key != "cid3" && key != "brandId") {
                        key = "specs." + key + ".keyword";
                    }
                    //字符串类型，进行term查询
                    filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
                }
            }
        }
        boolQueryBuilder.filter(filterQueryBuilder);
        return  boolQueryBuilder;
    }

    private List<Map<String, Object>> getSpec(Long cid, AggregatedPage<Goods> goods, QueryBuilder queryBuilder) {
        List<Map<String,Object>> retSpecs = new ArrayList<>();

        List<Specification> specifications = specClient.getSpecificationByCategordId(cid).getBody();

        Set<String> strSpec = new HashSet<>();
        Map<String,Object> numericalUnits = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (specifications.size() == 1) {

            Specification specification = specifications.get(0);
            String specificationsJson = specification.getSpecifications();
            List<Map<String,Object>> mapList = null;
            try {
                mapList = mapper.readValue(specificationsJson,new TypeReference<List<Map<String,Object>>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
            String searchableStr="searchable";
            String k ="k";
            String numerical="numerical";
            String unit="unit";
            //过滤可以搜索的 分为数值型和 字符串型

            mapList.forEach(item -> {
                List<Map<String, Object>> params = (List<Map<String, Object>>) item.get("params");
                params.forEach(param -> {
                    if ((Boolean) param.get(searchableStr))
                    {
                        if (param.containsKey(numerical)&&(Boolean) param.get(numerical)){
                            numericalUnits.put((String) param.get(k),(String)param.get(unit));
                        }else {
                            strSpec.add(param.get(k).toString());
                        }
                    }
                });
            });
        }
        else {
            try {
                throw new Exception("分类id"+cid+"对应多个规格,数据有误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //给有数值的集合做好min max 选值
        Map<String,Double> numberInterval = getNumberInterval(cid,numericalUnits);


        List<Map<String,Object>> SpecsMaps =new ArrayList<>();
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = searchQueryBuilder.withQuery(queryBuilder);

        //聚合数值类型
        numberInterval.forEach((s, aDouble) -> {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.histogram(s).interval(aDouble).field("specs."+s).minDocCount(1));
        });

        List<Goods> content = goods.getContent();
        Goods goodsItem = content.get(0);
        Map<String, Object> specs = goodsItem.getSpecs();
        //集合String 类型
        strSpec.forEach(s -> {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(s).field("specs."+s+".keyword"));
        });

        AggregatedPageImpl<Goods> search = (AggregatedPageImpl<Goods>) this.goodsRepository.search(nativeSearchQueryBuilder.build());
        Map<String, Aggregation> stringAggregationMap = search.getAggregations().asMap();
        search.getAggregations().asMap().forEach((s, aggregation) -> {
//            Map<String,Object> spec = new HashMap<>();
//            spec.put("k",s);
//            StringTerms terms = (StringTerms) aggregation;
//            spec.put("option",terms.getBuckets().stream().map((Function<StringTerms.Bucket, Object>)StringTerms.Bucket::getKeyAsString).collect(Collectors.toList()));
//            retSpecs.add(spec);
        });

        //解析数值类型
        numericalUnits.forEach((s, aDouble) -> {
            InternalHistogram histogram = (InternalHistogram) stringAggregationMap.get(s);
            Map<String,Object> map = new HashMap<>();
            String k = "k";
            String unit="unit";
            map.put(k,s);
            map.put(unit,aDouble);
            map.put("options",histogram.getBuckets().stream().map(bucket -> {
                Double begin = (Double) bucket.getKey();
                Double end = begin + numberInterval.get(s);
                //对begin和end取整
                if (NumberUtils.isInt(begin) && NumberUtils.isInt(end)){
                    //确实是整数，直接取整
                    return begin.intValue() + "-" + end.intValue();
                }else {
                    //小数，取2位小数
                    begin = NumberUtils.scale(begin,2);
                    end = NumberUtils.scale(end,2);
                    return begin + "-" + end;
                }
            }).collect(Collectors.toList()));

            SpecsMaps.add(map);
        });

        //解析String类型
        strSpec.forEach(s -> {
            Map<String,Object> map = new HashMap<>();
            map.put("k",s);
            StringTerms histogram = (StringTerms) stringAggregationMap.get(s);
            List<Object> collect = histogram.getBuckets().stream().map((Function<StringTerms.Bucket, Object>) StringTerms.Bucket::getKeyAsString)
                    .collect(Collectors.toList());
            map.put("options",collect);
            SpecsMaps.add(map);
        });
        return SpecsMaps;
    }

    private Map<String, Double> getNumberInterval(Long cid, Map<String, Object> numericalUnits) {
        Map<String,Double> map = new HashMap<>();

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("cid3",cid)).withSourceFilter(new FetchSourceFilter(new String[]{""},null)).withPageable(PageRequest.of(0,1));
        numericalUnits.forEach((s, o) -> {
            builder.addAggregation(AggregationBuilders.stats(s).field("specs."+ s));
        });
//        this.elasticsearchTemplate.query(builder.build(),new )
        AggregatedPageImpl<Goods> search = (AggregatedPageImpl<Goods>)this.goodsRepository.search(builder.build());

        Map<String, Aggregation> aggregationMap = search.getAggregations().asMap();
        for (String item : numericalUnits.keySet()) {
            InternalStats stats = (InternalStats) aggregationMap.get(item);
            //根据最大最小值计算间隔值
            double interval = getInterval(stats.getMin(),stats.getMax(),stats.getSum());
            map.put(item,interval);
        }
        return map;
    }

    private double getInterval(Double min, Double max, Double sum) {
        double interval = (max - min) / 6;
        if (sum.intValue() == sum)
        {
            //不是小数，要取整十、整百
            int length = StringUtils.substringBefore(String.valueOf(interval),".").length();
            double factor = Math.pow(10.0,length - 1);
            return Math.round(interval / factor)*factor;
        }else {
            return NumberUtils.scale(interval,1);
        }
    }

    private List<Brand> getBrandAggResult(String brandAggName, AggregatedPage<Goods> goods) {
        LongTerms aggregation = (LongTerms) goods.getAggregation(brandAggName);
        List<Long> bids = new ArrayList<>();
        for (LongTerms.Bucket bucket : aggregation.getBuckets()) {
            long bid = bucket.getKeyAsNumber().longValue();
            bids.add(bid);
        }
        return this.brandClient.queryBrandByIds(bids);
    }

    private List<Category> getCategoryAggResult(String categoryAggName, AggregatedPage<Goods> goods) {
        List<Category> categories = new ArrayList<>();
        LongTerms longTerms = (LongTerms)goods.getAggregation(categoryAggName);
        List<Long> cids = new ArrayList<>();
        List<LongTerms.Bucket> buckets = longTerms.getBuckets();
        buckets.forEach(bucket -> {
            cids.add(bucket.getKeyAsNumber().longValue());
        });
        //根据Id查询分类名称
        List<String> names = this.categoryClient.queryNameByIds(cids);
        for (int i = 0; i < names.size(); i++) {
            Category category = new Category();
            category.setId(cids.get(i));
            category.setName(names.get(i));
            categories.add(category);
        }
        return categories;
    }

    private void searchWithPageAndSort(SearchRequest searchRequest, NativeSearchQueryBuilder queryBuilder) {
        Integer page = searchRequest.getPage();
        Integer pageRow = searchRequest.getPageRow();

        queryBuilder.withPageable(PageRequest.of(page-1,pageRow));

        String sortBy = searchRequest.getSortBy();
        Boolean descending = searchRequest.getDescending();
        if (!StringUtils.isBlank(sortBy))
        {
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(descending? SortOrder.DESC:SortOrder.ASC));
        }
    }

}
