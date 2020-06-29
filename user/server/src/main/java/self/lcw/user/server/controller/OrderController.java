package self.lcw.user.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.order.client.OrderClient;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.user.server.entity.User;
import self.lcw.user.server.result.CodeMsg;
import self.lcw.user.server.result.Result;

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
        OrderDetailDto orderDetailDto = orderClient.orderDetail(orderId);
        if (orderDetailDto == null){
            return Result.error(CodeMsg.ORDER_NULL);
        }
        System.out.println(orderDetailDto);
        return Result.success(orderDetailDto);
    }
}
