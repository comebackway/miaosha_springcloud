package self.lcw.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.user.FeginClient.OrderClient;
import self.lcw.user.entity.OrderInfo;
import self.lcw.user.entity.User;
import self.lcw.user.result.Result;

@RequestMapping("/miaosha")
@Controller
public class MiaoshaController {

    @Autowired
    OrderClient orderClient;

    @RequestMapping("/do_miaosha")
    @ResponseBody
    public Result<OrderInfo> doMiaosha(User user, @RequestParam("goodsId") long goodsId){
        OrderInfo orderInfo = orderClient.doMiaosha(user,goodsId);
        if (orderInfo!=null){
        return Result.success(orderInfo);}
        else {
            return Result.error(null);
        }
    }
}
