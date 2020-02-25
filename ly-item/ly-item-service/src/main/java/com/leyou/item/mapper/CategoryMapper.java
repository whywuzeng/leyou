package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;

import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2020/1/22.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */

public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

}
