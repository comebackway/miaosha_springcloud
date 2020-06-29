package self.lcw.user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.order.client.OrderClient;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.user.server.entity.User;
import self.lcw.user.server.redis.OrderKey;
import self.lcw.user.server.redis.RedisService;
import self.lcw.user.server.result.Result;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController {

    @Autowired
    OrderClient orderClient;

    @Autowired
    RedisService redisService;

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<Integer> doMiaosha(User user, @RequestParam("goodsId") long goodsId){
        System.out.println("goodsId"+goodsId);
        Integer res = orderClient.doMiaosha(goodsId);
        return Result.success(res);
    }


    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    /**
     * orderId  成功
     * -1      失败
     *  0      排队中
     */
    public Result<String> miaoshaResult(@RequestParam("goodsId") long goodsId) {
//        try {
//            OrderDetailDto orderDetailDto = redisService.get(OrderKey.getMiaoshaOrderByUidGid, String.valueOf(goodsId), OrderDetailDto.class);
//            if (orderDetailDto != null){
//                return  Result.success(orderDetailDto);
//            }else{
//                return  Result.success(null);
//            }
//        }
//        catch (Exception e){
//            return Result.error(null);
//        }
        String res = redisService.get(OrderKey.Result, String.valueOf(goodsId), String.class);
        if (res.equals("1")){
            String or = redisService.get(OrderKey.getMiaoshaOrderByUidGid, String.valueOf(goodsId), String.class);
            return  Result.success(or);
        }else{
            return  Result.success("0");
        }
    }

}
