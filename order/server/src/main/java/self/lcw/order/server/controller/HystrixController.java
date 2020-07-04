package self.lcw.order.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@DefaultProperties(defaultFallback = "defaultfall")  //该注解是hystrix提供的，可以定义默认的参数
@RequestMapping(value = "/order")
public class HystrixController {

    @GetMapping("/hys")
    //@HystrixCommand(fallbackMethod = "error")
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "500"),
//            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //使用熔断机制
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//至少有10个请求过来了才开始进行熔断值的计算（也就是前9个100%的错误率也不会触发熔断）
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//熔断开启10000ms后，会变成半开状态
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "90") // 熔断器触发的错误的百分比
//    }) //当注解没有设定回调函数时，使用默认的回调函数
    @HystrixCommand(commandKey = "haha")
    public String getProductList(@RequestParam("number") Integer number){
        if (number == 1){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject("http://127.0.0.1:8081/product/product/showList",String.class);
        return res;
    }

    private String error(){
        return "服务器出小差啦~~";
    }

    private String defaultfall(){
        return "服务器默认出小差啦~~";
    }
}
