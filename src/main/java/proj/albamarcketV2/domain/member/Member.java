package proj.albamarcketV2.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import proj.albamarcketV2.domain.message.ChatMessage;
import proj.albamarcketV2.domain.message.ChatRoomMember;
import proj.albamarcketV2.domain.timetable.Timetable;

import java.util.Set;


@Entity
@Getter @Setter
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "사용자 이름을 작성해주세요 !")
    private String username;

    @NotEmpty(message = "사용할 아이디를 작성해주세요 !")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요 !")
    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "성별을 선택해 주세요 !")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "직종을 선택해 주세요 !")
    private MemberRole memberRole;

    private String phoneNumber;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String address;

    private String profilePicture = "/image/user.jpeg";

    @Transient
    private MultipartFile profilePictureFile;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Timetable timetable;

    @OneToMany(mappedBy = "member")
    private Set<ChatMessage> messages;

    @OneToMany(mappedBy = "member")
    private Set<ChatRoomMember> chatRoomMembers;

    /*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(memberRole.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }
    */

}
