package self.lcw.order.server.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import self.lcw.order.server.entity.OrderInfo;
import self.lcw.order.server.message.StreamClient;

@RestController
public class StreamSenderTest {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/StreamSenderTest")
    public void process(){
        streamClient.output().send(MessageBuilder.withPayload("hahaha").build());
    }

    @GetMapping("/StreamObjectSenderTest")
    public void processDto(){
        OrderInfo order = new OrderInfo();
        order.setStatus(1);
        streamClient.output().send(MessageBuilder.withPayload(order).build());
    }
}
