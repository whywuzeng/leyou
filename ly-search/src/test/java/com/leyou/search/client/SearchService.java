package com.leyou.search.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.sun.deploy.util.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/2/18.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.client
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private GoodsRepository goodsRepository;

    public Goods buildGoods(Spu spu) throws Exception{
        Goods goods = new Goods();

        // 查询商品分类名称
        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        //查询skus
        List<Sku> skus = this.goodsClient.querySkuBySpuId(spu.getId());

        //查询详情
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spu.getId());

        //价格
        List<BigInteger> longs = new ArrayList<>();
        //标题,图片
        List<Map<String,Object>> mapList = new ArrayList<>();
        skus.forEach(sku -> {
            //价格
           longs.add(sku.getPrice());
           //具体的标题
            Map<String,Object> maps = new HashMap<>();
            maps.put("id",sku.getId());
            maps.put("title",sku.getTitle());
            maps.put("images",sku.getImages());
            maps.put("price",sku.getPrice());
            mapList.add(maps);
        });

        List<Map<String,Object>> genericSpecs = mapper.readValue(spuDetail.getSpecifications(),new TypeReference<List<Map<String,Object>>>(){
        });

        Map<String,Object> specialSpecs = mapper.readValue(spuDetail.getSpecTemplate(), new TypeReference<Map<String,Object>>() {
       });

//        skus  specs 分类，品牌，网络格式,显示屏,像素，价格
        Map<String,Object> specs = new HashMap<>();

        String params ="params";
        String searchable ="searchable";
        String k = "k";
        String v = "v";
        String options = "options";
        String numerical = "numerical";
        String unit ="unit";

        genericSpecs.forEach(m -> {
            List<Map<String, Object>> mapList1 = (List<Map<String, Object>>) m.get(params);
            mapList1.forEach(map -> {
                if ((Boolean) map.get(searchable))
                {
                    if (map.get(v)!=null)
                    {
                        if (map.get(numerical)!=null&&(Boolean) map.get(numerical))
                        {
                            specs.put((String) map.get(k),map.get(v));
                        }else {
                            specs.put((String) map.get(k),map.get(v));
                        }
                    }else if (map.get(options)!=null){
                        specs.put((String) map.get(k),map.get(options));
                    }
                }
            });
        });

        goods.setId(spu.getId());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());

        goods.setSubTitle(spu.getSubTitle());
        List<Long> longList = new ArrayList<>();
        longs.forEach(bigInteger -> {
            longList.add(Long.valueOf(String.valueOf(bigInteger)));
        });
        goods.setPrice(longList);
        goods.setAll(spu.getTitle()+" "+ StringUtils.join(names," "));
        goods.setSpecs(specs);
        goods.setSkus(mapper.writeValueAsString(skus));
        return goods;
    }

    @Test
    public void loadData(){
        int page =1;
        int rows = 100;
        int size = 0;

        do {

            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(page, rows, true, null);
            List<SpuBo> spuBos = result.getItems();
            size =  spuBos.size();
            List<Goods> goodsList = new ArrayList<>();
            for (SpuBo spuBo : spuBos) {
                try {
                    Goods goods = buildGoods(spuBo);
                    goodsList.add(goods);
                } catch (Exception e) {
                    System.out.println(spuBo);
                    e.printStackTrace();
                    break;
                }
            }

            goodsRepository.saveAll(goodsList);
            page++;
        }while (size == 100);
    }
}
