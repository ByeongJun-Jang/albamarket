package proj.albamarcketV2.domain.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chatRoom")
    private Set<ChatRoomMember> chatRoomMembers;

    @OneToMany(mappedBy = "chatRoom")
    private Set<ChatMessage> messages;

}
