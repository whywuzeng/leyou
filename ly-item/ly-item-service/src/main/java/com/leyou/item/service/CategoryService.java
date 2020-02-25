package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2020/1/22.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */

public interface CategoryService {
    List<Category> queryListByParent(Long pid);

    List<String> queryCategoryByIds(List<Long> Ids);

    void addCategory(Category category);

    List<Category> queryAlllevel(Long cid3);
}
