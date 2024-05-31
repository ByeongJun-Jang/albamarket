package proj.albamarcketV2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import proj.albamarcketV2.domain.member.LoginForm;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.member.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession httpSession, Model model){
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginForm.getLoginId());
        if (memberOptional.isPresent() && memberOptional.get().getPassword().equals(loginForm.getPassword())) {
            httpSession.setAttribute("member", memberOptional.get());
            httpSession.setAttribute("role",memberOptional.get().getMemberRole());
            httpSession.setAttribute("isLogin", true);
            return "redirect:/";
        } else{
            model.addAttribute("loginError", "로그인이 실패했습니다.");
            log.info("실패 {}",model);
            return "login/loginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/members/add")
    public String showRegistrationForm(@ModelAttribute("member")Member member) {
        return "members/addForm";
    }

    @PostMapping("/members/add")
    public String processRegister(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addForm";
        }
        log.info("member: {}", member);
        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/members/edit")
    public String editUserForm(HttpSession httpSession, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        return "members/editForm";
    }

//    @PostMapping("/members/edit")
    public String editUser(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult,
                           @RequestParam("profilePicture") MultipartFile profilePicture, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            log.info("바인딩 여기냐?");
            bindingResult.getAllErrors().forEach(error -> log.info(error.toString()));
            return "members/editForm";
        }

        Member existingMember = memberRepository.findById(member.getId()).orElse(null);
        if (existingMember == null) {
            return "redirect:/";
        }

        if (!profilePicture.isEmpty()) {
            try {
                log.info("여기냐?");
                String fileName = UUID.randomUUID().toString() + "-" + profilePicture.getOriginalFilename();
                String uploadDir = "uploads/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                profilePicture.transferTo(new File(uploadDir + fileName));
                String fileUrl = "/uploads/" + fileName;
                existingMember.setProfilePicture(fileUrl);
            } catch (IOException e) {
                log.info("예외 여기냐?");
                bindingResult.rejectValue("profilePicture", "uploadError", "파일 업로드 실패");
                return "members/editForm";
            }
        }

        existingMember.setUsername(member.getUsername());
        existingMember.setPassword(member.getPassword());
        existingMember.setMemberRole(member.getMemberRole());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        existingMember.setAddress(member.getAddress());
        existingMember.setLoginId(member.getLoginId());
        existingMember.setGender(member.getGender());

        memberRepository.save(existingMember);
        httpSession.setAttribute("member", existingMember);

        return "redirect:/";
    }

    @PostMapping("/members/edit")
    public String editUser(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            log.info("바인딩 에러 발생");
            bindingResult.getAllErrors().forEach(error -> log.info(error.toString()));
            return "members/editForm";
        }

        Member existingMember = memberRepository.findById(member.getId()).orElse(null);
        if (existingMember == null) {
            return "redirect:/";
        }

        MultipartFile profilePictureFile = member.getProfilePictureFile();
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
            try {
                log.info("파일 업로드 시도");
                String fileName = UUID.randomUUID().toString() + "-" + profilePictureFile.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/uploads/"; // 절대 경로로 변경
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    boolean dirsCreated = uploadDirFile.mkdirs();
                    if (!dirsCreated) {
                        throw new IOException("Failed to create directories for " + uploadDir);
                    }
                }
                File destinationFile = new File(uploadDir + fileName);
                profilePictureFile.transferTo(destinationFile);
                String fileUrl = "/uploads/" + fileName;
                existingMember.setProfilePicture(fileUrl);
            } catch (IOException e) {
                log.info("파일 업로드 예외 발생: " + e.getMessage());
                log.error("스택 트레이스: ", e);
                bindingResult.rejectValue("profilePictureFile", "uploadError", "파일 업로드 실패");
                return "members/editForm";
            }
        }

        existingMember.setUsername(member.getUsername());
        existingMember.setPassword(member.getPassword());
        existingMember.setMemberRole(member.getMemberRole());
        existingMember.setEmail(member.getEmail());
        existingMember.setNickname(member.getNickname());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        existingMember.setAddress(member.getAddress());
        existingMember.setLoginId(member.getLoginId());
        existingMember.setGender(member.getGender());

        memberRepository.save(existingMember);
        httpSession.setAttribute("member", existingMember);

        return "redirect:/";
    }

//    @PostMapping("/members/edit")
//    public String editUser(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult, HttpSession httpSession) {
//        if (bindingResult.hasErrors()) {
//            return "members/editForm";
//        }
//
//        Member existingMember = memberRepository.findById(member.getId()).orElse(null);
//        if (existingMember == null) {
//            return "redirect:/";
//        }
//
//        existingMember.setId(member.getId());
//        existingMember.setUsername(member.getUsername());
//        existingMember.setPassword(member.getPassword());
//        existingMember.setMemberRole(member.getMemberRole());
//        existingMember.setEmail(member.getEmail());
//        existingMember.setPhoneNumber(member.getPhoneNumber());
//        existingMember.setAddress(member.getAddress());
//        existingMember.setLoginId(member.getLoginId());
//        existingMember.setGender(member.getGender());
//
//
//        memberRepository.save(existingMember);
//        httpSession.setAttribute("member", existingMember);
//
//        return "redirect:/";
//    }
}
