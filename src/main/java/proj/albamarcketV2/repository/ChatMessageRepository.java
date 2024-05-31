package proj.albamarcketV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.albamarcketV2.domain.message.ChatMessage;
import proj.albamarcketV2.domain.message.ChatRoom;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    List<ChatMessage> findByContentContaining(String keyword);
}
