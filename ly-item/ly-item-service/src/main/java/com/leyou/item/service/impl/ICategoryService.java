package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/1/22.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service.impl
 */
@Service
public class ICategoryService implements CategoryService {

    @Autowired
    private CategoryMapper categroyMapper;

    @Override
    public List<Category> queryListByParent(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return this.categroyMapper.select(category);
    }

    @Override
    public List<String> queryCategoryByIds(List<Long> Ids) {
        List<String> collect = this.categroyMapper.selectByIdList(Ids).stream().map(Category::getName)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void addCategory(Category category) {

        category.setId(null);

        int insert = this.categroyMapper.insert(category);

        //修改父节点状态
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setisParent(true);
        this.categroyMapper.updateByPrimaryKeySelective(parent);
    }

    @Override
    public List<Category> queryAlllevel(Long cid3) {
        Category c3 = this.categroyMapper.selectByPrimaryKey(cid3);
        Category c2 = this.categroyMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categroyMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1,c2,c3);
    }

}
