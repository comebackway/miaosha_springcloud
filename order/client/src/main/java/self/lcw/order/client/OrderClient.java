package self.lcw.order.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import self.lcw.order.common.OrderDetailDto;

@FeignClient(name = "order")
public interface OrderClient {
    @GetMapping("/order/order/detail")
    OrderDetailDto orderDetail(@RequestParam("orderId") long orderId);

    @RequestMapping(value = "/order/miaosha/do_miaosha", method = RequestMethod.POST, consumes = "application/json")
    OrderDetailDto doMiaosha(@RequestParam("goodsId") long goodsId);
}
