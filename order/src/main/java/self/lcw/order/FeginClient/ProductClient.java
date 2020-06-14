package self.lcw.order.FeginClient;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import self.lcw.order.dto.GoodsDetailDto;
import self.lcw.order.result.Result;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/product/msg")
    String productMsg();

    @PostMapping("/product/reduce")
    Boolean miaoshaReduceProduct(@RequestParam("goodsId") long goodsId);

    @PostMapping("/product/detail")
    GoodsDetailDto detail(@RequestParam("goodsId") long goodsId);
}
