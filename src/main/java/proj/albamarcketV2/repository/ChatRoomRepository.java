package proj.albamarcketV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.albamarcketV2.domain.message.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
