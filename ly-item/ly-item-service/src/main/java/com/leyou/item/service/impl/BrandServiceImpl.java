package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by Administrator on 2020/1/23.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service.impl
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     *
     * @param page 当前页，int
     * @param rows 每页大小，int
     * @param sortBy 排序字段，String
     * @param isDesc 是否为降序，boolean
     * @param key
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandByPage(int page, int rows, String sortBy, boolean isDesc, String key) {
        // 开始分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key))
        {
            example.createCriteria().andLike("name","%"+key+"%")
                    .andNotEqualTo("letter",key);
        }
        if (StringUtils.isNotBlank(sortBy)){
            // 排序
            String orderByClause = sortBy+" "+(isDesc? "DESC":"ASC");
            example.setOrderByClause(orderByClause);
        }

        Page<Brand> brandpage = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(brandpage.getTotal(),brandpage);
    }

    @Transactional
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        for (Long cid : cids) {
            brandMapper.insertCategory(cid,brand.getId());
        }
    }

    @Override
    public List<Brand> getBrandByCid(Long cid) {
        List<Brand> brandByCid = brandMapper.getBrandByCid(cid);
        return brandByCid;
    }
    //商品id查询商品
    @Override
    public List<Brand> queryBrandByIds(List<Long> ids) {
       return brandMapper.selectByIdList(ids);
    }
}
