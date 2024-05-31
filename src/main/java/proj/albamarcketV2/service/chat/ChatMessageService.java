package proj.albamarcketV2.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proj.albamarcketV2.domain.message.ChatMessage;
import proj.albamarcketV2.domain.message.ChatRoom;
import proj.albamarcketV2.repository.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> searchMessages(String keyword) {
        return chatMessageRepository.findByContentContaining(keyword);
    }

    public List<ChatMessage> findMessagesByChatRoom(ChatRoom chatRoom) {
        return chatMessageRepository.findByChatRoom(chatRoom);
    }
}
