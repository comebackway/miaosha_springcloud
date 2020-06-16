package self.lcw.user.FeginClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import self.lcw.user.dto.OrderDetailDto;
import self.lcw.user.entity.OrderInfo;
import self.lcw.user.entity.User;

@FeignClient(name = "order")
public interface OrderClient {
    @GetMapping("/order/order/detail")
    OrderDetailDto orderDetail(User user, @RequestParam("orderId") long orderId);

    @PostMapping("/order/miaosha/do_miaosha")
    OrderInfo doMiaosha(@RequestParam("user") User user, @RequestParam("goodsId") long goodsId);
}
