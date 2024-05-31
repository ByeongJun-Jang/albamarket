package proj.albamarcketV2.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.member.MemberRepository;
import proj.albamarcketV2.domain.post.Post;
import proj.albamarcketV2.domain.post.PostRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

//    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> getAllPosts() {
        return postRepository.findAllWithAuthor();
    }

    @Transactional
    public void savePost(Post post, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없네여" + loginId));
        log.info("Saving post {}", post);
        post.setAuthor(member);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 포스트 번호 " + id));
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id){
        postRepository.softDelete(id);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("포스트가 없습니다. 포스트 id는 " + id));
    }
}
