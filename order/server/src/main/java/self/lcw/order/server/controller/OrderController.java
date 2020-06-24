package self.lcw.order.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import self.lcw.order.common.OrderDetailDto;
import self.lcw.order.server.entity.OrderInfo;
import self.lcw.order.server.service.OrderService;
import self.lcw.product.client.ProductClient;
import self.lcw.product.common.GoodsDto;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductClient productClient;

    @GetMapping("/detail")
    @ResponseBody
    public OrderDetailDto info(@RequestParam("orderId") long orderId){
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null){
            return null;
        }
        long goodsId = orderInfo.getGoodsId();
        //TODO 获得goods信息
        GoodsDto goods = productClient.basic(goodsId);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setGoodsDto(goods);
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
    }
}
