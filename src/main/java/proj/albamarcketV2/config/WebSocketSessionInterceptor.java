package proj.albamarcketV2.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketSessionInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (SimpMessageHeaderAccessor.getSessionAttributes(accessor.getMessageHeaders()) == null) {
            Map<String, Object> sessionAttributes = (Map<String, Object>) accessor.getHeader(SimpMessageHeaderAccessor.SESSION_ATTRIBUTES);
            if (sessionAttributes != null) {
                accessor.setSessionAttributes(sessionAttributes);
            }
        }
        return message;
    }
}
