package self.lcw.order.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    String MessageName = "myMessageStream";
    String SendTo = "sendToMessageStream";

    @Input(StreamClient.MessageName)
    SubscribableChannel input();

    @Output(StreamClient.MessageName)
    MessageChannel output();

    @Input(StreamClient.SendTo)
    SubscribableChannel inputSendTo();

    @Output(StreamClient.SendTo)
    MessageChannel outputSendTo();
}
