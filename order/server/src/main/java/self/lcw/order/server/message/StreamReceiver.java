package self.lcw.order.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import self.lcw.order.server.entity.OrderInfo;

@Component
@Slf4j
@EnableBinding(StreamClient.class)
public class StreamReceiver {
//    @StreamListener(StreamClient.MessageName)
//    public void process(Object mess){
//        log.info("StreamReceiver: {}",mess);
//    }

    @StreamListener(StreamClient.MessageName)
    @SendTo(StreamClient.SendTo)
    public String process(OrderInfo mess){
        log.info("StreamReceiver: {}",mess);
        //此时返回的String就会作为消息发送给@SendTo里边定义的消息队列
        return "received finsh";
    }

    @StreamListener(StreamClient.SendTo)
    public void process2(String mess){
        log.info("StreamReceiver2: {}",mess);
    }
}
