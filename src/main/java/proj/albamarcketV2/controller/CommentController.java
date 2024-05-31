package proj.albamarcketV2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import proj.albamarcketV2.domain.member.LoginForm;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.service.comment.CommentService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@PathVariable Long postId, @RequestParam("content") String content,HttpSession httpSession ,Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member != null){
            commentService.addComment(postId, content, String.valueOf(member));
            return "redirect:/posts/" + postId;
        }else {
            model.addAttribute("loginForm", new LoginForm());
            log.info("error 로그인하지 않은 사용자");
            return "login/loginForm";
        }
    }

    @PostMapping("/posts/{postId}/{commentId}/update")
    public String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestParam("content") String content) {
        commentService.updateComment(commentId, content);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}
