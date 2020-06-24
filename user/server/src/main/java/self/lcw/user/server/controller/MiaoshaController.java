package self.lcw.user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.order.client.OrderClient;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.user.server.entity.User;
import self.lcw.user.server.result.Result;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController {

    @Autowired
    OrderClient orderClient;

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderDetailDto> doMiaosha(User user, @RequestParam("goodsId") long goodsId){
        System.out.println("goodsId"+goodsId);
        OrderDetailDto orderInfo = orderClient.doMiaosha(goodsId);
        if (orderInfo!=null){
        return Result.success(orderInfo);}
        else {
            return Result.error(null);
        }
    }
}
