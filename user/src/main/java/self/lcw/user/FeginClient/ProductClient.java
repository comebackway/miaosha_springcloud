package self.lcw.user.FeginClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import self.lcw.user.dto.GoodsDetailDto;
import self.lcw.user.dto.GoodsDto;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/product/msg")
    String productMsg();

    @PostMapping("/product/reduce")
    Boolean miaoshaReduceProduct(@RequestParam("goodsId") long goodsId);

    @GetMapping("/product/goods/detail")
    GoodsDetailDto detail(@RequestParam("goodsId") long goodsId);

    @GetMapping("/product/goods/showList")
    List<GoodsDto> showList();
}
