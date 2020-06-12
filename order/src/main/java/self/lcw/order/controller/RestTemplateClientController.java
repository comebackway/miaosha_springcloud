package self.lcw.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 服务间通信的几种方法demo
 */
@RestController
@Slf4j
public class RestTemplateClientController {

    /**
     * 方法一：使用RestTemplate
     * 缺点：1、要事先知道对方的服务器地址  2、无法获得对方多个地址，只能写死一个
     * @return
     */
    @GetMapping("/getProductMsg_1")
    public String getProductMsg_1(){
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject("http://localhost:8080/product/msg",String.class);
        log.info("response = {}",res);
        return res;
    }



    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 方法二：使用ServiceInstance获得服务名称(应用名) 对应的随机地址和端口，然后再使用RestTemplate访问
     * 缺点：每次都要获取一次url 且要写出拼接的url 不够灵活
     * @return
     */
    @GetMapping("/getProductMsg_2")
    public String getProductMsg_2(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s/%s/msg",serviceInstance.getHost(),serviceInstance.getPort(),"product");
        log.info(url);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url,String.class);
        log.info("response = {}",res);
        return res;
    }




    /**
     * 使用自定义的RestTemplate（实现来@LoadBalance注解的）
     * 其实也就是上边第二种的注解版，但更加方便使用
     * 使用方法：端口ip直接用实例名称替代
     * @return
     */

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getProductMsg_3")
    public String getProductMsg_3(){
        String res = restTemplate.getForObject("http://PRODUCT/product/msg",String.class);
        log.info("response = {}",res);
        return res;
    }
}
