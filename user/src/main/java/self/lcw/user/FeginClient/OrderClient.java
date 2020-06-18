package self.lcw.user.FeginClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import self.lcw.user.dto.OrderDetailDto;
import self.lcw.user.entity.OrderInfo;
import self.lcw.user.entity.User;

@FeignClient(name = "order")
public interface OrderClient {
    @GetMapping("/order/order/detail")
    OrderDetailDto orderDetail(User user, @RequestParam("orderId") long orderId);

    @RequestMapping(value = "/order/miaosha/do_miaosha",method = RequestMethod.POST,consumes = "application/json")
    OrderInfo doMiaosha(@RequestBody User user, @RequestParam("goodsId") long goodsId);
}
