package self.lcw.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import self.lcw.order.FeginClient.ProductClient;

@RestController
@Slf4j
public class FeignClientController {
    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg_feign_1")
    public String getProduct_1(){
        String res = productClient.productMsg();
        log.info("response = {}",res);
        return res;
    }
}
