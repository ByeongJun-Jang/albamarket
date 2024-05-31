package proj.albamarcketV2.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.albamarcketV2.domain.comment.Comment;
import proj.albamarcketV2.domain.comment.CommentRepository;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.member.MemberRepository;
import proj.albamarcketV2.domain.post.Post;
import proj.albamarcketV2.domain.post.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addComment(Long postId, String content, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없네여"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없네여"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(member);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String content){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없네여 " + commentId));
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId){
        commentRepository.softDelete(commentId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
