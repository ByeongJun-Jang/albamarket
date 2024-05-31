package proj.albamarcketV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.albamarcketV2.domain.message.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
