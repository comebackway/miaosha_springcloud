package self.lcw.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.lcw.order.dto.GoodsDto;
import self.lcw.order.entity.MiaoshaOrder;
import self.lcw.order.entity.OrderInfo;
import self.lcw.order.entity.User;
import self.lcw.order.redis.MiaoshaKey;
import self.lcw.order.redis.RedisService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class MiaoshaService {
    /*
    不提倡在自己的service里边引用其他类的dao，如果想用其他类的dao则引入他们对应的service再调用其dao
    @Autowired
    GoodsDao goodsDao
     */

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsDto goodsDto){
        if (user == null){
            return null;
        }

        // TODO 减库存
        boolean success = false;


        if (success){
            //下订单  and  写秒杀订单
            OrderInfo orderInfo = orderService.createOrder(user,goodsDto);
            return orderInfo;
        }else{
            // TODO 设置秒杀完毕状态
            return null;
        }
    }


    /**
     * 查询秒杀结果
     * @param userId
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(userId,goodsId);
        if (miaoshaOrder != null){
            return miaoshaOrder.getOrderId();
        }else{

            //TODO 判断是否已经秒杀完毕
            boolean isOver = true;

            if (isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

}
