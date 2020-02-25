package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2020/1/23.
 * <p>
 * by author wz
 * <p>
 * com.leyou.item.mapper
 */

public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand,Long> {

@Insert("insert into tb_category_brand(category_id,brand_id)values(#{cid},#{id})")
    void insertCategory(Long cid, Long id);

    @Select("select *\n" +
            "from tb_brand as tb\n" +
            "left join tb_category_brand as tbc\n" +
            "on tb.id = tbc.brand_id\n" +
            "where tbc.category_id = #{cid}")
    List<Brand> getBrandByCid(Long cid);
}
