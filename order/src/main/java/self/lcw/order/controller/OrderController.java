package self.lcw.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.order.FeginClient.ProductClient;
import self.lcw.order.dto.GoodsDto;
import self.lcw.order.dto.OrderDetailDto;
import self.lcw.order.entity.OrderInfo;
import self.lcw.order.entity.User;
import self.lcw.order.service.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductClient productClient;

    @RequestMapping("/detail")
    @ResponseBody
    public OrderDetailDto info(User user, @RequestParam("orderId") long orderId){
        if (user == null){
            return null;
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null){
            return null;
        }
        long goodsId = orderInfo.getGoodsId();
        //TODO 获得goods信息
        GoodsDto goods = productClient.basic(goodsId);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setGoodsDto(goods);
        orderDetailDto.setOrderInfo(orderInfo);
        return orderDetailDto;
    }
}
