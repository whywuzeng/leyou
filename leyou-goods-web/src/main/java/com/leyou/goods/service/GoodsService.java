package com.leyou.goods.service;

import java.util.Map;

/**
 * Created by Administrator on 2020/2/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.service
 */

public interface GoodsService {

    /**
     * //返回详情界面所有的数据
     * @param spuId
     * @return
     */
    Map<String,Object> loadGroup(Long spuId);
}
