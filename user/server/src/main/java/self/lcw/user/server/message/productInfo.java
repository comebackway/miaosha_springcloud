package self.lcw.user.server.message;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.user.server.redis.OrderKey;
import self.lcw.user.server.redis.RedisService;

@Component
public class productInfo {
    @Autowired
    RedisService redisService;

    @RabbitListener(queuesToDeclare = @Queue("OrderInfo"))
    public void setOrderInfo(String m){
        OrderDetailDto mess = JSON.toJavaObject(JSON.parseObject(m),OrderDetailDto.class);
        System.out.println("收到啦111");
        redisService.set(OrderKey.Result,String.valueOf(mess.getGoodsId()),"1");
        redisService.set(OrderKey.getMiaoshaOrderByUidGid, String.valueOf(mess.getGoodsId()),String.valueOf(mess.getId()));
    }
}
