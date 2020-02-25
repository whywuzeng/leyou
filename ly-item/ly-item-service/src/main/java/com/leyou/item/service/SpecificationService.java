package com.leyou.item.service;

import com.leyou.item.pojo.Specification;

import java.util.List;

/**
 * Created by Administrator on 2020/1/29.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.service
 */

public interface SpecificationService {
    Specification queryById(Long id);

    List<Specification> queryByCategoryId(Long categoryId);

    List<Specification> queryByCategoryId(Long categoryId,Boolean searchable) throws Exception;
}
