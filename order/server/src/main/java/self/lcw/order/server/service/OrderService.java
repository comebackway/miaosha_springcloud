package self.lcw.order.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.lcw.product.common.GoodsDto;
import self.lcw.order.server.dao.OrderDao;
import self.lcw.order.server.entity.MiaoshaOrder;
import self.lcw.order.server.entity.OrderInfo;
import self.lcw.order.server.redis.OrderKey;
import self.lcw.order.server.redis.RedisService;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long goodsId){
        return orderDao.getMiaoshaOrderByUserIdAndGoodsId(goodsId);
        //return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
    }

    @Transactional
    public OrderInfo createOrder(GoodsDto goodsDto) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsDto.getId());
        orderInfo.setGoodsName(goodsDto.getGoodsName());
        orderInfo.setGoodsPrice(goodsDto.getMiaoshaPrice());
        orderInfo.setStatus(0);

        orderDao.insertOrderInfo(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsDto.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());

        orderDao.insertMiaoShaOrder(miaoshaOrder);
        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+"_"+goodsDto.getId(),miaoshaOrder);
        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
