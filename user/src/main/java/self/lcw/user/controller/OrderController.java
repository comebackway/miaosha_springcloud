package self.lcw.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.user.FeginClient.OrderClient;
import self.lcw.user.dto.GoodsDto;
import self.lcw.user.dto.OrderDetailDto;
import self.lcw.user.entity.User;
import self.lcw.user.result.CodeMsg;
import self.lcw.user.result.Result;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderClient orderClient;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailDto> info(User user, @RequestParam("orderId") long orderId){
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderDetailDto orderDetailDto = orderClient.orderDetail(user, orderId);
        if (orderDetailDto == null){
            return Result.error(CodeMsg.ORDER_NULL);
        }
        return Result.success(orderDetailDto);
    }
}
