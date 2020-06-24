package self.lcw.order.server.controller;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.order.server.entity.MiaoshaOrder;
import self.lcw.order.server.redis.GoodsKey;
import self.lcw.order.server.redis.RedisService;
import self.lcw.order.server.result.CodeMsg;
import self.lcw.order.server.result.Result;
import self.lcw.order.server.service.MiaoshaService;
import self.lcw.order.server.service.OrderService;
import self.lcw.product.client.ProductClient;
import self.lcw.product.common.GoodsDto;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {


    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    ProductClient productClient;


    private Map<Long,Boolean> localOverMap = new HashMap<Long, Boolean>();

    /*
    该方法的作用是：在系统初始化时会调用该方法
    业务场景：在系统初始化时将商品秒杀的库存放到redis中。并初始化商品库存是否已经被秒杀完标志
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsDto> goodsDtoList = productClient.showList();
        if (goodsDtoList == null){
            return;
        }
        for (GoodsDto goodsDto:goodsDtoList){
            redisService.set(GoodsKey.getGoodsStock,""+goodsDto.getId(),goodsDto.getStockCount());
            //设定商品是否已被秒杀完标志
            localOverMap.put(goodsDto.getId(),false);
        }
    }


    //限定只能是post方式提交
    /*
    GET和POST区别：
    GET是幂等的，也就是不会对服务端数据有影响 比如只是查询
    POST是对服务端数据有影响的 比如新增修改删除
     */
    /*
    V3.0改造 隐藏url接口 新增动态参数path作为url一部分并作为参数校验
     */
    @PostMapping(value = "/do_miaosha")
    @ResponseBody
    public OrderDetailDto miaosha(@RequestParam("goodsId") long goodsId){

        /*
        v2.0 将库存放在redis中执行，使用rabbitmq对请求进行异步处理
         */
        //在预减库存前先查询是否已秒杀完毕标志,尽可能减少性能开销
        boolean over = localOverMap.get(goodsId);
        if (over){
            return null;
        }


        long stock = miaoshaService.reduceproductself(goodsId);

        if (stock < 0){
            localOverMap.put(goodsId,true);
            return null;
        }

        //判断是否已秒杀


        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(goodsId);
        if (miaoshaOrder != null){
            return null;
        }



        OrderDetailDto orderDetailDto = miaoshaService.miaosha(goodsId);

        return orderDetailDto;
    }


    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    /**
     * orderId  成功
     * -1      失败
     *  0      排队中
     */
    public Result<Long> miaoshaResult(Model model, @RequestParam("goodsId") long goodsId) {
        long result = miaoshaService.getMiaoshaResult(goodsId);
        return Result.success(result);
    }

}
