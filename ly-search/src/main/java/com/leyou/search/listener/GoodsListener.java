package com.leyou.search.listener;

import com.leyou.search.service.SearchService;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2020/2/27.
 * <p>
 * by author wz
 * <p>
 * com.leyou.search.listener
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "item.leyou.exchange",
                    ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            value = @Queue(value = "leyou.create.index.queue", durable = "true"),
            key = {"item.insert", "item.update"}
    ))
    public void listener(Long id) throws Exception {
        if (id == null) {
            return;
        }

        System.out.println("修改id" + id);

        searchService.createIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.delete.index.queue", durable = "true"),
            exchange = @Exchange(type = ExchangeTypes.TOPIC,
                    value = "item.leyou.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"item.delete"}))
    public void deleteListener(Long id) throws Exception {
        if (id == null) {
            return;
        }

        searchService.deleteIndex(id);
    }
}
