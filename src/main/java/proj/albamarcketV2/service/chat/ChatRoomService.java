package proj.albamarcketV2.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.message.ChatRoom;
import proj.albamarcketV2.domain.message.ChatRoomMember;
import proj.albamarcketV2.repository.ChatRoomMemberRepository;
import proj.albamarcketV2.repository.ChatRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> findAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public void addMemberToChatRoom(ChatRoom chatRoom, Member member) {
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setChatRoom(chatRoom);
        chatRoomMember.setMember(member);
        chatRoomMemberRepository.save(chatRoomMember);
    }
}
