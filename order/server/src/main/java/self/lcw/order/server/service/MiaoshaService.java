package self.lcw.order.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.order.server.entity.MiaoshaOrder;
import self.lcw.order.server.entity.OrderInfo;
import self.lcw.order.server.redis.GoodsKey;
import self.lcw.order.server.redis.RedisService;
import self.lcw.product.common.GoodsDetailDto;
import self.lcw.product.common.GoodsDto;
import self.lcw.product.client.ProductClient;


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

    @Autowired
    ProductClient productClient;

    @Transactional
    public Integer miaosha(long goodsId){

        //减库存，调用product接口
        boolean success = productClient.miaoshaReduceProduct(goodsId);
        if (success){
            return 1;
        }else {
            return 0;
        }
        /*
        if (success){
            // 获取商品详情信息 调用product接口
            GoodsDetailDto goodsDetailDto = productClient.detail(goodsId);
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

            return orderDetailDto;
        }else{
            // TODO 设置秒杀完毕状态
            return null;
        }
        */
    }


    /**
     * 查询秒杀结果
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(long goodsId) {
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(goodsId);
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

    public long reduceproductself(long goodsId) {
        long stock = redisService.decr(GoodsKey.getGoodsStock,""+goodsId);
        return stock;
    }
}
