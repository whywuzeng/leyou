package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by Administrator on 2020/1/30.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service.impl
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public PageResult<SpuBo> querySpuByPageAndSort(Integer page, Integer rows, Boolean saleable, String key) {
        PageHelper.startPage(page, Math.min(rows, 100));

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable != null) {
            criteria.orEqualTo("saleable",saleable);
        }
        if (StringUtils.isNotBlank(key))
        {
            criteria.andLike("title","%"+key+"%");
        }

        Page<Spu> pageInfo = (Page<Spu>) this.spuMapper.selectByExample(example);

        List<SpuBo> list = pageInfo.getResult().stream().map(spu -> {
            //2.把spu变为spubo
            SpuBo spuBo = new SpuBo();
            //属性拷贝
            BeanUtils.copyProperties(spu, spuBo);
            //3.查询spu的商品分类名称，要查三级分类
            List<String> categorys = categoryService.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            // 将分类名称拼接后存入
            spuBo.setCname(StringUtils.join(categorys, "/"));

            // 4、查询spu的品牌名称
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

            spuBo.setBname(brand.getName());

            return spuBo;
        }).collect(Collectors.toList());

        return new PageResult<>(pageInfo.getTotal(),list);
    }

    @Override
    public SpuDetail querySpuDetailById(Long id) {
        return spuDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sku> querySkuBySpuId(Long id) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",id);
        return skuMapper.selectByExample(example);
    }

    @Override
    public SpuBo querySpuById(Long id) {
        //这里一致作为一个接口
        //查出spuBo
        //包含 spudetail 和 skulist
        Spu spu = spuMapper.selectByPrimaryKey(id);
        SpuDetail spuDetail = this.spuDetailMapper.selectByPrimaryKey(id);

        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",id);
        List<Sku> skus = this.skuMapper.selectByExample(example);

        SpuBo spuBo = new SpuBo(spu.getBrandId(),spu.getCid1(),spu.getCid2(),spu.getCid3(),spu.getTitle(),spu.getSubTitle(),spu.getSaleable(),spu.getValid(),spu.getCreateTime(),spu.getLastUpdateTime());
        spuBo.setSpuDetail(spuDetail);
        spuBo.setSkuList(skus);

        return spuBo;
    }
}
