package self.lcw.order.server.message;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.order.server.entity.OrderInfo;
import self.lcw.order.server.redis.RedisService;
import self.lcw.order.server.service.OrderService;
import self.lcw.product.common.GoodsDetailDto;
import self.lcw.product.common.GoodsDto;

@Slf4j
@Component
public class MqReceiver {

    //@RabbitListener(queues = "myQueue")
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("Receiver: {}",message);
    }

    /**
     * 监听电脑订单
     * @param mess
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerOrder"),
            key = "computer",
            exchange = @Exchange("Order")
    ))
    public void processComputer(String mess){
        log.info("Computer Receiver: {}",mess);
    }
    /**
     * 监听水果订单
     * @param mess
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            key = "fruit",
            exchange = @Exchange("Order")
    ))
    public void processFruit(String mess){
        log.info("Fruit Receiver: {}",mess);
    }


    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    @SendTo("OrderInfo")
    public String SetProductInfo(String message){
        System.out.println("收到啦");
        GoodsDetailDto goodsDetailDto = JSON.toJavaObject(JSON.parseObject(message),GoodsDetailDto.class);
        GoodsDto goodsDto = goodsDetailDto.getGoodsDto();
        //下订单  and  写秒杀订单
        OrderInfo orderInfo = orderService.createOrder(goodsDto);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setGoodsDto(goodsDto);
        orderDetailDto.setCreateDate(orderInfo.getCreateDate());
        orderDetailDto.setDeliveryAddrId(orderInfo.getDeliveryAddrId());
        orderDetailDto.setGoodsCount(orderInfo.getGoodsCount());
        orderDetailDto.setGoodsId(orderInfo.getGoodsId());
        orderDetailDto.setGoodsName(orderInfo.getGoodsName());
        orderDetailDto.setGoodsPrice(orderInfo.getGoodsPrice());
        orderDetailDto.setId(orderInfo.getId());
        orderDetailDto.setPayDate(orderInfo.getPayDate());
        orderDetailDto.setStatus(orderInfo.getStatus());
        orderDetailDto.setUserId(orderInfo.getUserId());
        return JSON.toJSONString(orderDetailDto);

    }
}
