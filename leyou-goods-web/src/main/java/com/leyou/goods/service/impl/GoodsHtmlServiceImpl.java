package com.leyou.goods.service.impl;

import com.leyou.goods.service.GoodsHtmlService;
import com.leyou.goods.service.GoodsService;
import com.leyou.goods.utils.ThreadUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Created by Administrator on 2020/2/26.
 * <p>
 * by author wz
 * <p>
 * com.leyou.goods.service.impl
 */
@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    public static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlServiceImpl.class);
    /**
     * 创建html页面
     * @param id
     */
    @Override
    public void createHtml(Long id) {

        PrintWriter writer =null;

        try {
            Map<String, Object> groups = this.goodsService.loadGroup(id);

            // 创建thymeleaf上下文对象
            Context context = new Context();

            // 把数据放入上下文对象
            context.setVariables(groups);

            File file = new File("H:\\soft\\nginx-1.12.2\\html\\item\\" + id + ".html");

            //创建输出流
            writer = new PrintWriter(file);

            // 执行页面静态化方法
            templateEngine.process("item",context, writer);
        } catch (Exception e) {
            LOGGER.error("页面静态化出错:{}"+e,id);
            e.printStackTrace();
        }finally {
            if (writer!=null)
            {
                writer.close();
            }
        }

    }

    public void asyncExcute(Long id)
    {
        ThreadUtils.execute(() -> createHtml(id));
    }
}
