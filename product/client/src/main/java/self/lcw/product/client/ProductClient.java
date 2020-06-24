package self.lcw.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import self.lcw.product.common.GoodsDetailDto;
import self.lcw.product.common.GoodsDto;

import java.util.List;


@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/product/product/msg")
    String productMsg();

    @PostMapping("/product/product/reduce")
    Boolean miaoshaReduceProduct(@RequestParam("goodsId") long goodsId);

    @GetMapping("/product/product/detail")
    GoodsDetailDto detail(@RequestParam("goodsId") long goodsId);

    @GetMapping("/product/product/showList")
    List<GoodsDto> showList();

    @GetMapping("/product/product/basic")
    GoodsDto basic(@RequestParam("goodsId") long goodsId);

}
