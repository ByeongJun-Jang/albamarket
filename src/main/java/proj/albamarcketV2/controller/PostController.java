package proj.albamarcketV2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proj.albamarcketV2.domain.member.LoginForm;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.post.Post;
import proj.albamarcketV2.domain.post.PostRepository;
import proj.albamarcketV2.service.comment.CommentService;
import proj.albamarcketV2.service.post.PostService;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostRepository postRepository;

//    @GetMapping("/posts")
    public String getAllPosts1(Model model, @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "post/postList";
    }


    @GetMapping("/posts")
    public String getAllPosts(Model model, Principal principal){
        model.addAttribute("posts", postService.getAllPosts());
        return "post/postList";
    }

    @GetMapping("/posts/addPost")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/addPost";
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, HttpSession httpSession, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member != null){
            postService.savePost(post, member.getLoginId());
            return "redirect:/posts";
        }else{
            model.addAttribute("loginForm", new LoginForm());
            log.info("error 로그인하지 않은 사용자");
            return "login/loginForm";
        }
    }

    @GetMapping("/posts/{id}")
    public String showPostDetails(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("comments",commentService.getCommentsByPostId(id));
        return "post/postDetails";
    }

    @GetMapping("/posts/{id}/update")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post/editPost";
    }

    @PostMapping("/posts/{id}/update")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post) {
        postService.updatePost(id, post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        log.info("Deleted post {}", id);
        return "redirect:/posts";
    }

//    @PostMapping("/posts/{id}/comments")
    public String addComment1(@PathVariable Long postId, @RequestParam String content, Principal principal) {
        String username = "testName"; // 또는 principal.getName();
//        commentService.addComment(postId, content, username);
        return "redirect:/posts/" + postId;
    }

//    @PostMapping("/posts/{postId}/comments")
    public String addComment2(@PathVariable Long postId, @RequestParam String content, @RequestParam String username) {
        commentService.addComment(postId, content, username);
        return "redirect:/posts/" + postId;
    }

}
