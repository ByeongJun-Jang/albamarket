package proj.albamarcketV2.domain.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.albamarcketV2.domain.member.Member;

@Entity
@Getter @Setter
public class ChatRoomMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
