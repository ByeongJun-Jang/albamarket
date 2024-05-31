package proj.albamarcketV2.domain.comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.post.Post;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.ACTIVE;

    private LocalDateTime createdAt = LocalDateTime.now();
}
