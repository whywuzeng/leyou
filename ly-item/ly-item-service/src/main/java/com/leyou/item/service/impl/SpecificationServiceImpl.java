package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.Specification;
import com.leyou.item.service.SpecificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * Created by Administrator on 2020/1/29.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service.impl
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public Specification queryById(Long id) {
        return specificationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Specification> queryByCategoryId(Long categoryId) {

//        example.createCriteria().andLike("name","%"+key+"%")
//                .andNotEqualTo("letter",key);
        Example example = new Example(Specification.class);
        example.createCriteria().andEqualTo("categoryId", categoryId);
        return specificationMapper.selectByExample(example);
    }

    //可以筛选其是否可以搜索字段
    @Override
    public List<Specification> queryByCategoryId(Long categoryId, Boolean searchable) throws Exception {
        Example example = new Example(Specification.class);
        example.createCriteria().andEqualTo("categoryId", categoryId);
        List<Specification> specifications = specificationMapper.selectByExample(example);

        return null;
    }
}
