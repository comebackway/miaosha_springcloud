package self.lcw.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import self.lcw.order.entity.MiaoshaOrder;
import self.lcw.order.entity.OrderInfo;
import self.lcw.order.entity.User;
import self.lcw.order.redis.RedisService;
import self.lcw.order.result.CodeMsg;
import self.lcw.order.result.Result;
import self.lcw.order.service.MiaoshaService;
import self.lcw.order.service.OrderService;
import java.util.HashMap;

import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {


    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    private Map<Long,Boolean> localOverMap = new HashMap<Long, Boolean>();



    //限定只能是post方式提交
    /*
    GET和POST区别：
    GET是幂等的，也就是不会对服务端数据有影响 比如只是查询
    POST是对服务端数据有影响的 比如新增修改删除
     */
    /*
    V3.0改造 隐藏url接口 新增动态参数path作为url一部分并作为参数校验
     */
    @RequestMapping(value = "/{path}/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    //将返回类型从Result<OrderInfo> 变成 Result<Integer>  返回状态（1：表示排队中）
    public Result<Integer> miaosha(Model model, User user, @RequestParam("goodsId") long goodsId
            , @PathVariable("path") String path){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        /*
        v2.0 将库存放在redis中执行，使用rabbitmq对请求进行异步处理
         */
        //在预减库存前先查询是否已秒杀完毕标志,尽可能减少性能开销
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //TODO 预减库存
        //1. 为了提高性能 先在秒杀中预减库存，减少流量
        long stock = miaoshaService.reduceproductself(goodsId);
        if (stock < 0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }

        //判断是否已秒杀
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if (miaoshaOrder != null){
            return Result.error(CodeMsg.MIAOSHA_REPEATE);
        }

        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsId);

        //3. 下订单服务



        //TODO 入队操作
        /*
        MQMiaoshaMessage mqMiaoshaMessage = new MQMiaoshaMessage();
        mqMiaoshaMessage.setGoodId(goodsId);
        mqMiaoshaMessage.setUser(user);
        mqSender.sendMiaoshaMessage(mqMiaoshaMessage);
        */

        //返回排队中提示
        return Result.success(1);
    }


    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    /**
     * orderId  成功
     * -1      失败
     *  0      排队中
     */
    public Result<Long> miaoshaResult(Model model, User user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

}
